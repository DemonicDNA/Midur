package rest;

import io.swagger.annotations.*;
import models.Attribute;
import models.MidurResult;
import models.MidurResultStatusReason;
import rest.controller.PermissionController;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/permissions")
@Api( value = "/Permissions", description = "Operations about permissions" )
@Produces({"application/json"})
public class PermissionResource {

    private PermissionController permissionController;

    public PermissionResource(PermissionController permissionController) {
        this.permissionController = permissionController;
    }

    @GET
    @Path("/{userId}/{systemName}")
    @ApiOperation(value = "Find All Permissions by UserId and SystemName",
            response = MidurResult.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 404, message = "The passed System Name wasn't configured in the configuration file"),
            @ApiResponse(code = 200, message = "Operation Succeeded") })
    public Response getAllAttributesByUserAndSystem(@ApiParam(value = "UserID that need to be queried", required = true) @NotNull @PathParam("userId") String userId,
                                                    @ApiParam(value = "SystemName that need to be queried", required = true) @NotNull @PathParam("systemName") String systemName) {
        MidurResult midurResult = permissionController.calculateMidurResult(userId, systemName, false);
        if(midurResult.getMidurResultStatusReason() == MidurResultStatusReason.SystemNameNotConfigured) {
            return Response.status(404).entity("The System Name " + systemName + " wasn't configured").build();
        }
        return Response.ok(midurResult).build();
    }

    @GET
    @Path("/{userId}/{systemName}/{attributeId}")
    @ApiOperation(value = "Find specific Permission by UserId, SystemName and AttributeId",
            response = MidurResult.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "The passed System Name wasn't configured in the configuration file"),
            @ApiResponse(code = 500, message = "More than 1 attribute with same Id was found"),
            @ApiResponse(code = 200, message = "Operation Succeeded") })
    public Response getAttributeByUserAndSystem(@ApiParam(value = "UserID that need to be queried", required = true) @NotNull @PathParam("userId") String userId,
                                                @ApiParam(value = "SystemName that need to be queried", required = true) @NotNull @PathParam("systemName") String systemName,
                                                @ApiParam(value = "Attribute that we want to be get", required = true) @NotNull @PathParam("attributeId") String attributeId) {
        MidurResult midurResult = permissionController.calculateMidurResult(userId, systemName, false);
        if(midurResult.getMidurResultStatusReason() == MidurResultStatusReason.SystemNameNotConfigured) {
            return Response.status(404).entity("The System Name " + systemName + " wasn't configured").build();
        }
        List<Attribute> attributes = midurResult.getAttributeList();
        List<Attribute> relevantAttributes = attributes.stream().filter(attribute -> attribute.getAttributeId().equals(systemName + ":" + attributeId))
                .collect(Collectors.toList());
        if(relevantAttributes.size() > 1){
            return Response.status(500).entity("More than 1 attribute with same Id for " + attributeId + " was found").build();
        }
        return Response.ok(new MidurResult(relevantAttributes, midurResult.getMidurResultStatusReason())).build();
    }

    @GET
    @Path("manualUpdate/{userId}/{systemName}")
    @ApiOperation(value = "Manual Permission update of for user by UserId and SystemName",
            response = MidurResult.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 404, message = "The passed System Name wasn't configured in the configuration file"),
            @ApiResponse(code = 200, message = "Operation Succeeded") })
    public Response manualUpdate(@ApiParam(value = "UserID that need to be updated", required = true)@NotNull @PathParam("userId") String userId,
                                 @ApiParam(value = "SystemName that need to be updated", required = true)@NotNull @PathParam("systemName") String systemName) {
        MidurResult midurResult = permissionController.calculateMidurResult(userId, systemName, true);
        if(midurResult.getMidurResultStatusReason() == MidurResultStatusReason.SystemNameNotConfigured) {
            return Response.status(404).entity("The System Name " + systemName + " wasn't configured").build();
        }
        return Response.ok(midurResult).build();
    }


}
