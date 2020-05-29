package com.fission.slice;

import com.fission.api.AbstractSlice;
import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

import java.util.Arrays;
import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/14 18:54
 * Description :
 */
public class MethodSlice extends AbstractSlice {

    private String access;
    private String returnType;
    private String name;
    private String exception;
    private String annotation;
    private List<String> params;

    public MethodSlice(String access, String returnType, String name, String exception, String annotation, String... params) {
        this.access = access;
        this.returnType = returnType;
        this.name = name;
        this.exception = exception;
        this.params = Arrays.asList(params);
        this.annotation = annotation;
    }

    public MethodSlice(String access, String returnType, String name, String exception, String annotation, List<String> params) {
        this.access = access;
        this.returnType = returnType;
        this.name = name;
        this.exception = exception;
        this.params = params;
        this.annotation = annotation;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public Class getTriggerAnnotation() {
        return null;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {
        if(annotation != null){
            oldList.add(annotation);
        }
        StringBuilder builder = new StringBuilder();
        if(returnType == null || "".equals(returnType)){
            builder.append(access + " " + name + "(");
        } else {
            builder.append(access + " " + returnType + " " + name + "(");
        }
        if(params != null && params.size() > 0){
            for(int i = 0; i < params.size(); i++){
                ParamEntity paramEntity = StringParser.parseParam(params.get(i));
                builder.append(paramEntity.getType() + " " + paramEntity.getName());
                if(params.size() > 1 && i < params.size()-1){
                    builder.append(", ");
                }
            }
        }
        builder.append(")");
        if(exception != null){
            builder.append(" throws " + exception + " ");
        }
        builder.append("{");
        oldList.add(builder.toString());
        return oldList;
    }

    @Override
    public List<String> getBottomContents(List<String> oldList) {
        oldList.add("}\n");
        return oldList;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
