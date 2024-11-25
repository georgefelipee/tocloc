package org.acme.hibernate.orm.panache.forms

import org.acme.hibernate.orm.panache.entities.TipoUsuario
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class LocalForm(

    @field:NotBlank(message = "O Local não pode estar vazio")
    val nome: String,

    @field:NotBlank(message = "O Endereço não pode estar vazio")
    val endereco: String,

    @field:NotBlank(message = "O Tipo de Esporte não pode estar vazio")
    val tipoEsporte: String,


    val descricao: String?,

    @field:NotNull(message = "O Anfitrião não pode estar vazio")
    val anfitriao: Long


)
