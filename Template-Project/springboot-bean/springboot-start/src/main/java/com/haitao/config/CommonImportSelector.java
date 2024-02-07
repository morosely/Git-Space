package com.haitao.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommonImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //读取配舞文件的内容
        List<String> imports = new ArrayList<String>();
        InputStream is = CommonImportSelector.class.getClassLoader().getResourceAsStream("common.imports");
        if(is != null){
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            try {
                while((line = br.readLine()) !=null){
                    imports.add(line);
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }finally {
                if(br!=null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return imports.toArray(new String[imports.size()]);
        }
        return  new String[]{};
        //return new String[]{"com.haitao.config.CommonConfig"};
    }
}
