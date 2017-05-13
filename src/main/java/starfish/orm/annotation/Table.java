package starfish.orm.annotation;

import java.lang.annotation.*;

/**
 * 数据库表名类注解
 * Created by song on 17/5/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    /**
     * 数据库表名称
     * @return
     */
    public String value() default "";
}
