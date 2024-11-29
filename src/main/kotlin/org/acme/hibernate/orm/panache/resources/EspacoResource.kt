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
import org.acme.hibernate.orm.panache.forms.EspacoForm
import org.acme.hibernate.orm.panache.forms.LocalForm
import org.acme.hibernate.orm.panache.forms.LoginForm
import org.acme.hibernate.orm.panache.forms.UserForm
import org.acme.hibernate.orm.panache.services.EspacoService
import org.acme.hibernate.orm.panache.services.LocalService
import org.acme.hibernate.orm.panache.services.UserService


@Path("/espaco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class EspacoResource(@Inject var espacoService: EspacoService
) {


    @POST
    @Path("/create")
    fun createEspaco(@Valid espacoForm: EspacoForm): Response {
        var newEspaco = espacoService.createEspaco(
            espacoForm = espacoForm
        )
        return Response.status(Response.Status.CREATED).build()
    }



}