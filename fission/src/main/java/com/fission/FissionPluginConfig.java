package com.fission;

import com.fission.api.ISlice;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/20 16:19
 * Description : 自定义Slice
 */
public class FissionPluginConfig {

    /** Slice类名全称 **/
    private String name;

    /** route非空时: 嵌入对应的Slice，例:"fragment-initWidget"表示该插件会嵌入FragmentSlice中的InitWidgetSlice中，
     *  route为空时: 成为顶级Slice，请在{@link FissionConfig} 注册对应的注解 **/
    private String route;

    /** 在父Slice中位置
     * @see ISlice#getPriority()  **/
    private int priority;

    public FissionPluginConfig(String name, String route, int priority) {
        this.name = name;
        this.route = route;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

