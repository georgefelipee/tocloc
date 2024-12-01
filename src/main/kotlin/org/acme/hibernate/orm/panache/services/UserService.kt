package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.hibernate.orm.panache.entities.TipoUsuario
import org.acme.hibernate.orm.panache.entities.Usuario


@ApplicationScoped
class UserService {

    @Transactional
    fun createUser(nome: String, email: String, senha: String, tipoUsuario: TipoUsuario): Usuario {
       Usuario.find("email", email).firstResult()?.let {
            throw IllegalArgumentException("Email já cadastrado")
       }

        val usuario = Usuario(nome, email, senha, tipoUsuario)
        usuario.persist()
        return usuario
    }
    @Transactional
    fun loginUser(email: String, senha: String): Usuario {
        return Usuario.find("email = ?1 and senha = ?2", email, senha).firstResult()
            ?: throw IllegalArgumentException("Crendenciais inválidas")
    }

    @Transactional
    fun getAllUsers(): List<Usuario> {
        return Usuario.listAll()  // Retorna todos os usuários
    }

    @Transactional
    fun getUserById(id: Long): Usuario {
        return Usuario.findById(id) ?: throw IllegalArgumentException("Usuário não encontrado")
    }
}