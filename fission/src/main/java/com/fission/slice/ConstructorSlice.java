package com.fission.slice;

import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

import java.util.Arrays;
import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/21 15:33
 * Description :
 */
public class ConstructorSlice extends MethodSlice {

    public ConstructorSlice(String name, String... params) {
        this(name, Arrays.asList(params));
    }

    public ConstructorSlice(String name, List<String> params) {
        super("public", "", name, null,"", params);
        for(String param : params){
            ParamEntity paramEntity = StringParser.parseParam(param);
            addSlice(new LineSlice("this." + paramEntity.getName() + " = " + paramEntity.getName() + ";"));
        }
    }

    @Override
    public String getId() {
        return "constructor";
    }

}
