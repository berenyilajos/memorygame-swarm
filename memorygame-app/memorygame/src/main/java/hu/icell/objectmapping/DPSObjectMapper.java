package hu.icell.objectmapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

public class DPSObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;

	public DPSObjectMapper() {
		super();
	}

	public DPSObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
		super(jf, sp, dc);
	}

	public DPSObjectMapper(JsonFactory jf) {
		super(jf);
	}

	public DPSObjectMapper(ObjectMapper src) {
		super(src);
	}
	
	public <T> T readEntityValue(ClientResponseContext responseContext, Class<T> entityClass) throws JsonParseException, JsonMappingException, IOException {
		if (!responseContext.hasEntity()) {
			return null;
		}
		InputStreamReader reader = new InputStreamReader(responseContext.getEntityStream(), charset(responseContext.getMediaType()) );
		
		return readValue(reader, entityClass);
	}
	
	public <T> T readEntityValue(Response response, Class<T> entityClass) {
		if (!(response.getEntity() instanceof InputStream)) {
			return response.readEntity(entityClass);
		}
		try {
			InputStreamReader reader = new InputStreamReader((InputStream)(response.getEntity()), charset(response.getMediaType()));
			return readValue(reader, entityClass);
		} catch (IOException e) {
			throw new RuntimeException("Mapping exception", e);
		}
	}
	
	public <T> List<T> readEntityListValue(Response response, Class<T> entityClass) {
		if (!(response.getEntity() instanceof InputStream)) {
			return response.readEntity(getGenericListType(entityClass));
		}
		try {
			InputStreamReader reader = new InputStreamReader((InputStream)(response.getEntity()), charset(response.getMediaType()));
			return readValue(reader, getListTypeReference(entityClass));
		} catch (IOException e) {
			throw new RuntimeException("Mapping exception", e);
		}
	}

	private String charset(MediaType mediaType) {
		String charset = mediaType != null ? mediaType.getParameters().get(MediaType.CHARSET_PARAMETER) : null;
		return charset != null ? charset : StandardCharsets.UTF_8.name();
	}

	private <T> TypeReference<List<T>> getListTypeReference(Class<T> entityClass) {
    	Type type = new ParameterizedType() {
		  	public Type[] getActualTypeArguments() {
			    return new Type[]{entityClass};
			}

			public Type getRawType() {
			    return List.class;
			}

			public Type getOwnerType() {
			    return null;
			}
		};
		return new TypeReference<List<T>>() {
			@Override
			public Type getType() { return type; }
		};
    }
	
	private <T> GenericType<List<T>> getGenericListType(Class<T> responseClass) {
    	Type type = new ParameterizedType() {
		  	public Type[] getActualTypeArguments() {
			    return new Type[]{responseClass};
			}

			public Type getRawType() {
			    return List.class;
			}

			public Type getOwnerType() {
			    return null;
			}
		};
		return new GenericType<>(type);
	}

}
