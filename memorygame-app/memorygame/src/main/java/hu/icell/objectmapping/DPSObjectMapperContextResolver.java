package hu.icell.objectmapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class DPSObjectMapperContextResolver implements
        ContextResolver<ObjectMapper> {
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return DPSObjectMapperFactory.createObjectMapper();
    }
}
