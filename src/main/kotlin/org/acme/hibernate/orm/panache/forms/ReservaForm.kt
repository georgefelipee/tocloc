package org.acme.hibernate.orm.panache.forms


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ReservaForm(


        @field:NotNull(message = "O disponibilidadeId não pode estar vazio")
        val disponibilidadeId: Long,

        @field:NotNull(message = "O usuario não pode estar vazio")
        val usuarioId: Long


)
