package com.fission.annotation;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/18 16:20
 * Description :
 */
public @interface Entity {
    String name();
    String[] request() default {};
    String[] response();
    boolean json() default true;
    boolean fetcher() default true;
    boolean objectbox() default false;
    boolean forceRequest() default false;
    boolean forceResponse() default false;
    boolean forceFetcher() default false;
}
