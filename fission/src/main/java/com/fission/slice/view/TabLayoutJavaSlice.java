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
public class TabLayoutJavaSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "tab_layout_java";
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
                    "fragments.clear();\n" +
                            "        fragments.add(new androidx.fragment.app.Fragment());\n\n" +
                            "        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();\n" +
                            "        tabEntities.add(new MSTabLayout.MSTabEntity(\"\", R.mipmap.ic_launcher, R.mipmap.ic_launcher));\n\n" +
                            "        tabLayout.setTabData(tabEntities,this, R.id.layout_main, fragments);";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
