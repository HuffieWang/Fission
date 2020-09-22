package com.fission.slice.fetcher;

import com.fission.slice.LineSlice;
import com.fission.slice.MethodSlice;
import com.fission.util.ParamEntity;
import com.fission.util.StringParser;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/9/17 16:55
 * Description :
 */
public class ObjectBoxTranslateSlice extends MethodSlice {

    public ObjectBoxTranslateSlice(String name, String[] params){
        super("public", name, "translate", null, "@Override");
        addSlice(new LineSlice(name + " entityX = new "+ name +"();"));
        for(String param : params){
            ParamEntity paramEntity = StringParser.parseParam(param);

            if(paramEntity.getType().contains("List<")){
                addSlice(new LineSlice("entityX." + paramEntity.getName() + ".addAll(XCollections.translate(" + paramEntity.getName() + "));"));
            } else {
                if(StringParser.isBaseType(param)){
                    addSlice(new LineSlice("entityX." + paramEntity.getName() + " = " + paramEntity.getName() + ";"));
                } else {
                    addSlice(new LineSlice("entityX." + paramEntity.getName() + ".setTarget(" + paramEntity.getName() + ".translate());"));
                }
            }
        }
        addSlice(new LineSlice("return entityX;"));
    }
}
