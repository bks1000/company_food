package com.june.utils;

import java.util.Properties;

public class ConfigUtil {
	private static String appId = "";

	private static String schema = "";

	//private static String defSysInitUrl = "";

	private static String tokenNameInHeader = "";

	
	static {
		Properties prop = ManageUtils.getApplicationSettings();
		if (prop != null) {
			appId = prop.getProperty("appId");
			schema = prop.getProperty("schema");
			//defSysInitUrl = prop.getProperty("pt.defSysInitUrl");
			tokenNameInHeader = prop.getProperty("pt.tokenNameInHeader");
		}
	}

	/**
     * 子系统ID
	 * 从配置文件取
	 *
	 * @return
	 */
    public static String getAppId() {
        return appId;
    }

    /**
     * 子系统schema
	 * 从配置文件取
	 *
	 * @return
	 */
    public static String getSchema() {
        return schema;
    }
    

	/***
	 * 子系统初始化入口地址
	 * @return
	 *
	public static String getDefSysInitUrl() {
		return defSysInitUrl;
	}*/

	/***
	 * http header中自定义的用于保存token的头信息名称
	 * @return
	 */
	public static String getTokenNameInHeader() {
		return tokenNameInHeader;
	}
	//=====================================================

    /**
     * 导出模板存放路径
	 */
	public final static String TEMPLATE_EXPORT_PATH="/resources/template/";
	/**
	 * 导出临时文件位置
	 */
	public final static String POI_EXPORT_PATH="/resources/download/export";
	/**
	 * 自定义下载临时文件位置
	 */
	public final static String ZDYCX_EXPORT_PATH="/resources/download/zdycx/";
	/**
	 * 组织机构模板名称
	 */
	public final static String TEMPLATE_ORG_NAME="模板_组织机构.xls";
	/**
	 * 导出组织机构名称
	 */
	public final static String EXPORT_ORG_NAME="财政政策制度库_组织机构.xls";
	/**
	 * 劳务人员到岗模板
	 */
	public final static String TEMPLATE_LWDG_NAME="模板_劳务人员到岗登记表.xls";
	/**
	 * 导出人员到岗
	 */
	public final static String EXPORT_LWDG_NAME="劳务人员到岗登记表.xls";
	/**
	 * 劳务人员离岗模板
	 */
	public final static String TEMPLATE_LWLG_NAME="模板_劳务人员离岗登记表.xls";
	/**
	 * 导出人员离岗
	 */
	public final static String EXPORT_LWLG_NAME="劳务人员离岗登记表.xls";
}
