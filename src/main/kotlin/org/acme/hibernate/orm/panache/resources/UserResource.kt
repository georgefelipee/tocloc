package org.acme.hibernate.orm.panache.resources

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Produces
import jakarta.validation.Valid
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.forms.UserForm
import org.acme.hibernate.orm.panache.services.UserService


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource(  @Inject var userService: UserService
) {


    @POST
    @Path("/create")
    fun createUser(@Valid request: UserForm): Response {
        print("vapo")
        val newUser = userService.createUser(
            nome = request.nome,
            email = request.email,
            senha = request.senha,
            tipoUsuario = request.tipoUsuario
        )
        return Response.status(Response.Status.CREATED).entity(newUser).build()
    }
}