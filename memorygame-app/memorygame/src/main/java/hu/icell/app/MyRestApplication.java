package hu.icell.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import hu.icell.services.ResultRestService;
import hu.icell.services.ResultService;
import hu.icell.exception.MyApplicationExceptionHandler;
import hu.icell.services.IndexService;

@ApplicationPath("/game")
public class MyRestApplication extends Application {
    private Set<Class<?>> classes = new HashSet<Class<?>>();
//    private Set<Object> singletons = new HashSet<Object>();
    
    public MyRestApplication() {
        classes.add(IndexService.class);
        classes.add(ResultService.class);
        classes.add(ResultRestService.class);
        classes.add(MyApplicationExceptionHandler.class);
//        singletons.add(new IndexService());
//        singletons.add(new ResultService());
//        singletons.add(new ResultRestService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

//    @Override
//    public Set<Object> getSingletons() {
//        return singletons;
//    }
    
}
