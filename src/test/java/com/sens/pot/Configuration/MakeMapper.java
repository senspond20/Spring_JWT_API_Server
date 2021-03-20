package com.sens.pot.Configuration;

import java.nio.file.Paths;

import com.sens.pot.common.utils.FilesCopyUtils;

import org.junit.jupiter.api.Test;

public class MakeMapper {

    @Test
	void makeMapperXML(){
		final String APP_ROOT = System.getProperty("user.dir");
		final String ORIGIN_PATH = "src/main/java/com/sens/pot/web/repository";
		final String COPY_PATH ="src/main/resources/mapper";
		final String orginDir = Paths.get(APP_ROOT, ORIGIN_PATH).toString();
		final String copyDir = Paths.get(APP_ROOT, COPY_PATH).toString();

		FilesCopyUtils fc = new FilesCopyUtils();

		fc.clear(copyDir);
		fc.setFileLists(orginDir, copyDir , ".xml");
        System.out.println("[파일 목록]");
        fc.getOriginFileList().stream().forEach(System.out::println);
        System.out.println("을");
        
        fc.getNewFileList().stream().forEach(System.out::println);
        System.out.println("로 복사를 진행하겠습니다");
        
		fc.run();        
		fc = null;
	} 
}
