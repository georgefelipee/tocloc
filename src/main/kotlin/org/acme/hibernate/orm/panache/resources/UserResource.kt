package org.acme.hibernate.orm.panache.resources

import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.forms.LoginForm
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
        val newUser = userService.createUser(
            nome = request.nome,
            email = request.email,
            senha = request.senha,
            tipoUsuario = request.tipoUsuario
        )
        return Response.status(Response.Status.CREATED).entity(newUser).build()
    }


    @POST
    @Path("/login")
    fun loginUser(@Valid request: LoginForm): Response {
        val user = userService.loginUser(
            email = request.email,
            senha = request.senha
        )
        return Response.status(Response.Status.OK).entity(user).build()
    }

    @GET
    @Path("/")
    fun getAllUsers(): Response {
        val users = userService.getAllUsers()

        return if (users.isNotEmpty()) {
            Response.status(Response.Status.OK).entity(users).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Nenhum usuário encontrado").build()
        }
    }



    @GET
    @Path("/{id}")
    fun getUserById(@PathParam("id") id: Long): Response {
        return try {
            val user = userService.getUserById(id)
            Response.status(Response.Status.OK).entity(user).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.NOT_FOUND).entity(e.message).build()
        }
    }
}