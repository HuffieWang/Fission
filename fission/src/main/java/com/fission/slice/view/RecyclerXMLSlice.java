package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.RecyclerView;
import com.fission.api.AbstractSlice;
import com.fission.slice.LineSlice;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 15:12
 * Description :
 */
public class RecyclerXMLSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "recycler_xml";
    }

    @Override
    public Class getTriggerAnnotation() {
        return RecyclerView.class;
    }

    @Override
    public boolean isEqualParentDeep() {
        return true;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        RecyclerView annotation = element.getAnnotation(RecyclerView.class);
        if(annotation != null){
            String s =
                    "<com.scwang.smartrefresh.layout.SmartRefreshLayout\n" +
                            "        android:id=\"@+id/refresh\"\n" +
                            "        android:layout_width=\"match_parent\"\n" +
                            "        android:layout_height=\"match_parent\">\n" +
                            "\n" +
                            "        <com.musheng.android.view.MSRecyclerView\n" +
                            "            android:id=\"@+id/rv\"\n" +
                            "            android:layout_width=\"match_parent\"\n" +
                            "            android:layout_height=\"match_parent\" />\n" +
                            "\n" +
                            "    </com.scwang.smartrefresh.layout.SmartRefreshLayout>"
                    ;
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
