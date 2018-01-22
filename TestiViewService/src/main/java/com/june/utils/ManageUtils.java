package com.june.utils;

import java.util.Properties;

public class ManageUtils {
	private static Properties applicationSettings;

    public static Properties getApplicationSettings() {
        if (applicationSettings == null) {
            applicationSettings = (Properties) BeanUtils.getInstance().getBean("applicationProperties");
        }
        return applicationSettings;
    }
}
