package com.fission.slice;

import com.fission.api.AbstractSlice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/14 18:55
 * Description :
 */
public class PackageSlice extends AbstractSlice {

    private List<String> packageList;

    public PackageSlice(String packageName, String... imports) {
        this(packageName, Arrays.asList(imports));
    }

    public PackageSlice(String packageName, List<String> imports) {
        packageList = new ArrayList<>();
        packageList.add("package " + packageName + ";");
        if(imports != null && !imports.isEmpty()){
            for(String s : imports){
                packageList.add("import " + s + ";");
            }
        }
    }

    @Override
    public String getId() {
        return "package";
    }

    @Override
    public Class getTriggerAnnotation() {
        return null;
    }

    @Override
    public List<String> getTopContents(List<String> oldList) {
        oldList.addAll(packageList);
        return oldList;
    }

    @Override
    public List<String> getBottomContents(List<String> oldList) {
        oldList.add("\n");
        return oldList;
    }

}
