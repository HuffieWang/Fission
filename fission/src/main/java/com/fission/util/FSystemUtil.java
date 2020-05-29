package com.fission.util;

import java.io.File;
import java.io.IOException;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/18 16:50
 * Description :
 */
public class FSystemUtil {

    public static String getProjectDir(){
        File directory = new File("");
        String configPath = null;
        try {
            configPath = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configPath;
    }

    public static String getSubPackageName(String className){
        if(className == null){
            return null;
        }
        if(!className.contains(".")){
            return className;
        }
        return className.substring(0, className.lastIndexOf("."));
    }

    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String patternPath(String dir, String pattern){
        if(dir.endsWith("\\")){
            dir = dir.substring(0, dir.lastIndexOf("\\"));
        }
        if(pattern.startsWith("/")){
            return appendFileSeparator(pattern, "\\");
        }
        if(pattern.startsWith("./")){
            return appendFileSeparator(dir + "\\" + pattern.replaceAll("\\./", ""), "\\");
        }
        if(pattern.startsWith("../")){
            dir = dir.substring(0, dir.lastIndexOf("\\"));
            pattern = pattern.substring(3);
            return patternPath(dir, pattern);
        }
        return appendFileSeparator(dir, "\\") + pattern;
    }

    public static String appendFileSeparator(String filepath, String separator){
        if(filepath == null){
            return null;
        }
        if(filepath.endsWith(separator)){
            return filepath;
        }
        return filepath + separator;
    }
}
