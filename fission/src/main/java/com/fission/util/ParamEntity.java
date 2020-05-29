package com.fission.util;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/15 16:55
 * Description :
 */
public class ParamEntity {
    private String type;
    private String name;
    private String annotation;
    private boolean isArray;

    public ParamEntity(String type, String name, String annotation, boolean isArray) {
        this.type = type;
        this.name = name;
        this.annotation = annotation;
        this.isArray = isArray;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }
}
