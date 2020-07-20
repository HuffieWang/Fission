package com.fission.slice.view;

import com.fission.FissionConfig;
import com.fission.annotation.Dialog;
import com.fission.api.AbstractSlice;
import com.fission.slice.LineSlice;

import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 17:11
 * Description :
 */
public class DialogXMLSlice extends AbstractSlice {

    @Override
    public String getId() {
        return "dialog_xml";
    }

    @Override
    public Class getTriggerAnnotation() {
        return Dialog.class;
    }
    @Override
    public List<String> getTopContents(List<String> oldList) {

        oldList.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        oldList.add("<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        oldList.add("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"");
        oldList.add("    android:orientation=\"vertical\"");
        oldList.add("    android:background=\"#40000000\"");
        oldList.add("    android:layout_width=\"match_parent\"");
        oldList.add("    android:layout_height=\"match_parent\">");
        oldList.add("    <LinearLayout");
        oldList.add("        android:layout_width=\"match_parent\"");
        oldList.add("        android:layout_height=\"wrap_content\"");
        oldList.add("        android:background=\"#FFFFFF\"");
        oldList.add("        android:orientation=\"vertical\"");
        oldList.add("        android:layout_centerInParent=\"true\"");
        oldList.add("        android:gravity=\"center_horizontal\"");
        oldList.add("        android:layout_marginStart=\"@dimen/dp_15\"");
        oldList.add("        android:layout_marginEnd=\"@dimen/dp_15\">");
        oldList.add(" ");

        return oldList;
    }

    @Override
    public List<String> getBottomContents(List<String> oldList) {
        oldList.add(" ");
        oldList.add("    </LinearLayout>");
        oldList.add("</RelativeLayout>");
        return oldList;
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        Dialog annotation = element.getAnnotation(Dialog.class);

        LineSlice titleText = new LineSlice("    <com.musheng.android.view.MSTextView\n" +
                "            android:id=\"@+id/tv_title\"\n" +
                "            android:layout_width=\"wrap_content\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:layout_marginTop=\"@dimen/dp_5\"\n" +
                "            android:layout_marginStart=\"@dimen/dp_10\"\n" +
                "            android:layout_marginEnd=\"@dimen/dp_10\"\n" +
                "            android:textSize=\"@dimen/dp_16\"\n" +
                "            android:textColor=\"#000000\"\n" +
                "            android:text=\"title\"\n" +
                "            />");

        LineSlice contextText = new LineSlice("    <com.musheng.android.view.MSTextView\n" +
                "            android:id=\"@+id/tv_content\"\n" +
                "            android:layout_width=\"wrap_content\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:layout_marginStart=\"@dimen/dp_10\"\n" +
                "            android:layout_marginEnd=\"@dimen/dp_10\"\n" +
                "            android:textSize=\"@dimen/dp_16\"\n" +
                "            android:textColor=\"#000000\"\n" +
                "            android:text=\"content\"\n" +
                "            android:layout_marginTop=\"@dimen/dp_5\"\n" +
                "            />");

        LineSlice contextInput = new LineSlice("    <com.musheng.android.view.MSEditText\n" +
                "            android:id=\"@+id/et_input\"\n" +
                "            android:layout_width=\"match_parent\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:layout_marginTop=\"@dimen/dp_5\"\n" +
                "            android:layout_marginStart=\"@dimen/dp_10\"\n" +
                "            android:layout_marginEnd=\"@dimen/dp_10\"\n" +
                "            android:gravity=\"center\"\n" +
                "            android:textSize=\"@dimen/dp_16\"\n" +
                "            android:textColor=\"#000000\"\n" +
                "            android:hint=\"hint\"\n" +
                "            />");

        LineSlice cancelButton = new LineSlice("        <com.musheng.android.view.MSTextView\n" +
                "                android:id=\"@+id/tv_cancel\"\n" +
                "                android:layout_width=\"0dp\"\n" +
                "                android:layout_height=\"match_parent\"\n" +
                "                android:layout_weight=\"1\"\n" +
                "                android:gravity=\"center\"\n" +
                "                android:text=\"cancel\"\n" +
                "                android:textColor=\"#000000\"\n" +
                "                android:textSize=\"@dimen/dp_16\"\n" +
                "                />");

        LineSlice confirmButton = new LineSlice("        <com.musheng.android.view.MSTextView\n" +
                "                android:id=\"@+id/tv_confirm\"\n" +
                "                android:layout_width=\"0dp\"\n" +
                "                android:layout_height=\"match_parent\"\n" +
                "                android:layout_weight=\"1\"\n" +
                "                android:gravity=\"center\"\n" +
                "                android:text=\"confirm\"\n" +
                "                android:textColor=\"#000000\"\n" +
                "                android:textSize=\"@dimen/dp_16\"\n" +
                "                />");


        if(annotation.titleText()){
            addSlice(titleText);
        }

        if(annotation.contentText()){
            addSlice(contextText);
        }

        if(annotation.contentInput()){
            addSlice(contextInput);
        }

        if(annotation.cancelButton() || annotation.confirmButton()){
            addSlice(new LineSlice("    <LinearLayout\n" +
                    "            android:layout_width=\"match_parent\"\n" +
                    "            android:layout_height=\"@dimen/dp_44\"\n" +
                    "            android:layout_marginTop=\"@dimen/dp_5\">"));
            if(annotation.cancelButton()){
                addSlice(cancelButton);
            }
            if(annotation.confirmButton()){
                addSlice(confirmButton);
            }
            addSlice(new LineSlice("        </LinearLayout>"));
        }

        return "dialog_" + annotation.name().toLowerCase()+".xml";
    }

    @Override
    public String getOutputDir() {
        return "/app/src/main/res/layout";
    }

    @Override
    public boolean isForceBuild(Element element) {
        Dialog annotation = element.getAnnotation(Dialog.class);
        return annotation.forceLayout();
    }
}