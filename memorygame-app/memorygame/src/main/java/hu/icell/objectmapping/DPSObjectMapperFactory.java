package hu.icell.objectmapping;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DPSObjectMapperFactory {

    public static DPSObjectMapper createObjectMapper(){
        final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        final String UTC_TIMEZONE = "UTC";

        DPSObjectMapper objectMapper = new DPSObjectMapper();
        
        objectMapper.registerModule(new JodaModule());

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);

        //to use textual format is the default anyways, we just state this explicitly here...
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        DateFormat dateFormat = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE));
        objectMapper.setDateFormat(dateFormat);

        return  objectMapper;
    }
}
