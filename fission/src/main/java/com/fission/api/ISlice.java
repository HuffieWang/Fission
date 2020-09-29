package com.fission.api;

import com.fission.FissionConfig;

import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/14 18:52
 * Description :
 */
public interface ISlice {

    /**
     * 唯一标识
     * **/
    String getId();

    /**
     * 触发注解，在{@link FissionConfig}中注册后成为顶级Slice。
     * **/
    Class getTriggerAnnotation();

    /**
     * 设置排版优先级，数值越小优先级越高，优先级最高的Slice生成在文件的顶部。
     * **/
    void setPriority(int priority);
    int getPriority();

    /**
     * 设置排版缩进深度，每个数值对应四个空格符。
     * **/
    void setDeep(int deep);
    int getDeep();
    boolean isEqualParentDeep();

    /**
     * 默认文件输出路径
     * **/
    String getOutputDir();
    String getOutputDir(String moduleName);

    /**
     * 设置自定义文件输出路径，优先级比默认路径高
     * **/
    void setExtOutputDir(String outputDir);
    String getExtOutputDir();

    /**
     * 添加子Slice
     * **/
    void addSlice(ISlice slice);
    List<ISlice> getSliceList();

    /**
     * 根据ID链寻找子Slice
     * **/
    ISlice findSlice(List<String> idRoutes);

    /**
     * 顶部文本，列表中的每个item输出一行文本
     * **/
    List<String> getTopContents(List<String> oldList);

    /**
     * 底部文本，列表中的每个item输出一行文本
     * **/
    List<String> getBottomContents(List<String> oldList);

    /**
     * 整体文本，包含顶部文本、子Slice生成的文本和底部文本
     * **/
    String getContent();

    /**
     * 根据输入的数据，对顶部文本、子Slice文本和底部文本进行配置
     * @param element 包含触发注解的元素
     * @param roundEnvironment 所有包含触发注解的元素
     * @param packageName 生成的类的包名
     * @param config 配置文件
     * **/
    String handle(Element element, RoundEnvironment roundEnvironment, String packageName, FissionConfig config);

    /**
     * @return 当文件已存在时强制覆盖
     * **/
    boolean isForceBuild(Element element);

    /**
     * 文本排版
     * **/
    ISlice build();

    /**
     * 重置所有子Slice
     * **/
    void reset();
}
