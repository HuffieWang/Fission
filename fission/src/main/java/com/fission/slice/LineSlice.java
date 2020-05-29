package com.fission.slice;

import com.fission.api.AbstractSlice;

import java.util.Arrays;
import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/15 18:35
 * Description :
 */
public class LineSlice extends AbstractSlice {

    private List<String> lines;

    public LineSlice(List<String> lines) {
        this.lines = lines;
    }

    public LineSlice(String... lines) {
        this(Arrays.asList(lines));
    }

    @Override
    public String getId() {
        return "line";
    }

    @Override
    public Class getTriggerAnnotation() {
        return null;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {
        oldList.addAll(lines);
        return oldList;
    }

}
