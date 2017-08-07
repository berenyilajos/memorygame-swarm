package hu.icell.transaction;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import org.apache.deltaspike.jpa.impl.transaction.BeanManagedUserTransactionStrategy;
import org.hibernate.engine.transaction.jta.platform.internal.JBossAppServerJtaPlatform;

/**
/**
 * TODO: 2015.01.20 valoszinu mar nem kell, mert az osben is mar igy van
 * hasznalva, deltaspike verziovaltasnal javitva lehetett. Ki kell probalni
 * 
 * TODO: 2015.07.03 wildfly 8.1, benne levo hibernate es deltaspike 1.4.1
 * kikoveteli ujra. A 1.0.3 deltaspike-al egyatalan nem akart mukodni kulon
 * szalban (quartz job) a tranzakcio kezeles, UserTransaction-t nem talalta
 * 
 * Ez akkor kell amikor egy threadban akarjuk hasznalni a @Transactional
 * annotaciot
 * 
 * @author ischeffer
 * 
 */
@Dependent
@Alternative
public class ThreadUserTransactionStrategy extends
		BeanManagedUserTransactionStrategy {

	private static final long serialVersionUID = 1L;

	// @Resource hasznalata lenne a logikus megoldas de ennek koszonhetoen szal
	// el, ezert az @Inject van hasznalva
	@Inject
	private UserTransaction userTransaction;

	// @Override
	// protected UserTransaction resolveUserTransaction() {
	// return transaction;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.deltaspike.jpa.impl.transaction.BeanManagedUserTransactionStrategy
	 * #resolveUserTransaction()
	 */
	@Override
	protected UserTransaction resolveUserTransaction() {
		if (userTransaction != null) {
			return userTransaction;
		}
		InitialContext context = null;
		try {
			context = new InitialContext();
			// "java:comp/UserTransaction"
			UserTransaction returnUserTransaction = lookup(context, JBossAppServerJtaPlatform.UT_NAME);
			if (returnUserTransaction == null) {
				// "java:jboss/UserTransaction"
				returnUserTransaction = lookup(context, JBossAppServerJtaPlatform.JBOSS_UT_NAME);
			}
			if (returnUserTransaction == null) {
				// "UserTransaction" allitolag valami extra esetekben elofordul
				// hogy csak ez van
				returnUserTransaction = lookup(context, "UserTransaction");
			}
			if (returnUserTransaction != null) {
				return returnUserTransaction;
			}
		} catch (NamingException ne) {
			Logger.getLogger(ThreadUserTransactionStrategy.class.getName()).warning(
					"Creating InitialContext failed, message: " + ne.getLocalizedMessage());
		}

		return super.resolveUserTransaction();
	}

	private UserTransaction lookup(InitialContext context, String name) {
		try {
			UserTransaction returnUserTransaction = (javax.transaction.UserTransaction) context.lookup(name);
			return returnUserTransaction;
		} catch (NamingException ne) {
			Logger.getLogger(ThreadUserTransactionStrategy.class.getName()).warning(
					"UserTransaction for [" + name + "] not found, message: " + ne.getLocalizedMessage());
		}
		return null;
	}
}
