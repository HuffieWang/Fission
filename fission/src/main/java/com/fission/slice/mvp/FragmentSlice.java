package com.fission.slice.mvp;

import com.fission.FissionConfig;
import com.fission.annotation.Contract;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.LineSlice;
import com.fission.slice.MethodSlice;
import com.fission.slice.PackageSlice;
import com.fission.slice.view.RecyclerJavaSlice;
import com.fission.slice.view.RecyclerParamSlice;
import com.fission.slice.view.TabLayoutJavaSlice;
import com.fission.slice.view.TabLayoutParamSlice;
import com.fission.slice.view.ViewPagerJavaSlice;
import com.fission.slice.view.ViewPagerParamSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/18 18:37
 * Description :
 */
public class FragmentSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "fragment";
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

        Contract annotation = element.getAnnotation(Contract.class);

        if(!annotation.isFragment()){
            return null;
        }

        String elementName;
        if(!"".equals(annotation.name())){
            elementName = annotation.name();
        } else {
            elementName = element.getSimpleName().toString().replaceAll("Contract", "");
        }


        String className = elementName + "Fragment";
        String contractName = elementName + "Contract";
        String presenterName = elementName + "Presenter";

        PackageSlice packageSlice = new PackageSlice(packageName,
                "android.os.Bundle",
                "android.view.View",
                "android.view.ViewGroup",
                "android.view.LayoutInflater",
                "com.musheng.android.common.mvp.BaseFragment",
                "com.alibaba.android.arouter.facade.annotation.Route"
        );

        ClassSlice classSlice = new ClassSlice("public",  "class", className,
                "BaseFragment<"+contractName+".Presenter>", null, contractName+".View");

        MethodSlice initPresenterMethod = new MethodSlice("public", contractName + ".Presenter" ,
                "initPresenter", null,"@Override");
        initPresenterMethod.addSlice(new LineSlice("return new "+presenterName+"();"));

        MethodSlice getRootViewMethod = new MethodSlice("public","View", "getRootView", null,
                "@Override", "inflater$LayoutInflater", "container$ViewGroup", "savedInstanceState$Bundle");
        getRootViewMethod.addSlice(new LineSlice("return inflater.inflate(R.layout.fragment_" + elementName.toLowerCase()
                +", container, false);"));

        MethodSlice initWidgetMethod = new MethodSlice("public","void",
                "initWidget", null, "@Override");
        initWidgetMethod.addSlice(new LineSlice(""));

        initWidgetMethod.addSlice(new TabLayoutJavaSlice());
        initWidgetMethod.addSlice(new ViewPagerJavaSlice());
        initWidgetMethod.addSlice(new RecyclerJavaSlice());

        addSlice(packageSlice);
        classSlice.addSlice(new TabLayoutParamSlice());
        classSlice.addSlice(new ViewPagerParamSlice());
        classSlice.addSlice(new RecyclerParamSlice());
        classSlice.addSlice(initPresenterMethod);
        classSlice.addSlice(getRootViewMethod);
        classSlice.addSlice(initWidgetMethod);
        addSlice(classSlice);

        super.handle(element, roundEnvironment, packageName, config);

        return className + ".java";
    }
}
