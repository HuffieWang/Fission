package com.fission.annotation;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/7/9 17:11
 * Description :
 */
public @interface Dialog {
    String name();
    boolean titleText() default false;
    boolean contentText() default false;
    boolean contentInput() default false;
    boolean confirmButton() default false;
    boolean cancelButton() default false;
    boolean forceLayout() default false;
    boolean forceView() default false;
}
