package hu.icell.ejb;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;

import hu.icell.managers.interfaces.IndexManagerRemote;
import hu.icell.managers.interfaces.ResultDataManagerRemote;
import hu.icell.managers.interfaces.ResultManagerRemote;

@Model
public class EjbProducer {
	
	@Produces
	protected IndexManagerRemote createIndexManager() {
		return EjbFinder.getIndexManager();
	}
	
	@Produces
	protected ResultManagerRemote createResultManager() {
		return EjbFinder.getResultManager();
	}
	
	@Produces
	protected ResultDataManagerRemote createResultDataManager() {
		return EjbFinder.getResultDataManager();
	}
	
}
