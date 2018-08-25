package com.ckj.projects.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * created by ChenKaiJu on 2018/8/4  11:19
 */
public class ConfigUtils {

    public static Locale locale1 = new Locale("zh", "CN");
    public static ResourceBundle config = ResourceBundle.getBundle("globalConfig", locale1);

    public static String getString(String key){
        return config.getString(key);
    }

    public static int getInt(String key){
        return Integer.valueOf(config.getString(key));
    }

}
