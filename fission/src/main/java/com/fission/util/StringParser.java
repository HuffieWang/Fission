package com.fission.util;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/15 16:54
 * Description :
 */
public class StringParser {

    public static boolean isBaseType(String param){
        if(param == null || !param.contains("$")){
            return true;
        }
        String type = param.split("\\$")[1];
        return type.startsWith("boolean") || type.startsWith("char") || type.startsWith("byte") || type.startsWith("short") ||
                type.startsWith("int") || type.startsWith("long") || type.startsWith("float") || type.startsWith("double")
                || type.startsWith("String");
    }

    public static ParamEntity
    parseParam(String param){
        String type = null;
        String name = null;
        String annotation = null;
        boolean isArray = false;

        if(param.contains("$")){
            String[] split = param.split("\\$");
            name = split[0];
            param = split[1];
        } else {
            name = param;
            type = "String";
        }
        if(param.contains("#")){
            String[] split = param.split("#");
            annotation = split[1];
            param = split[0];
        }
        if(param.contains("[]")){
            isArray = true;
            param = param.replaceAll("\\[]", "");
            switch (param){
                case "boolean":
                    param = "Boolean";
                    break;
                case "char":
                    param = "Char";
                    break;
                case "byte":
                    param = "Byte";
                    break;
                case "short":
                    param = "Short";
                    break;
                case "int":
                    param = "Integer";
                    break;
                case "long":
                    param = "Long";
                    break;
                case "float":
                    param = "Float";
                    break;
                case "double":
                    param = "Double";
                    break;
            }
            if(type == null){
                type = "List<" + param+">";
            } else {
                type = "List<String>";
            }
        } else {
            if(type == null){
                type = param;
            }
        }
        return new ParamEntity(type, name, annotation, isArray);
    }
}
