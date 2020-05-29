package com.fission.slice;

import com.fission.api.AbstractSlice;

import java.util.Arrays;
import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/14 18:55
 * Description :
 */
public class ClassSlice extends AbstractSlice {

    private String access;
    private String classType;
    private String className;
    private String extendsClass;
    private String annotation;
    private List<String> impls;

    public ClassSlice(String access, String classType, String className, String extendsClass, String annotation, String... impls) {
        this.className = className;
        this.classType = classType;
        this.extendsClass = extendsClass;
        this.annotation = annotation;
        this.impls = Arrays.asList(impls);
        this.access = access;
    }

    public ClassSlice(String access, String classType, String className, String extendsClass, String annotation, List<String> impls) {
        this.access = access;
        this.classType = classType;
        this.className = className;
        this.extendsClass = extendsClass;
        this.annotation = annotation;
        this.impls = impls;
    }

    @Override
    public String getId() {
        return "class";
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
        builder.append(access + " " + classType + " " + className + " ");
        if(extendsClass != null){
            builder.append("extends " + extendsClass + " ");
        }
        if(impls != null && impls.size() > 0){
            builder.append("implements ");
            for(int i = 0; i < impls.size(); i++){
                if(i > 0 && i < impls.size()-1){
                    builder.append(", ");
                }
                builder.append(impls.get(i));
            }
        }
        builder.append(" {\n");
        oldList.add(builder.toString());
        return oldList;
    }

    @Override
    public List<String> getBottomContents(List<String> oldList) {
        oldList.add("}");
        return oldList;
    }

}
