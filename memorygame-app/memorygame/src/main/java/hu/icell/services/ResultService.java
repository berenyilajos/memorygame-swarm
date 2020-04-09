package hu.icell.services;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.managers.interfaces.ResultManagerLocal;

@Stateless
@LocalBean
@Path("/result")
public class ResultService {
	
    @EJB
    private ResultManagerLocal resultManager;
    
    @Inject
    @ThisLogger
    private AppLogger log;
    
//  @PersistenceUnit(unitName="memorygame")
//  private EntityManagerFactory emf;
//  private EntityManager em;
    
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public void listAction(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {
        
//      emf = Persistence.createEntityManagerFactory("memorygame");
//      em = emf.createEntityManager();
//      em.getTransaction().begin();
//      Result r = new Result();
//      r.setResultDate(Calendar.getInstance().getTime());
//      r.setSeconds(BigDecimal.valueOf(55));
//      r.setUser(em.find(User.class, 1L));
//      em.persist(r);
//      em.flush();
//      Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC");
//      q.setMaxResults(20);
//      List<Result> list = q.getResultList();
        
        log.debug("ResultService.listAction >>>");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/memorygame/game/login");
            return;
        }
        
//        List<Result> list = resultAction.getResults();
        List<Result> list = resultManager.getResultsData();
//      em.getTransaction().commit();
        
        request.setAttribute("list", list);
        log.debug("<<< ResultService.listAction");
        
        request.getRequestDispatcher("/WEB-INF/views/result/results.jsp")
        .forward(request, response);
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{userId:\\d+}")
    public void showAction(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("userId") String userId)
            throws ServletException, IOException {
        log.debug("ResultService.showAction, userId=[{}] >>>", userId);
        HttpSession session = request.getSession(false);
        User user;
        if (session == null || (user = (User)session.getAttribute("user")) == null || user.getId() != Long.parseLong(userId)) {
            response.sendRedirect("/memorygame/game/login");
            return;
        }
        List<Result> list = resultManager.getResultsByUser(user);
        request.setAttribute("list", list);
        log.debug("<<< ResultService.showAction");
        
        request.getRequestDispatcher("/WEB-INF/views/result/userresult.jsp")
        .forward(request, response);
    }
    
}
