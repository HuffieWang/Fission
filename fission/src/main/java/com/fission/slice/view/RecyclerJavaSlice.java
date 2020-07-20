package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.RecyclerView;
import com.fission.api.AbstractSlice;
import com.fission.slice.LineSlice;
import com.fission.util.FLogUtil;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 16:05
 * Description :
 */
public class RecyclerJavaSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "recycler_java";
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
        if (annotation != null) {
            String s =
                    "setSmartRefreshLayout(refreshLayout);\n" +
                            "        rvAdapter = new CommonAdapter<String>(getViewContext(), R.layout.activity_main, list) {\n" +
                            "            @Override\n" +
                            "            protected void convert(ViewHolder holder, String messageEntity, int position) {\n" +
                            "\n" +
                            "            }\n" +
                            "        };\n" +
                            "        recyclerView.setAdapter(rvAdapter);";
            addSlice(new LineSlice(s));
        }
        return super.handle(element, roundEnvironment, packageName, config);
    }
}