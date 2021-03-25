package com.sens.pot.web.service;

import com.sens.pot.web.domain.content.Category;

public interface ContentService {

    /* Category */
    Category saveCategory(String name, String description);
    Category saveCategory(String code, String name, String description);
    Category updateCategory(String code,String name, String description);
    
    
}
