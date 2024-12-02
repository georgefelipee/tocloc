package org.acme.hibernate.orm.panache.forms


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.acme.hibernate.orm.panache.entities.DiaSemana
import java.math.BigDecimal

data class DisponibilidadeForm(

    @field:NotBlank(message = "A hora de inicio não pode estar vazio")
    val horaInicio: String,

    @field:NotBlank(message = "A hora de fim não pode estar vazio")
    val horaFim: String,

    @field:NotNull(message = "O intervalo não pode estar vazio")
    val intervalo: Int,

    @field:NotNull(message = "Os dias da semana nao pode estar vazio")
    val diasSemana:  Set<DiaSemana>,

    @field:NotNull(message = "O espaco não pode estar vazio")
    val espacoId: Long,

    @field:NotNull(message = "O valor não pode estar vazio")
    val valor: BigDecimal


)
