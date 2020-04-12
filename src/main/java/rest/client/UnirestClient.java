package rest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UnirestClient<T> implements RestClient<T>{

    private final ObjectMapper om;

    public UnirestClient(){
        om = new ObjectMapper();
        Unirest.setTimeouts(1000, 1000);
    }

    public Optional<T> get(String url){
        try {
            return Optional.of(om.readValue(Unirest.get(url).asString().getBody(),new TypeReference<T>(){}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<T>> getList(String url){
        try {
            return Optional.of(om.readValue(Unirest.get(url).asString().getBody(),new TypeReference<List<T>>(){}));
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
