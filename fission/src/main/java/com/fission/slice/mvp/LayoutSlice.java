package com.fission.slice.mvp;

import com.fission.FissionConfig;
import com.fission.annotation.Contract;
import com.fission.api.AbstractSlice;

import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/21 11:51
 * Description :
 */
public class LayoutSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "layout";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Contract.class;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {

        oldList.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        oldList.add("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        oldList.add("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"");
        oldList.add("    android:orientation=\"vertical\"");
        oldList.add("    android:background=\"#FFFFFF\"");
        oldList.add("    android:layout_width=\"match_parent\"");
        oldList.add("    android:layout_height=\"match_parent\">");

        return oldList;
    }

    @Override
    public List<String> getBottomContents(List<String> oldList) {
        oldList.add("</LinearLayout>");
        return oldList;
    }

    @Override
    public boolean isForceBuild(Element element) {
        Contract annotation = element.getAnnotation(Contract.class);
        return annotation.forceLayout();
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        Contract annotation = element.getAnnotation(Contract.class);
        String elementName;
        if(!"".equals(annotation.name())){
            elementName = annotation.name();
        } else {
            elementName = element.getSimpleName().toString().replaceAll("Contract", "");
        }
        String classname = elementName.toLowerCase() + ".xml";
        classname = (annotation.isFragment() ? "fragment_" : "activity_") + classname;
        return classname;
    }


    @Override
    public String getOutputDir() {
        return "/app/src/main/res/layout";
    }
}
