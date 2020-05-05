package hu.icell.jwt;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static javax.ws.rs.core.Response.Status;

@Provider
@RestSecure
@Priority(Priorities.AUTHENTICATION)
public class RestSecureNeededFilter implements ContainerRequestFilter {

    @Inject
    @ThisLogger
    private AppLogger log;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.info("running filter ContainerRequestContext method...");
        String userName = null;
        if (requestContext.getSecurityContext() != null && requestContext.getSecurityContext().getUserPrincipal() != null) {
            userName = requestContext.getSecurityContext().getUserPrincipal().getName();
            log.debug("Username: " + userName + ", in role user: " + requestContext.getSecurityContext().isUserInRole("user"));
        }
        if (userName == null) {
            log.debug("JwtFilter: user not logged in, path: " + requestContext.getUriInfo());
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        } else {
            log.debug("JwtFilter: user " + userName + " logged in, path: " + requestContext.getUriInfo());
        }
    }
}
