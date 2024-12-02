package org.acme.hibernate.orm.panache.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "espaco")
class Espaco(


    @Column(nullable = false, length = 100)
    var nome: String = "Espaço Padrão",

    @Column(length = 500)
    var descricao: String? = "Descrição Padrão",

    @Column(nullable = false, length = 50)
    var tipoEsporte: String = "Esporte Padrão",

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_local", nullable = false)
    var local: LocalEsportivo = LocalEsportivo(
        nome = "Local Padrão",
        endereco = "Endereço Padrão",
        descricao = "Descrição Local Padrão",
        anfitriao = Usuario() // Substitua por um usuário padrão ou válido
    ),

    @OneToMany(mappedBy = "espaco", cascade = [CascadeType.ALL], orphanRemoval = true)
    var disponibilidades: MutableList<Disponibilidade> = mutableListOf()
) : PanacheEntity() {


    companion object : PanacheCompanion<Espaco>

    // Bloco init opcional para validação
    init {
        require(nome.isNotBlank()) { "O nome do espaço não pode ser vazio." }
        require(tipoEsporte.isNotBlank()) { "O tipo de esporte não pode ser vazio." }
    }
}
