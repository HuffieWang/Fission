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
public class TabLayoutParamSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "tab_layout_param";
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
                    "@BindView(R2.id.tb_main)\n" +
                            "    MSTabLayout tabLayout;\n" +
                            "\n" +
                            "    ArrayList<androidx.fragment.app.Fragment> fragments = new ArrayList<>();\n";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
