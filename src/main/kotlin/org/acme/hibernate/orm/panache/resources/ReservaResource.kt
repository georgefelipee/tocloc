package org.acme.hibernate.orm.panache.resources

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Produces
import jakarta.validation.Valid
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.entities.Disponibilidade
import org.acme.hibernate.orm.panache.entities.Espaco
import org.acme.hibernate.orm.panache.forms.*
import org.acme.hibernate.orm.panache.services.*


@Path("/reserva")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ReservaResource(@Inject var reservaService: ReservaService
) {


    @POST
    @Path("/create")
    fun createReserva(@Valid reservaForm : ReservaForm): Response {
        var newReserva = reservaService.criarReserva(
            disponibilidadeId = reservaForm.disponibilidadeId,
            usuarioId = reservaForm.usuarioId
        )
        return Response.status(Response.Status.CREATED).build()
    }




}