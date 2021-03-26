package com.sens.pot.common.helper.converter;


import com.sens.pot.common.helper.constants.SocialLoginType;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
    /**
     * SocialLoginType 소문자로 변환
     */
    @Override
    public SocialLoginType convert(String s) {
        return SocialLoginType.valueOf(s.toLowerCase());
    }
}

