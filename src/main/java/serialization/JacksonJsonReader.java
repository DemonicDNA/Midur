package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JacksonJsonReader implements JsonReader{

    private ObjectMapper om = new ObjectMapper();

    public <T> T fromJson(String content, Class<T> valueType) throws IOException {
        return om.readValue(content, valueType);
    }

    public <T> T fromJson(InputStream inputStream, Class<T> valueType) throws IOException {
        return om.readValue(inputStream, valueType);
    }
}
