package hu.icell.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * http://stackoverflow.com/questions/9373081/how-to-set-up-jax-rs-application-
 * using-annotations-only-no-web-xml
 * 
 * @author lajos
 * 
 */
@ApplicationPath("/game")
public class RestApplication extends Application {
}
