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
import org.acme.hibernate.orm.panache.entities.Disponibilidade
import org.acme.hibernate.orm.panache.entities.Espaco
import org.acme.hibernate.orm.panache.forms.*
import org.acme.hibernate.orm.panache.services.DisponibilidadeService
import org.acme.hibernate.orm.panache.services.EspacoService
import org.acme.hibernate.orm.panache.services.LocalService
import org.acme.hibernate.orm.panache.services.UserService


@Path("/disponibilidade")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class DisponibilidadeResource(@Inject var disponibilidadeService: DisponibilidadeService
) {


    @POST
    @Path("/create")
    fun createDisponibilidade(@Valid disponibilidadeForm: DisponibilidadeForm): Response {
        var newDisponibilidade = disponibilidadeService.criarDisponibilidades(
            espacoId = disponibilidadeForm.espacoId,
            form = disponibilidadeForm
        )

        // Retorna a lista de disponibilidades criadas na resposta
        return Response.status(Response.Status.CREATED)
            .entity(newDisponibilidade) // Inclui a lista no corpo da resposta
            .build()
    }






}