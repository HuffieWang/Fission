package com.fission;

import com.fission.api.ISlice;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/20 16:18
 * Description : Slice输出目录配置，优先级高于Slice的默认输出目录。
 */
public class FissionOutputConfig {

    /** 功能ID
     * @see ISlice#getId()  **/
    private String id;

    /** 文件输出目录
     * @see ISlice#getExtOutputDir() **/
    private String filepath;

    public FissionOutputConfig(String id, String filepath) {
        this.id = id;
        this.filepath = filepath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
