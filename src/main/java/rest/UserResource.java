package rest;

import caches.Cache;
import config.Configuration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import models.MidurResult;
import models.User;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Path("/users")
@Api( value = "/Users", description = "Operations about users" )
@Produces({"application/json"})
public class UserResource {


    private Cache<String, User> userCache;

    public UserResource(Cache<String, User> userCache) {
        this.userCache = userCache;
    }

    @GET
    @ApiOperation(value = "Find All Users in Cache",
            response = User.class,
            responseContainer = "List")
    public Collection<User> getAllUsers(){
        return userCache.values().orElse(null);
    }

    @GET
    @Path("/{userId}")
    @ApiOperation(value = "Find User in cache by UserId",
            response = User.class)
    public User getUserByID(@ApiParam(value = "UserID that need to be queried", required = true) @NotNull @PathParam("userId") String userId){
        return userCache.get(userId).orElse(null);
    }

}
