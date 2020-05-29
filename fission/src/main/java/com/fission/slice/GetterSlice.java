package com.fission.slice;

import com.fission.util.FSystemUtil;
import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/21 16:07
 * Description :
 */
public class GetterSlice extends MethodSlice{

    public GetterSlice(String param) {
        super("public", "", "", null,null);
        ParamEntity paramEntity = StringParser.parseParam(param);
        setReturnType(paramEntity.getType());
        setName("get" + FSystemUtil.toUpperCaseFirstOne(paramEntity.getName()));
        addSlice(new LineSlice("return " + paramEntity.getName() + ";"));
    }

    @Override
    public String getId() {
        return "getter";
    }
}
