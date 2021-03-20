package com.sens.pot.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapperCopy {
    
    private final static String APP_ROOT = System.getProperty("user.dir");
    private final static String ORIGIN_PATH = "src/main/java/com/sens/pot/web/repository";
    private final static String COPY_PATH ="src/main/resources/mapper";

    private static List<File> originFileList = new ArrayList<>();
    private static List<File> newFileList = new ArrayList<>();
    
    public static void makeCopyFileList(String orginDir, String copyDir) {
        File dir = new File(orginDir);
        File files[] = dir.listFiles();
        File newFile = null;

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            
            // 디렉토리 이면
            if (file.isDirectory()) {
                
                // 재귀 호출
                makeCopyFileList(file.getPath(), copyDir);
            } else {
                String parent = file.getParent();
                String newDir = parent.substring(parent.lastIndexOf("\\") + 1);
                newFile = new File(Paths.get(copyDir, newDir, file.getName()).toString());

                originFileList.add(file);
                newFileList.add(newFile);
            }
        }
    }
    
    public static void fileCopy(File orignFile, File newFile){

        File parent = new File(newFile.getParent());
        if(!parent.exists()){
            parent.mkdirs();
        }


        // Path newPath = Paths.get(APP_ROOT, COPY_PATH, "test");
        // File newFile = new File(newPath.toString());
        try (
            FileInputStream fis = new FileInputStream(orignFile); //읽을파일
            FileOutputStream fos = new FileOutputStream(newFile); //복사할파일
        ) {
            int fileByte = 0; 
            // fis.read()가 -1 이면 파일을 다 읽은것
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //자원사용종료
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static void main(String[] args) {
    
        Path originPath = Paths.get(APP_ROOT, ORIGIN_PATH);
        Path newPath = Paths.get(APP_ROOT, COPY_PATH);

        makeCopyFileList(originPath.toString(), newPath.toString());

        System.out.println("=== xml 파일 복사 ===");
        originFileList.stream().forEach(System.out::println);
        System.out.println("을");
        
        newFileList.stream().forEach(System.out::println);
        System.out.println("로 복사 합니다.");


       
    }
}
