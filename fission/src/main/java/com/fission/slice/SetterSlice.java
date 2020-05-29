package com.fission.slice;

import com.fission.util.FSystemUtil;
import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/21 16:07
 * Description :
 */
public class SetterSlice extends MethodSlice{

    public SetterSlice(String param) {
        super("public", "void", "", null,null, param);
        ParamEntity paramEntity = StringParser.parseParam(param);
        setName("set" + FSystemUtil.toUpperCaseFirstOne(paramEntity.getName()));
        addSlice(new LineSlice("this." + paramEntity.getName() + " = " + paramEntity.getName() + ";"));
    }

    @Override
    public String getId() {
        return "setter";
    }
}
