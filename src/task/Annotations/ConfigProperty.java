package task.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ConfigProperty {
    String configFileName() default "default.properties";
    String propertyName() default "";
    PropertyType type() default PropertyType.AUTO;
}
