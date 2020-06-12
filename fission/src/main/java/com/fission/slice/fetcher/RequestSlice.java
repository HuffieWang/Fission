package com.fission.slice.fetcher;

import com.fission.FissionConfig;
import com.fission.annotation.Entity;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.ConstructorSlice;
import com.fission.slice.GetterSlice;
import com.fission.slice.PackageSlice;
import com.fission.slice.ParamSlice;
import com.fission.slice.SetterSlice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/20 13:54
 * Description :
 */
public class RequestSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "request";
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

        String[] requests = annotation.request();
        boolean isContainList = false;

        ClassSlice classSlice = new ClassSlice("public", "class", annotation.name()+"Request",
                null, null, "MSFetcherRequest");
        ParamSlice paramSlice = new ParamSlice(requests);
        ConstructorSlice emptyConstructorSlice = new ConstructorSlice(annotation.name()+"Request");
        classSlice.addSlice(paramSlice);
        if(requests.length > 0){
            ConstructorSlice constructorSlice = new ConstructorSlice(annotation.name()+"Request", requests);
            classSlice.addSlice(constructorSlice);
        } else {
            classSlice.addSlice(emptyConstructorSlice);
        }
        for(String response : requests){
            classSlice.addSlice(new SetterSlice(response));
            classSlice.addSlice(new GetterSlice(response));
            if(response.contains("[]")){
                isContainList = true;
            }
        }
        List<String> imports = new ArrayList<>();

        imports.add("com.musheng.android.fetcher.MSFetcherRequest");
        imports.add("com.google.gson.Gson");

        if(isContainList){
            imports.add("java.util.List");
        }
        PackageSlice packageSlice = new PackageSlice(packageName, imports);

        addSlice(packageSlice);
        addSlice(classSlice);

        return annotation.name() + "Request.java";
    }

    @Override
    public boolean isForceBuild(Element element) {
        Entity annotation = element.getAnnotation(Entity.class);
        return annotation.forceRequest();
    }

}
