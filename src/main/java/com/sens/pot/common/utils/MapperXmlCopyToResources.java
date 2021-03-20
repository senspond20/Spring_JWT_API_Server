package com.sens.pot.common.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;

public class MapperXmlCopyToResources {
    
    private final static String APP_ROOT = System.getProperty("user.dir");
    private final static String ORIGIN_PATH = "src/main/java/com/sens/pot/web/repository";
    private final static String COPY_PATH ="src/main/resources/mapper";


    public static void main(String[] args) {

        String orginDir = Paths.get(APP_ROOT, ORIGIN_PATH).toString();
        String copyDir = Paths.get(APP_ROOT, COPY_PATH).toString();
        FilesCopyUtils fc = new FilesCopyUtils();

        // fc.setFileLists(orginDir, copyDir);
        // fc.setFileLists(orginDir, copyDir, ".java");
        fc.setFileLists(orginDir, copyDir , ".xml");
        
        System.out.println("[파일 목록]");
        fc.getOriginFileList().stream().forEach(System.out::println);
        System.out.println("을");
        
        fc.getNewFileList().stream().forEach(System.out::println);
        System.out.println("로 복사를 진행하겠습니다");

        // fc.run();        

    }
}
