package hu.icell.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.icell.actions.IndexAction;
import hu.icell.actions.ResultAction;
import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.entities.Result;
import hu.icell.entities.User;
import hu.icell.objectmapping.DPSObjectMapperFactory;
import hu.icell.xsdpojo.common.common.SuccessType;
import hu.icell.xsdpojo.pojo.ResultResponse;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ValamiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	@ThisLogger
	private AppLogger log;

	@Inject
	private IndexAction indexAction;

	@Inject
	private ResultAction resultAction;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user;
		Result result;
		ResultResponse response = new ResultResponse();
		try {
			user = indexAction.getUserByUsername(req.getParameter("userName"));
			if (user == null) {
				throw new Exception("User not found");
			}
			List<Result> results = resultAction.getResultsByUser(user);
			if (results.isEmpty()) {
				throw new Exception("No result was found, userName: " + user.getUsername());
			}
			result = results.get(0);
			response.setMessage("Best result of the user");
			response.setSeconds((int) result.getSeconds());
			response.setSuccess(SuccessType.SUCCESS);
			response.setUserId(user.getId());
		} catch (Exception e) {
			response.setSuccess(SuccessType.ERROR);
			response.setMessage(e.getMessage());
		}
		ObjectMapper objectMapper = DPSObjectMapperFactory.createObjectMapper();
		String jsonString = objectMapper.writeValueAsString(response);
		System.out.println(jsonString);
		resp.getWriter().println(jsonString);
	}
}
