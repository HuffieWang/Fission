package com.fission.slice.fetcher;

import com.fission.FissionConfig;
import com.fission.annotation.Entity;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.LineSlice;
import com.fission.slice.MethodSlice;
import com.fission.slice.PackageSlice;
import com.fission.util.FSystemUtil;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/20 13:55
 * Description :
 */
public class FetcherSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "fetcher";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Entity.class;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        super.handle(element, roundEnvironment, packageName, config);

        Entity annotation = element.getAnnotation(Entity.class);

        if(!annotation.fetcher()){
            return null;
        }

        String urlName = element.getSimpleName().toString();
        String requestName = annotation.name() + "Request";
        String responseName = annotation.name();

        MethodSlice fetchNetwork = new MethodSlice("public", responseName, "fetchNetwork","Exception","@Override",
                "request$"+requestName);
        if(annotation.json()){
            fetchNetwork.addSlice(new LineSlice("return ((Api)MSRetrofit.getApi())." + urlName + "(request).execute().body();"));
        } else {
            StringBuilder builder = new StringBuilder();
            String requestParam = null;
            if(annotation.request().length > 0){
                for(int i = 0; i < annotation.request().length; i++){
                    builder.append("request.get" + FSystemUtil.toUpperCaseFirstOne(annotation.request()[i]) + "()");
                    if(annotation.request().length > 1 && i < annotation.request().length-1){
                        builder.append(",");
                    }
                }
                requestParam = builder.toString();
            } else {
                requestParam = "\"default\"";
            }
            fetchNetwork.addSlice(new LineSlice("return ((Api)MSRetrofit.getApi())." + urlName + "(" + requestParam + ").execute().body();"));
        }

        MethodSlice fetchCache = new MethodSlice("public", responseName, "fetchCache", "Exception", "@Override",
                "request$"+requestName);
        fetchCache.addSlice(new LineSlice("return null;"));

        MethodSlice fetchDefault = new MethodSlice("public", responseName, "fetchDefault", "Exception", "@Override",
                "request$"+requestName);
        fetchDefault.addSlice(new LineSlice("return null;"));

        MethodSlice writeCache = new MethodSlice("public", "void", "writeCache", "Exception","@Override",
                "request$"+requestName, "entity$"+responseName);

        ClassSlice classSlice = new ClassSlice("public", "class", annotation.name() + "Fetcher",
                "MSBaseFetcher<" + requestName + "," + responseName + ">", null);
        classSlice.addSlice(fetchNetwork);
        classSlice.addSlice(fetchCache);
        classSlice.addSlice(fetchDefault);
        classSlice.addSlice(writeCache);

        List<String> imports = new ArrayList<>();
        imports.add("com.musheng.android.fetcher.MSBaseFetcher");
        imports.add("com.musheng.android.common.retrofit.MSRetrofit");
        PackageSlice packageSlice = new PackageSlice(packageName, imports);

        addSlice(packageSlice);
        addSlice(classSlice);

        return annotation.name() + "Fetcher.java";
    }

    @Override
    public boolean isForceBuild(Element element) {
        Entity annotation = element.getAnnotation(Entity.class);
        return annotation.forceFetcher();
    }

}