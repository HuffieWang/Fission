package com.fission.slice.mvp;

import com.fission.FissionConfig;
import com.fission.annotation.Contract;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.LineSlice;
import com.fission.slice.MethodSlice;
import com.fission.slice.PackageSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/22 18:15
 * Description :
 */
public class RouterSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "router";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Contract.class;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        Contract annotation = element.getAnnotation(Contract.class);
        if(annotation.isFragment()){
            return null;
        }
        String elementName;
        if(!"".equals(annotation.name())){
            elementName = annotation.name();
        } else {
            elementName = element.getSimpleName().toString().replaceAll("Contract", "");
        }
        String className = elementName + "Router";

        PackageSlice packageSlice = new PackageSlice(packageName, "com.musheng.android.router.MSBaseRouter");
        ClassSlice classSlice = new ClassSlice("public",  "class", className,
                "MSBaseRouter",
                null);

        classSlice.addSlice(new LineSlice("public static final String PATH = \"/app/" + elementName + "\";\n"));

        MethodSlice getPathMethod = new MethodSlice("public", "String", "getPath", null, "@Override");
        getPathMethod.addSlice(new LineSlice("return PATH;"));

        classSlice.addSlice(getPathMethod);

        addSlice(packageSlice);
        addSlice(classSlice);
        return className + ".java";
    }

    @Override
    public boolean isForceBuild(Element element) {
        Contract annotation = element.getAnnotation(Contract.class);
        return annotation.forceView();
    }
}
