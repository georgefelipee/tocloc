package org.acme.hibernate.orm.panache.entities

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
    var nome: String,

    @Column(length = 500)
    var descricao: String? = null,

    @Column(nullable = false, length = 50)
    var tipoEsporte: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local", nullable = false)
    var local: LocalEsportivo
) : PanacheEntity() {
    constructor() : this(
        "",
        "",
        "",
        LocalEsportivo(
            nome = "",
            endereco = "",
            descricao = "",
            anfitriao = Usuario()
        )
    ) {

    }

    @OneToMany(mappedBy = "espaco", cascade = [CascadeType.ALL], orphanRemoval = true)
    var disponibilidades: MutableList<Disponibilidade> = mutableListOf()
}
