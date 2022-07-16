package sn.seye.gestionmatricule.mefpai;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import sn.seye.gestionmatricule.mefpai.DbGestionMatricule5App;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = DbGestionMatricule5App.class)
public @interface IntegrationTest {
}
