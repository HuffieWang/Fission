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
 * CreateDate  : 2020/5/15 11:37
 * Description :
 */
public class ActivitySlice extends AbstractSlice {

    @Override
    public String getId() {
        return "activity";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Contract.class;
    }

    @Override
    public boolean isForceBuild(Element element) {
        Contract annotation = element.getAnnotation(Contract.class);
        return annotation.forceView();
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        super.handle(element, roundEnvironment, packageName, config);

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

        String className = elementName + "Activity";
        String contractName = elementName + "Contract";
        String routerName = elementName + "Router";
        String presenterName = elementName + "Presenter";

        PackageSlice packageSlice = new PackageSlice(packageName,
                "android.os.Bundle",
                "android.view.View",
                config.getPackageName() + ".R",
                "com.musheng.android.common.mvp.BaseActivity",
                "com.alibaba.android.arouter.facade.annotation.Route"
        );

        ClassSlice classSlice = new ClassSlice("public", "class", className,
                "BaseActivity<"+contractName+".Presenter>",
                "@Route(path = "+routerName+".PATH)",
                contractName+".View");


        MethodSlice initPresenterMethod = new MethodSlice("public",contractName + ".Presenter" ,
                "initPresenter", null, "@Override");
        initPresenterMethod.addSlice(new LineSlice("return new "+presenterName+"();"));

        MethodSlice setRootViewMethod = new MethodSlice("public","void",
                "setRootView", null,"@Override", "savedInstanceState$Bundle");
        setRootViewMethod.addSlice(new LineSlice("setContentView(R.layout.activity_"+elementName.toLowerCase()+");"));

        MethodSlice initWidgetMethod = new MethodSlice("public","void",
                "initWidget", null,"@Override");
        initWidgetMethod.addSlice(new LineSlice(""));

        addSlice(packageSlice);
        classSlice.addSlice(initPresenterMethod);
        classSlice.addSlice(setRootViewMethod);
        classSlice.addSlice(initWidgetMethod);
        addSlice(classSlice);

        return className + ".java";
    }
}
