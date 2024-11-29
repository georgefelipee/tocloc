package org.acme.hibernate.orm.panache.forms


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class EspacoForm(

    @field:NotBlank(message = "O Espaco não pode estar vazio")
    val nome: String,

    @field:NotBlank(message = "O Tipo de Esporte não pode estar vazio")
    val tipoEsporte: String,

    val descricao: String?,

    @field:NotNull(message = "O Local não pode estar vazio")
    val local: Long


)
