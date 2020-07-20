package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.TabLayout;
import com.fission.api.AbstractSlice;
import com.fission.slice.LineSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 15:12
 * Description :
 */
public class TabLayoutXMLSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "tab_layout_xml";
    }

    @Override
    public Class getTriggerAnnotation() {
        return TabLayout.class;
    }

    @Override
    public boolean isEqualParentDeep() {
        return true;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        TabLayout annotation = element.getAnnotation(TabLayout.class);
        if(annotation != null){
            String s =
                    "<RelativeLayout\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"match_parent\">\n" +
                            "        \n" +
                            "        <FrameLayout\n" +
                            "            android:id=\"@+id/layout_main\"\n" +
                            "            android:layout_width=\"match_parent\"\n" +
                            "            android:layout_height=\"match_parent\"\n" +
                            "            android:layout_above=\"@id/div_main\"\n" +
                            "            />\n" +
                            "\n" +
                            "        <View\n" +
                            "            android:id=\"@+id/div_main\"\n" +
                            "            android:layout_width=\"match_parent\"\n" +
                            "            android:layout_height=\"@dimen/dp_0_5\"\n" +
                            "            android:background=\"#BFC2CC\"\n" +
                            "            android:layout_above=\"@id/tb_main\"/>\n" +
                            "\n" +
                            "        <com.musheng.android.view.MSTabLayout\n" +
                            "            android:id=\"@+id/tb_main\"\n" +
                            "            android:layout_width=\"match_parent\"\n" +
                            "            android:layout_height=\"@dimen/dp_70\"\n" +
                            "            android:paddingStart=\"@dimen/dp_20\"\n" +
                            "            android:paddingEnd=\"@dimen/dp_20\"\n" +
                            "            android:background=\"#F5F7FA\"\n" +
                            "            android:layout_alignParentBottom=\"true\"\n" +
                            "            app:tl_textsize=\"@dimen/dp_12\"\n" +
                            "            app:tl_textSelectColor=\"#FFFFFF\"\n" +
                            "            app:tl_textUnselectColor=\"#F2F2F2\"\n" +
                            "            app:tl_tab_space_equal=\"true\"\n" +
                            "            app:tl_iconWidth=\"@dimen/dp_23\"\n" +
                            "            app:tl_iconHeight=\"@dimen/dp_23\"\n" +
                            "            app:tl_iconGravity=\"TOP\"\n" +
                            "            app:tl_indicator_color=\"#00000000\"\n" +
                            "            />\n" +
                            "    </RelativeLayout>";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
