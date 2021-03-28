package com.sens.pot.common.anotation;
import java.lang.annotation.*;

/**
 * @author <a href="mailto:guzhongtao@middol.com">guzhongtao</a>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TableComment {

    /**
     * @return String
     */
    String value() default "";
}

