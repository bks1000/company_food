package com.june.utils;

import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.june.config.JavaConfig;

@Component
public class TokenUtils {
	
	@Value("${jwt.signingSecret}")
	public String SigningSecret;// JWT签名密钥
	
	@Value("${appId}")
	public String appId;
	
	@Value("${schema}")
	public String schema;

	public String createJWT(String username, String guid) throws Exception {
		int timeout =0; //CacheUtils.Timeout;// 过期时间（秒）
		
		String issuer = this.appId; //ConfigUtil.getAppId();
		long now = System.currentTimeMillis();

		Algorithm algorithm = Algorithm.HMAC256(SigningSecret);
		Builder builder = JWT.create();
		builder.withIssuer(issuer)// iss 签发者
				// .withSubject("")//sub 所面向的用户
				// .withAudience("")//aud 接收方
				.withExpiresAt(new Date(now + (timeout + 1) * 1000))// exp 过期时间,必须要大于签发时间
				.withNotBefore(new Date(now))// nbf 定义在什么时间之前，该jwt都是不可用的
				.withIssuedAt(new Date(now))// iat 签发时间
				.withJWTId(StringUtil.getUUID());// jti jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
		builder.withClaim("username", username).withClaim("guid", guid);
		String token = builder.sign(algorithm);
		return token;
	}

	public DecodedJWT verify(String token) throws Exception {
		Algorithm algorithm = Algorithm.HMAC256(SigningSecret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}

	/**
	 * 获取Token,先从header中找token，否则从请求参数中找。参数不能以@RequstBody形式传
	 * @param request
	 * @return
	 */
	public String getToken(HttpServletRequest request) {
		if (request != null) {
			String token = request.getHeader(ConfigUtil.getTokenNameInHeader());// header取
			if (token == null || token.length() == 0) {
				token = request.getParameter("token");// 参数取
			}
			return token;
		}
		return null;
	}
}
