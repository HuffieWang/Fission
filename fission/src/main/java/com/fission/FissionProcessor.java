package com.fission;

import com.fission.annotation.Contract;
import com.fission.annotation.Entity;
import com.fission.annotation.ForceBuild;
import com.fission.api.ISlice;
import com.fission.slice.fetcher.ApiSlice;
import com.fission.slice.fetcher.EntitySlice;
import com.fission.slice.fetcher.FetcherSlice;
import com.fission.slice.fetcher.RequestSlice;
import com.fission.slice.mvp.ActivitySlice;
import com.fission.slice.mvp.FragmentSlice;
import com.fission.slice.mvp.LayoutSlice;
import com.fission.slice.mvp.PresenterSlice;
import com.fission.slice.mvp.RouterSlice;
import com.fission.util.FSystemUtil;
import com.google.auto.service.AutoService;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class FissionProcessor extends AbstractProcessor {

    /** 支持的注解集合 **/
    private Set<String> annotations = new LinkedHashSet<>();

    /** 支持的Slice集合 **/
    private Set<ISlice> slices = new LinkedHashSet<>();

    /** 配置文件 **/
    private FissionConfig fissionConfig;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return annotations;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        String configPath = FSystemUtil.getProjectDir() + "/FissionConfig";
        try {
            File file = new File(configPath);
            Gson gson = new Gson();
            if(file.exists()){
                FileReader fileReader = new FileReader(file);
                fissionConfig = gson.fromJson(fileReader, FissionConfig.class);
            } else {
                String json = generateDefaultConfig(file);
                fissionConfig = gson.fromJson(json, FissionConfig.class);
            }
            initAnnotations(fissionConfig);

            initSlices(fissionConfig);

            initOutputs(fissionConfig);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(configPath + " error");
        }
    }

    private String generateDefaultConfig(File file){
        StringBuilder builder = new StringBuilder();
        builder.append("{").append("\n");
        builder.append("    ").append("\"packageName\":\"com.fission\",").append("\n");
        builder.append("    ").append("\"annotations\":[").append("\n");
        builder.append("        ").append("\"com.fission.sample.Adapter\"").append("\n");
        builder.append("    ").append("],").append("\n");
        builder.append("    ").append("\"plugins\":[").append("\n");
        builder.append("        ").append("{\"name\":\"com.fission.sample.AdapterSlice\", \"route\":\"fragment-class\", \"priority\":2}").append("\n");
        builder.append("    ").append("],").append("\n");
        builder.append("    ").append("\"extOutputs\":[").append("\n");
        builder.append("        ").append("{\"id\":\"entity\", \"filepath\":\"../entity\"}").append("\n");
        builder.append("    ").append("]").append("\n");
        builder.append("}");
        String content = builder.toString();
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            writer.close();
            return content;
        } catch (Exception ignored) {
        }
        return null;
    }

    private void initAnnotations(FissionConfig fissionConfig){
        annotations.clear();

        /** 默认加载：MVP快速构建 **/
        annotations.add(Contract.class.getCanonicalName());

        /** 默认加载：网络请求自动生成 **/
        annotations.add(Entity.class.getCanonicalName());

        /** 加载开发者自定义的注解 **/
        annotations.addAll(fissionConfig.getAnnotations());
    }

    private void initSlices(FissionConfig fissionConfig){
        slices.clear();

        /** 默认加载：MVP快速构建 **/
        slices.add(new ActivitySlice());
        slices.add(new FragmentSlice());
        slices.add(new PresenterSlice());
        slices.add(new RouterSlice());
        slices.add(new LayoutSlice());

        /** 默认加载：网络请求自动生成 **/
        slices.add(new EntitySlice());
        slices.add(new FetcherSlice());
        slices.add(new RequestSlice());
        slices.add(new ApiSlice());

        /** 加载开发者自定义的Slice **/
        if(fissionConfig.getPlugins() != null){
            for(FissionPluginConfig config : fissionConfig.getPlugins()){
                if(config.getRoute() == null) {
                    try {
                        Class<?> aClass = Class.forName(config.getName());
                        ISlice slice = (ISlice) aClass.newInstance();
                        slices.add(slice);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /** 加载开发者自定义的输出路径 **/
    private void initOutputs(FissionConfig fissionConfig){

        HashMap<String, String> outputsMap = new HashMap<>();

        if(fissionConfig.getExtOutputs() != null && !fissionConfig.getExtOutputs().isEmpty()){
            for(FissionOutputConfig config : fissionConfig.getExtOutputs()){
                outputsMap.put(config.getId(), config.getFilepath());
            }
        }

        for(ISlice slice : slices){
            if(outputsMap.containsKey(slice.getId())){
                slice.setExtOutputDir(outputsMap.get(slice.getId()));
            }
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set == null || set.isEmpty() || fissionConfig == null) {
            return true;
        }

        for(ISlice slice : slices) {

            Set<Element> elements = roundEnvironment.getElementsAnnotatedWith(slice.getTriggerAnnotation());

            if (elements != null && !elements.isEmpty()) {

                for (Element element : elements) {

                    slice.reset();

                    String outputDir = getOutputDir(slice, element);
                    if(outputDir == null){
                        continue;
                    }

                    String packageName = getPackageName(outputDir);

                    String filename = slice.handle(element, roundEnvironment, packageName, fissionConfig);
                    if(filename == null){
                        continue;
                    }

                    String content = slice.build().getContent();

                    writeFile(outputDir, filename, slice, element, content);
                }
            }
        }
        return true;
    }

    private String getOutputDir(ISlice slice, Element element){

        String outputDir = slice.getExtOutputDir();
        if (outputDir == null) {
            if(slice.getOutputDir()== null){
                return null;
            }
            outputDir = slice.getOutputDir();
        }

        String mainDir = FSystemUtil.getProjectDir();
        if (outputDir.startsWith("/")) {
            outputDir = mainDir + outputDir;

        } else {

            String pattern = outputDir;
            mainDir = mainDir + "\\app\\src\\main\\java";
            String classpath;
            if(element.getKind().isClass() || element.getKind().isInterface()){
                classpath = FSystemUtil.getSubPackageName(element.toString());
            } else {
                classpath = FSystemUtil.getSubPackageName(element.getEnclosingElement().toString());
            }
            classpath = mainDir + "\\" + classpath;

            classpath = classpath.replaceAll("\\.", "\\\\");

            outputDir = FSystemUtil.patternPath(classpath, pattern);
        }
        outputDir = outputDir.replaceAll("/", "\\\\");
        outputDir = outputDir.endsWith("\\") ? outputDir : outputDir + "\\";
        return outputDir;
    }

    private String getPackageName(String outputDir){
        String packageName = "";
        if(outputDir.contains("\\app\\src\\main\\java")){
            packageName = outputDir.substring(outputDir.indexOf("\\app\\src\\main\\java") + "\\app\\src\\main\\java".length() + 1);
            packageName = packageName.replaceAll("\\\\", ".");
            packageName = packageName.substring(0, packageName.length()-1);
        }
        return packageName;
    }

    private void writeFile(String outputDir, String filename, ISlice slice, Element element, String content){
        File outputDirFile = new File(outputDir);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }
        ForceBuild annotation = element.getAnnotation(ForceBuild.class);
        File outputFile = new File(outputDir + filename);
        if(outputFile.exists() && (annotation == null || !annotation.enable()) && !slice.isForceBuild(element)){
            return;
        }
        if(outputFile.exists()){
            outputFile.delete();
        }
        try {
            outputFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
