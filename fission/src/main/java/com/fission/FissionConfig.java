package com.fission;

import java.util.List;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/18 14:59
 * Description : 1.FissionConfig会自动生成在工程根目录；
 *               2.强制：请把FissionConfig中的packageName修改为你的App包名；
 *               3.可选：自定义的注解、插件{@link FissionPluginConfig}和文件生成路径{@link FissionOutputConfig}。
 * 示例：
    {
        "packageName":"com.musheng.fissionpre",
        "annotations":[
            "com.musheng.dev.Adapter"
        ],
        "plugins":[
            {"name":"com.musheng.dev.AdapterSlice", "route":"fragment-class", "priority":2}
        ],
        "extOutputs":[
            {"id":"entity", "filepath":"../entity"}
        ]
    }
 */
public class FissionConfig {

    /** app包名 **/
    private String packageName;

    /** 开发者自定义的注解 **/
    private List<String> annotations;

    /** 开发者自定义的Slice **/
    private List<FissionPluginConfig> plugins;

    /** 强制更改指定Slice的输出目录 **/
    private List<FissionOutputConfig> extOutputs;

    public FissionConfig(String packageName, List<String> annotations, List<FissionPluginConfig> plugins, List<FissionOutputConfig> extOutputs) {
        this.packageName = packageName;
        this.annotations = annotations;
        this.plugins = plugins;
        this.extOutputs = extOutputs;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public List<FissionPluginConfig> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<FissionPluginConfig> plugins) {
        this.plugins = plugins;
    }

    public List<FissionOutputConfig> getExtOutputs() {
        return extOutputs;
    }

    public void setExtOutputs(List<FissionOutputConfig> extOutputs) {
        this.extOutputs = extOutputs;
    }
}
