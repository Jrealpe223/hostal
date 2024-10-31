package co.com.usc.hostalusc.test.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author  jvillada
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SQLDatabaseScript {

    /**
     * @return
     */
    String basePath() default "scripts/";

    /**
     * Name of the file containing the data
     * @return
     */
    String fileName();

    /**
     * @return
     */
    boolean forceReRun() default false;

}
