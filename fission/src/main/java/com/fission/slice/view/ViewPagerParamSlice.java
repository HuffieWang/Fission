package com.fission.slice.view;

import com.fission.FissionConfig;
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
public class ViewPagerParamSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "view_pager_param";
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
                    "@BindView(R.id.tb)\n" +
                            "    MSSlidingTabLayout slidingTabLayout;\n" +
                            "\n" +
                            "    @BindView(R.id.vp)\n" +
                            "    MSViewPager viewPager;\n" +
                            "\n" +
                            "    private FragmentStatePagerAdapter tabAdapter;\n" +
                            "    private ArrayList<androidx.fragment.app.Fragment> tabFragments = new ArrayList<>();\n" +
                            "    private List<String> typeNameList = new ArrayList<>();\n";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
