package com.fission.cps;

import com.fission.FissionConfig;
import com.fission.api.AbstractSlice;
import com.fission.slice.LineSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/6/11 14:00
 * Description :
 */
public class TopBarLayoutNewSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "top_bar_layout_new";
    }

    @Override
    public Class getTriggerAnnotation() {
        return TopBar.class;
    }

    @Override
    public boolean isEqualParentDeep() {
        return true;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        TopBar annotation = element.getAnnotation(TopBar.class);

        if(annotation != null || true){
            StringBuilder builder = new StringBuilder();
            builder.append("\n").append("    <com.newcps.view.MSTopBar").append("\n")
                    .append("        android:id=\"@+id/top_bar\"").append("\n")
                    .append("        android:layout_width=\"match_parent\"").append("\n")
                    .append("        android:layout_height=\"wrap_content\"").append("\n")
                    .append("        app:ms_top_title=\"\"").append("\n")
                    .append("        />").append("\n")
                    .append("    <View").append("\n")
                    .append("        android:layout_width=\"match_parent\"").append("\n")
                    .append("        android:layout_height=\"@dimen/dp_0_5\"").append("\n")
                    .append("        android:background=\"#BFC4CC\"").append("\n")
                    .append("        />").append("\n")
            ;

            addSlice(new LineSlice(builder.toString()));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
