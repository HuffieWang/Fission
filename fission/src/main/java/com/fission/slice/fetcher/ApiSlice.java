package com.fission.slice.fetcher;

import com.fission.FissionConfig;
import com.fission.annotation.Entity;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.LineSlice;
import com.fission.slice.PackageSlice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/20 14:07
 * Description :
 */
public class ApiSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "api";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Entity.class;
    }

    @Override
    public boolean isForceBuild(Element element) {
        return true;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName2, FissionConfig config) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Entity.class);

        ClassSlice classSlice = new ClassSlice("public", "interface", "Api",
                null, null);

        for(Element item : elements){
            Entity entity = item.getAnnotation(Entity.class);
            if(entity.fetcher()){
                RetrofitMethodSlice slice = new RetrofitMethodSlice(entity.name(),
                        item.getSimpleName().toString().replaceAll("_", "/"),
                        entity.json(), Arrays.asList(entity.request()));
                classSlice.addSlice(slice);
                classSlice.addSlice(new LineSlice(" "));
            }
        }

        List<String> imports = new ArrayList<>();
        imports.add("retrofit2.Call");
        imports.add("retrofit2.http.Body");
        imports.add("retrofit2.http.POST");
        imports.add("retrofit2.http.Field");
        imports.add("retrofit2.http.FormUrlEncoded");
        PackageSlice packageSlice = new PackageSlice(packageName2, imports);

        addSlice(packageSlice);
        addSlice(classSlice);

        return "Api.java";
    }
}
