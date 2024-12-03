package org.acme.hibernate.orm.panache.resources

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Produces
import jakarta.validation.Valid
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.forms.LocalForm
import org.acme.hibernate.orm.panache.forms.LoginForm
import org.acme.hibernate.orm.panache.forms.UserForm
import org.acme.hibernate.orm.panache.services.LocalService
import org.acme.hibernate.orm.panache.services.UserService


@Path("/local")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class LocalResource(@Inject var localService: LocalService
) {


    @POST
    @Path("/create")
    fun createLocal(@Valid request: LocalForm): Response {
        var newLocal = localService.createLocal(
            localForm = request
        )
        return Response.status(Response.Status.CREATED).entity(newLocal).build()
    }



    @GET
    @Path("/list")
    fun listAll(): Response {
        return Response.ok(localService.listAll()).build()
    }

    @GET
    @Path("/get/{id}")
    fun getLocalById(id: Long): Response {
        return Response.ok(localService.getLocalById(id)).build()
    }


}