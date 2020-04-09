package hu.icell.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.icell.managers.interfaces.IndexManagerRemote;
import hu.icell.managers.interfaces.ResultManagerRemote;
import hu.icell.managers.interfaces.ResultDataManagerRemote;

public class EjbFinder {
	
	private static Logger logger = LoggerFactory.getLogger(EjbFinder.class);
	
	public static IndexManagerRemote getIndexManager() {
		return lookupBean(IndexManagerRemote.class, "IndexManagerEjb");
	}
	
	public static ResultManagerRemote getResultManager() {
		return lookupBean(ResultManagerRemote.class, "ResultManagerEjb");
	}
	
	public static ResultDataManagerRemote getResultDataManager() {
		return lookupBean(ResultDataManagerRemote.class, "ResultDataManagerEjb");
	}

	private static <T> T lookupBean(Class<T> remoteInterfaceClass, String beanName) {
		try {
			Context ctx = createInitialContext();
			String namespace = "";
			String appName = "ejb:";
			String moduleName = "memorygame-ear";
			String distinctName = "";
			String viewClassName = remoteInterfaceClass.getName();
			String lookupText = namespace + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName;
			logger.info("lookupText: " + lookupText);
			return (T) ctx.lookup(lookupText);
		} catch (NamingException e) {
			logger.debug(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private static Context createInitialContext() throws NamingException {
		Properties jndiProperties = new Properties();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8485");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		return new InitialContext(jndiProperties);
	}

}
