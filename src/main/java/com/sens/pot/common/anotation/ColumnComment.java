package com.sens.pot.common.anotation;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnComment {

    /**
     * @return String
     */
    String value() default "";
}

