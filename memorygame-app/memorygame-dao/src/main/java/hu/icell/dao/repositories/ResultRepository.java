package hu.icell.dao.repositories;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import hu.icell.entities.Result;
import hu.icell.entities.User;

@Repository
public interface ResultRepository extends EntityRepository<Result, Long> {
	
	@Query(value = "SELECT r FROM Result r JOIN FETCH r.user u WHERE u=:user ORDER BY r.seconds ASC, r.resultDate DESC", max = 10)
	public List<Result> getResultsByUser(@QueryParam("user") User user);
	
	@Query(value = "SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC", max = 20)
	public List<Result> getResults();
	
}
