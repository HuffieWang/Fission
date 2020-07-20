package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.Dialog;
import com.fission.api.AbstractSlice;
import com.fission.slice.ClassSlice;
import com.fission.slice.LineSlice;
import com.fission.slice.MethodSlice;
import com.fission.slice.PackageSlice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 17:12
 * Description :
 */
public class DialogJavaSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "dialog_java";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Dialog.class;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        super.handle(element, roundEnvironment, packageName, config);

        Dialog annotation = element.getAnnotation(Dialog.class);

        List<String> imports = new ArrayList<>();
        imports.add("android.view.View");
        imports.add("android.content.Context");

        PackageSlice packageSlice = new PackageSlice(packageName, imports);

        ClassSlice classSlice = new ClassSlice("public", "class", annotation.name()+"Dialog",
                "razerdp.basepopup.BasePopupWindow", null);

        MethodSlice createPopupById = new MethodSlice("public", "View", "onCreateContentView",
                null, "@Override");

        createPopupById.addSlice(new LineSlice("return createPopupById(R.layout.dialog_"+annotation.name().toLowerCase()+");"));

        MethodSlice showPopupWindow = new MethodSlice("public", "void", "showPopupWindow",
                null, "@Override");
        if(annotation.titleText()){
            showPopupWindow.addSlice(new LineSlice("MSTextView titleText = getContentView().findViewById(R.id.tv_title);\n"));
        }
        if(annotation.contentText()){
            showPopupWindow.addSlice(new LineSlice("MSTextView contentText = getContentView().findViewById(R.id.tv_content);\n"));
        }
        if(annotation.contentInput()){
            showPopupWindow.addSlice(new LineSlice("MSEditText contentInput = getContentView().findViewById(R.id.et_input);\n"));
        }
        if(annotation.cancelButton()){
            showPopupWindow.addSlice(new LineSlice("MSTextView cancelText = getContentView().findViewById(R.id.tv_cancel);\n" +
                    "        cancelText.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                dismiss();\n" +
                    "            }\n" +
                    "        });\n"));
        }
        if(annotation.confirmButton()){
            showPopupWindow.addSlice(new LineSlice("MSTextView confirmText = getContentView().findViewById(R.id.tv_confirm);\n" +
                    "        confirmText.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                dismiss();\n" +
                    "            }\n" +
                    "        });\n"));
        }

        showPopupWindow.addSlice(new LineSlice("super.showPopupWindow();"));

        classSlice.addSlice(new LineSlice("public "+annotation.name()+"Dialog(Context context){\n" +
                "        super(context);\n" +
                "    }\n"));

        classSlice.addSlice(createPopupById);
        classSlice.addSlice(showPopupWindow);

        addSlice(packageSlice);
        addSlice(classSlice);

        return annotation.name() + "Dialog.java";
    }

    @Override
    public boolean isForceBuild(Element element) {
        Dialog annotation = element.getAnnotation(Dialog.class);
        return annotation.forceView();
    }
}
