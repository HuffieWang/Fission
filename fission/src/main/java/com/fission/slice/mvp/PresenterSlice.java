package com.fission.slice.mvp;

import com.fission.FissionConfig;
import com.fission.annotation.Contract;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.PackageSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/18 18:55
 * Description :
 */
public class PresenterSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "presenter";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Contract.class;
    }

    @Override
    public boolean isForceBuild(Element element) {
        Contract annotation = element.getAnnotation(Contract.class);
        return annotation.forcePresenter();
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        super.handle(element, roundEnvironment, packageName, config);
        Contract annotation = element.getAnnotation(Contract.class);

        String className = annotation.name() + "Presenter";
        String contractName = annotation.name() + "Contract";

        PackageSlice packageSlice = new PackageSlice(packageName,
                "com.musheng.android.common.mvp.BasePresenter"
        );

        ClassSlice classSlice = new ClassSlice("public", "class", className,
                "BasePresenter<"+contractName+".View>",
                null,
                contractName+".Presenter");

        addSlice(packageSlice);
        addSlice(classSlice);
        return className + ".java";
    }
}
