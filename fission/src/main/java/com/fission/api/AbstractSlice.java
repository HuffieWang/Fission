package com.fission.api;

import com.fission.FissionConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/14 18:52
 * Description :
 */
public abstract class AbstractSlice implements ISlice {

    public static String DEEP_SPACING = "    ";

    private int deep;
    private int priority;
    private String outputDir;
    private String deepPadding;
    private List<ISlice> sliceList = new ArrayList<>();

    private List<String> topContentList = new ArrayList<>();
    private List<String> bottomContentList = new ArrayList<>();

    @Override
    public void addSlice(ISlice container) {
        sliceList.add(container);
    }

    @Override
    public List<ISlice> getSliceList() {
        return sliceList;
    }

    @Override
    public void setDeep(int deep) {
        this.deep = deep;

        for(int i = 0; i < sliceList.size(); i++){
            ISlice slice = sliceList.get(i);
            if(slice.getPriority() == 0){
                slice.setPriority(i+1);
            }
        }

        Collections.sort(sliceList, new Comparator<ISlice>() {
            @Override
            public int compare(ISlice o1, ISlice o2) {
                return Integer.compare(o1.getPriority(), o2.getPriority());
            }
        });

        if(sliceList != null && !sliceList.isEmpty()){
            for(ISlice slice : sliceList){
                if(slice.isEqualParentDeep()){
                    slice.setDeep(deep);
                } else {
                    slice.setDeep(deep+1);
                }
            }
        }

        if(deep > 0){
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < deep; i++){
                builder.append(DEEP_SPACING);
            }
            deepPadding = builder.toString();
        } else {
            deepPadding = "";
        }
    }

    @Override
    public int getDeep() {
        return deep;
    }

    @Override
    public boolean isEqualParentDeep(){
        return false;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {
        return null;
    }

    @Override
    public List<String> getBottomContents(List<String> oldList) {
        return null;
    }

    @Override
    public String getContent(){
        StringBuilder builder = new StringBuilder();
        topContentList.clear();
        List<String> topContents = getTopContents(topContentList);
        if(topContents != null && !topContents.isEmpty()){
            for(String topContent : topContents){
                if(topContent.equals("\n")){
                    builder.append("\n");
                } else {
                    builder.append(getDeepPadding()).append(topContent).append("\n");
                }
            }
        }
        if(sliceList != null && !sliceList.isEmpty()){
            for(ISlice slice : sliceList){
                builder.append(slice.getContent());
            }
        }
        bottomContentList.clear();
        List<String> bottomContents = getBottomContents(bottomContentList);
        if(bottomContents != null && !bottomContents.isEmpty()){
            for(String bottomContent : bottomContents){
                if(bottomContent.equals("\n")){
                    builder.append("\n");
                } else {
                    builder.append(getDeepPadding()).append(bottomContent).append("\n");
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void reset() {
        sliceList.clear();
    }

    @Override
    public String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config) {
        for(ISlice slice : sliceList){
            slice.handle(element, roundEnvironment, packageName, config);
        }
        return null;
    }

    @Override
    public boolean isForceBuild(Element element) {
        return false;
    }

    @Override
    public ISlice build() {
        if(getTopContents(new ArrayList<>()) == null
                && getBottomContents(new ArrayList<>()) == null){
            setDeep(-1);
        } else {
            setDeep(0);
        }
        return this;
    }

    @Override
    public void setExtOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public String getExtOutputDir() {
        return outputDir;
    }

    @Override
    public String getOutputDir() {
        return "./";
    }

    public String getDeepPadding(){
        return deepPadding;
    }

    @Override
    public ISlice findSlice(List<String> idRoutes) {
        if(idRoutes == null || idRoutes.isEmpty()){
            return null;
        }
        if(idRoutes.get(0).equals(getId())){
            if(idRoutes.size() == 1){
                return this;
            }
            idRoutes = idRoutes.subList(1, idRoutes.size());
            for(ISlice slice : sliceList) {
                ISlice childFind = ((AbstractSlice) slice).findSlice(idRoutes);
                if(childFind != null) {
                    return childFind;
                }
            }
        }
        return null;
    }

}
