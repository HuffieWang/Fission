package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.TabLayout;
import com.fission.annotation.ViewPager;
import com.fission.api.AbstractSlice;
import com.fission.slice.LineSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 15:12
 * Description :
 */
public class ViewPagerXMLSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "view_pager_xml";
    }

    @Override
    public Class getTriggerAnnotation() {
        return ViewPager.class;
    }

    @Override
    public boolean isEqualParentDeep() {
        return true;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        ViewPager annotation = element.getAnnotation(ViewPager.class);
        if(annotation != null){
            String s =
                    "<com.musheng.android.view.MSSlidingTabLayout\n" +
                            "        android:id=\"@+id/tb\"\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"@dimen/dp_44\"\n" +
                            "        app:tl_textSelectColor=\"#41BCCA\"\n" +
                            "        app:tl_textUnselectColor=\"#CAD3D7\"\n" +
                            "        app:tl_indicator_style=\"NORMAL\"\n" +
                            "        app:tl_indicator_color=\"#41BCCA\"\n" +
                            "        app:tl_indicator_height=\"@dimen/dp_3\"\n" +
                            "        app:tl_indicator_corner_radius=\"@dimen/dp_2\"\n" +
                            "        app:tl_textsize=\"@dimen/dp_14\"\n" +
                            "        app:tl_tab_space_equal=\"true\"\n" +
                            "        />\n" +
                            "\n" +
                            "    <com.musheng.android.view.MSViewPager\n" +
                            "        android:id=\"@+id/vp\"\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"match_parent\"\n" +
                            "        />"
                    ;
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
