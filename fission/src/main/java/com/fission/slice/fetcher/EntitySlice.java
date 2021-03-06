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
public class EntitySlice extends AbstractSlice {

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

        String[] responses = annotation.response();
        if(annotation.objectbox() && responses.length > 0){
            for(int i = 0; i < responses.length; i++){
                String response = responses[i];
                if(response.contains("#")){
                    responses[i] = response.substring(0, response.lastIndexOf("#"));
                }
            }
        }

        ClassSlice classSlice = null;
        if(annotation.objectbox()){
            classSlice = new ClassSlice("public", "class", annotation.name(),
                    "BaseEntity", null, "XTranslation<X"+annotation.name()+">");
        } else {
            classSlice = new ClassSlice("public", "class", annotation.name(),
                    "BaseEntity", null);
        }

        ParamSlice paramSlice = new ParamSlice(responses);
        ConstructorSlice emptyConstructorSlice = new ConstructorSlice(annotation.name());
        classSlice.addSlice(paramSlice);
        classSlice.addSlice(emptyConstructorSlice);
        if(responses.length > 0){
            ConstructorSlice constructorSlice = new ConstructorSlice(annotation.name(), responses);
            classSlice.addSlice(constructorSlice);
        }

        boolean isContainList = false;

        for(String response : responses){
            classSlice.addSlice(new SetterSlice(response));
            classSlice.addSlice(new GetterSlice(response));
            if(response.contains("[]")){
                isContainList = true;
            }
        }

        if(annotation.objectbox()){
            classSlice.addSlice(new ObjectBoxTranslateSlice("X" + annotation.name(), annotation.response()));
        }

        List<String> imports = new ArrayList<>();
        if(isContainList){
            imports.add("java.util.List");
        }
        if(annotation.objectbox()){
            imports.add("com.musheng.android.common.objectbox.XTranslation");
            if(isContainList){
                imports.add("com.musheng.android.common.objectbox.XCollections");
            }
        }
        PackageSlice packageSlice = new PackageSlice(packageName, imports);

        addSlice(packageSlice);
        addSlice(classSlice);

        return annotation.name() + ".java";
    }

    @Override
    public boolean isForceBuild(Element element) {
        Entity annotation = element.getAnnotation(Entity.class);
        return annotation.forceResponse();
    }


}
