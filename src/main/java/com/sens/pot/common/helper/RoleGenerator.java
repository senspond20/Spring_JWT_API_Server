package com.sens.pot.common.helper;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


public class RoleGenerator implements IdentifierGenerator{
    final String prefix= "RO";  
    
    @Override
    public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
       // 오늘날짜 초-밀리세컨드초 : 4자리 
       SimpleDateFormat sf = new SimpleDateFormat("ssSSS");
       String sssTime = sf.format(new Date()).toString();
       sf = null;
       // 0~65 난수 : 1자리 문자
       char numChar = (char)(int) Math.floor(Math.random() * 66);
       // 2 + 4 + 1 = 7자리 문자열
       final String code = (prefix + sssTime + numChar).substring(0, 7);
       return code;
    }
    
}
