package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.hibernate.orm.panache.entities.TipoUsuario
import org.acme.hibernate.orm.panache.entities.Usuario


@ApplicationScoped
class UserService {

    @Transactional
    fun createUser(nome: String, email: String, senha: String, tipoUsuario: TipoUsuario): Usuario {
        val usuario = Usuario(nome, email, senha, tipoUsuario)
        usuario.persist()
        return usuario
    }
}