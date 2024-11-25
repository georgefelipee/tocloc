package org.acme.hibernate.orm.panache.entities


import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import java.time.LocalDateTime


@Table(name = "usuario")
@Entity
class Usuario() : PanacheEntity() {

    companion object : PanacheCompanion<Usuario>
    @Column(nullable = false, length = 100)
    lateinit var nome: String

    @Column(nullable = false, unique = true, length = 100)
    lateinit var email: String

    @Column(nullable = false)
    lateinit var senha: String

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var tipoUsuario: TipoUsuario

    @Column(nullable = false)
    var dataCadastro: LocalDateTime = LocalDateTime.now()


    constructor(nome: String, email: String, senha: String, tipoUsuario: TipoUsuario) : this() {
        this.nome = nome
        this.email = email
        this.senha = senha
        this.tipoUsuario = tipoUsuario
    }
}

enum class TipoUsuario {
    VISITANTE, USUARIO, ANFITRIAO
}
