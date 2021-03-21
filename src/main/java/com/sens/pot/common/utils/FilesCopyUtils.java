package com.sens.pot.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesCopyUtils {
    private List<File> originFileList = null;
    private List<File> newFileList = null;
    private Boolean isCanRun = false;
    
    public FilesCopyUtils(){
    }

    public void setFileLists(String orginDir, String copyDir){
        originFileList = new ArrayList<>();
        newFileList = new ArrayList<>();
        makeCopyFileList(orginDir,copyDir);
        isCanRun = true;
    }

    public void setFileLists(String orginDir, String copyDir, String suffix){
        originFileList = new ArrayList<>();
        newFileList = new ArrayList<>();
        makeCopyFileList(orginDir,copyDir, suffix);
        isCanRun = true;
    }    

    public List<File> getOriginFileList(){
        return this.originFileList;
    }
    public List<File> getNewFileList(){
        return this.newFileList;
    }

    /**
     * 대량 복사 진행
     */
    public void run(){
        if(isCanRun){
            for(int idx = 0; idx < originFileList.size(); idx++){
                fileCopy(originFileList.get(idx), newFileList.get(idx));
            }
        }
    }

    /**
     * 해당 디렉토리 경로(하위 디렉토리 포함) 모든 파일목록과 복사될 파일 목록
     * - 필터링 없음
     * @param orginDir
     * @param copyDir
     */
    private void makeCopyFileList(String orginDir, String copyDir) {
        makeCopyFileList(orginDir,copyDir, null);
    }

    /**
     * -- 필터링 있음
     * @param orginDir
     * @param copyDir
     * @param fileFilter
     */
    private void makeCopyFileList(String orginDir, String copyDir, String suffix) {
        if(orginDir == null || copyDir == null){
            return;
        }
        File dir = new File(orginDir);
       
        File files[] = dir.listFiles();
        File newFile = null;

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            
            // 디렉토리 이면
            if (file.isDirectory()) {
                
                // 재귀 호출
                makeCopyFileList(file.getPath(), copyDir, suffix);
            } else {
                String parent = file.getParent();
                String newDir = parent.substring(parent.lastIndexOf("\\") + 1);
                newFile = new File(Paths.get(copyDir, newDir, file.getName()).toString());

                if(suffix == null || file.getName().endsWith(suffix)){
                    this.originFileList.add(file);
                    this.newFileList.add(newFile);
                }
            }
        }
    }
       
    
    public void fileCopy(File orignFile, File newFile){

        // 복사할 경로의 폴더가 존재하지 않으면 폴더들 생성
        File parent = new File(newFile.getParent());
        if(!parent.exists()){ 
            parent.mkdirs();
        }
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

    public void clear(String copyDir) {
        File file = new File(copyDir);
        file.delete();
    }



}
