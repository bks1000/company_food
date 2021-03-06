package com.june.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Clob;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialClob;

import oracle.sql.BLOB;

public class StringUtil {
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
	}
	 /**
     * 判断对象是否为空
     * 
     * @param str
     * @return
     * */
     
    public static boolean isNotEmpty(Object str){
    	boolean flag=true;
    	if (str!=null&&!str.equals("")) {
			if (str.toString().length()>0) {
				flag=true;
			}
		}else {
			flag=false;
		}
    	return flag;
    }
    
    /**
     * 生成主键
     * 
     * @return
     * */
    public static String getUUID(){
    	UUID uuid=null;
    	try {
    		uuid=UUID.randomUUID();		
    	 } catch (Exception e) {
             e.printStackTrace();
         }
    	return uuid.toString();
    }
    
	/**
	 * URL 编码, Encode默认为UTF-8. 
	 * @throws ServiceException 
	 */
	public static String urlEncode(String part) {
		try {
			part= URLEncoder.encode(part, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return part;
	}
	
	/**
	 * 站点的绝对地址
	 * @param part
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		 
		String url=request.getSession().getServletContext().getRealPath("");
		return url;
	}
	
	
	/**
	 * BLOB转byte[]
	 * @param blob
	 * @return
	 */
	public static byte[] blobToBytes(BLOB blob) {
		
        BufferedInputStream is = null;
        byte[] bytes = null;
        
        try {
            is = new BufferedInputStream(blob.getBinaryStream());
        
            bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;
 
            while (offset < len
                    && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
 
    }
	/**
	 * Clob转String
	 * @param clob
	 * @return
	 */
	public static String clobToString(Clob clob) {
		if (clob == null)
			return null;
		try {
			return clob.getSubString(1, (int) clob.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * String转Clob
	 * @param str
	 * @return
	 */
	public static Clob stringToClob(String str) {
		if (null == str || str.trim().length() == 0) {
			return null;
		}
		try {
			return new SerialClob(str.toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * InputStream转化为byte[]数组
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream is) throws IOException {
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = is.read(buffer))) {
	        os.write(buffer, 0, n);
	    }
     	if(is != null){
     		is.close();
			}
			if(os != null){
				os.flush();
				os.close();
			}
	    return os.toByteArray();
	}
 

	
	/**
	 * 字符串转rtf（富文本）
	 * @param content
	 * @return
	 */
	public static String strToRtf(String content) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\\rtf1\\ansi\\ansicpg936\\deff0\\deflang1033\\deflangfe2052{\\fonttbl{\\f0\\fnil\\fcharset134 \\'cb\\'ce\\'cc\\'e5;}}\\viewkind4\\uc1\\pard\\lang2052\\f0\\fs18");
		try {
			char[] digital = "0123456789abcdef".toCharArray();
			byte[] bs = null;
			bs = content.getBytes("GB2312");
			int bit;
			for (int i = 0; i < bs.length; i++) {
				bit = (bs[i] & 0x0f0) >> 4;
				sb.append("\\'");
				sb.append(digital[bit]);
				bit = bs[i] & 0x0f;
				sb.append(digital[bit]);
			}
			sb.append("\\par}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	} 
	/**
	 * 获取项目路径
	 * @param request
	 * @return
	 */
	public static String getSispPath(HttpServletRequest request) {  
	        String path = request.getServletContext().getRealPath("/");
	        return path;
	 }  
}
