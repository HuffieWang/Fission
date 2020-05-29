package com.fission.annotation;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/21 14:30
 * Description :
 */
public @interface ForceBuild {
    boolean enable() default true;
}
