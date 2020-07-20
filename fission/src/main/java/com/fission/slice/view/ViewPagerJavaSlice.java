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
 * CreateDate  : 2020/7/9 16:05
 * Description :
 */
public class ViewPagerJavaSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "tab_layout_java";
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
        if (annotation != null) {
            String s =
                    "typeNameList.clear();\n" +
                            "        tabFragments.clear();\n" +
                            "\n" +
                            "        typeNameList.add(\"Default\");\n" +
                            "\n" +
                            "        tabFragments.add(new androidx.fragment.app.Fragment());\n" +
                            "\n" +
                            "        tabAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {\n" +
                            "            @Override\n" +
                            "            public androidx.fragment.app.Fragment getItem(int position) {\n" +
                            "                return tabFragments.get(position);\n" +
                            "            }\n" +
                            "\n" +
                            "            @Override\n" +
                            "            public int getCount() {\n" +
                            "                return tabFragments.size();\n" +
                            "            }\n" +
                            "\n" +
                            "            @Override\n" +
                            "            public CharSequence getPageTitle(int position) {\n" +
                            "                return typeNameList.get(position);\n" +
                            "            }\n" +
                            "\n" +
                            "            @Override\n" +
                            "            public int getItemPosition(Object object) {\n" +
                            "                return POSITION_NONE;\n" +
                            "            }\n" +
                            "        };\n" +
                            "\n" +
                            "        viewPager.setAdapter(tabAdapter);\n" +
                            "        slidingTabLayout.setViewPager(viewPager);";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}