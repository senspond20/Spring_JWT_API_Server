package com.sens.pot.common.helper;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialTypeConverter implements Converter<String,SocialType> {
    /**
     * SocialLoginType 소문자로 변환
     */

   /* private String[] codeArray = {"AC001","AC002","AC003","AC004","AC005"}; 

    @Override
    public String convert(SocialType type) {
        String code = codeArray[0];
        if(type.equals(SocialType.GOOGLE))  code = codeArray[1];
        if(type.equals(SocialType.NAVER))   code = codeArray[2];
        if(type.equals(SocialType.KAKAO))   code = codeArray[3];
        if(type.equals(SocialType.FACEBOOK)) code = codeArray[4];
        return code;
    }*/
    @Override
    public SocialType convert(String s) {
        return SocialType.valueOf(s.toLowerCase());
    }

}

