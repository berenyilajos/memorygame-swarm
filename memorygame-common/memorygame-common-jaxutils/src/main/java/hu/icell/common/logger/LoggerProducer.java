package hu.icell.common.logger;

import javax.enterprise.context.Dependent;

//import javax.enterprise.inject.Produces;
//
//import org.slf4j.Logger;
//
//import hu.icell.common.annotation.ThisLogger;
//
//@ThisLogger
//public class LoggerProducer {
//    
//    @ThisLogger
//    private Logger log;
//    
//    @Produces
//    public Logger createLogger() {
//        return log;
//    }
//
//}

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.LoggerFactory;

@Named
@Dependent
public class LoggerProducer {
    
    @Inject
    @DefaultAppLogger
    private AppLogger appLogger;
    
    @Produces
    @ThisLogger
    public AppLogger getLogger(final InjectionPoint ip) {
        appLogger.setLogger(LoggerFactory.getLogger(ip.getMember().getDeclaringClass()));
        
        return appLogger;
    }
    
    public static AppLogger getStaticLogger(Class<?> clazz) {
        AppLogger appLogger = new AppLoggerImpl();
        appLogger.setLogger(LoggerFactory.getLogger(clazz));
        
        return appLogger;
    }
    
}
