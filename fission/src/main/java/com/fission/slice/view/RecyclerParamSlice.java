package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.RecyclerView;
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
public class RecyclerParamSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "recycler_param";
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
                    "@BindView(R.id.refresh)\n" +
                            "    SmartRefreshLayout refreshLayout;\n" +
                            "\n" +
                            "    @BindView(R.id.rv)\n" +
                            "    MSRecyclerView recyclerView;\n" +
                            "\n" +
                            "    private List<String> list = new ArrayList<>();\n" +
                            "    private CommonAdapter<String> rvAdapter;\n";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}
