package serialization;

import java.io.IOException;
import java.io.InputStream;

public interface JsonReader {

    <T> T fromJson(String content, Class<T> valueType) throws IOException;

    <T> T fromJson(InputStream inputStream, Class<T> valueType) throws IOException;

}
