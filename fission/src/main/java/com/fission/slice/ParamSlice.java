package com.fission.slice;

import com.fission.api.AbstractSlice;
import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

import java.util.Arrays;
import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/14 19:04
 * Description :
 */
public class ParamSlice extends AbstractSlice {

    private List<String> params;

    public ParamSlice(String... params) {
        this(Arrays.asList(params));
    }

    public ParamSlice(List<String> params) {
        this.params = params;
    }

    @Override
    public String getId() {
        return "param";
    }

    @Override
    public Class getTriggerAnnotation() {
        return null;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {
        oldList.add("\n");
        if(params != null && params.size() > 0){
            for(int i = 0; i < params.size(); i++){
                ParamEntity paramEntity = StringParser.parseParam(params.get(i));
                if(paramEntity.getAnnotation() != null){
                    oldList.add(paramEntity.getAnnotation());
                }
                oldList.add(paramEntity.getType() + " " + paramEntity.getName() + ";");
            }
        }
        return oldList;
    }

}
