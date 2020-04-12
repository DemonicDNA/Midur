package rest.client;

import java.util.List;
import java.util.Optional;

public interface RestClient<T> {

    Optional<T> get(String url);
    Optional<List<T>> getList(String url);

}
