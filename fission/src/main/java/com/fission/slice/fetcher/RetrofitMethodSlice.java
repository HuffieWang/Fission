package com.fission.slice.fetcher;

import com.fission.api.AbstractSlice;
import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/22 11:07
 * Description :
 */
public class RetrofitMethodSlice extends AbstractSlice {

    private String name;
    private String url;
    boolean isJson;
    List<String> params;

    public RetrofitMethodSlice(String name, String url, boolean isJson, List<String> params) {
        this.name = name;
        this.url = url;
        this.isJson = isJson;
        this.params = params;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {
        if(!isJson){
            oldList.add("@FormUrlEncoded");
        }
        oldList.add("@POST(\"/" + url + "\")");
        StringBuilder builder = new StringBuilder();
        builder.append("Call<"+name+"> ");
        builder.append(url.replaceAll("/", "_"));
        builder.append("(");
        if(isJson){
            builder.append("@Body ").append(name).append("Request param");

        } else if(params == null || params.isEmpty()){
            builder.append("@Field(\"defaultParam\") String defaultParam");

        } else {
            for(int i = 0; i < params.size(); i++){
                ParamEntity paramEntity = StringParser.parseParam(params.get(i));
                builder.append("@Field(\"").append(paramEntity.getName()).append("\") ")
                        .append(paramEntity.getType()).append(" ").append(paramEntity.getName());
                if(params.size() > 1 && i < params.size()-1){
                    builder.append(", ");
                }
            }
        }
        builder.append(");");
        oldList.add(builder.toString());
        return oldList;
    }

    @Override
    public String getId() {
        return "retrofit_method";
    }

    @Override
    public Class getTriggerAnnotation() {
        return null;
    }
}
