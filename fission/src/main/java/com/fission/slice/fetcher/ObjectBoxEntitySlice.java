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
public class ObjectBoxEntitySlice extends AbstractSlice {

    @Override
    public String getId() {
        return "entity";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Entity.class;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        super.handle(element, roundEnvironment, packageName, config);

        Entity annotation = element.getAnnotation(Entity.class);

        if(!annotation.objectbox()){
            return null;
        }

        String name = "X" + annotation.name();
        String[] responses = annotation.response();

        boolean isContainList = false;

        String objectboxAnnotation = annotation.objectbox() ? "@Entity" : null;

        ClassSlice classSlice = new ClassSlice("public", "class", name, "BaseEntity", objectboxAnnotation);
        ParamSlice paramSlice = new ParamSlice(responses);
        ConstructorSlice emptyConstructorSlice = new ConstructorSlice(name);
        classSlice.addSlice(paramSlice);
        classSlice.addSlice(emptyConstructorSlice);
        if(responses.length > 0){
            ConstructorSlice constructorSlice = new ConstructorSlice(name, responses);
            classSlice.addSlice(constructorSlice);
        }
        for(String response : responses){
            classSlice.addSlice(new SetterSlice(response));
            classSlice.addSlice(new GetterSlice(response));
            if(response.contains("[]")){
                isContainList = true;
            }
        }

        List<String> imports = new ArrayList<>();
        if(isContainList){
            imports.add("io.objectbox.relation.ToMany");
        }
        if(annotation.objectbox()){
            imports.add("io.objectbox.annotation.*");
        }
        PackageSlice packageSlice = new PackageSlice(packageName, imports);

        addSlice(packageSlice);
        addSlice(classSlice);

        return name + ".java";
    }

    @Override
    public String getContent() {
        return super.getContent().replaceAll("List<", "ToMany<X");
    }

    @Override
    public boolean isForceBuild(Element element) {
        Entity annotation = element.getAnnotation(Entity.class);
        return annotation.forceResponse();
    }


}