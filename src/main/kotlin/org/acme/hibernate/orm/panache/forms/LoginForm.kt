package org.acme.hibernate.orm.panache.forms

import org.acme.hibernate.orm.panache.entities.TipoUsuario
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class LoginForm(

    @field:NotBlank(message = "O e-mail não pode estar vazio")
    @field:Email(message = "O e-mail deve ser válido")
    val email: String,

    @field:NotBlank(message = "A senha não pode estar vazia")
    @field:Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    val senha: String,
)
