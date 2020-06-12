package com.fission.annotation;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/15 14:40
 * Description :
 */
public @interface Contract {
    String name() default "";

    boolean isFragment() default false;

    boolean forceView() default false;

    boolean forceLayout() default false;

    boolean forcePresenter() default false;
}
