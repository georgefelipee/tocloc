package org.acme.hibernate.orm.panache.entities


import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*

@Entity
@Table(name = "local_esportivo")
class LocalEsportivo() : PanacheEntity() {

    @Column(nullable = false, length = 100)
    lateinit var nome: String

    @Column(nullable = false, length = 255)
    lateinit var endereco: String

    @Column(nullable = false, length = 50)
    lateinit var tipoEsporte: String

    @Column(length = 500)
    var descricao: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anfitriao", nullable = false)
    lateinit var anfitriao: Usuario

    // Construtor adicional para inicialização simplificada
    constructor(nome: String, endereco: String, tipoEsporte: String, descricao: String?, anfitriao: Usuario) : this() {
        this.nome = nome
        this.endereco = endereco
        this.tipoEsporte = tipoEsporte
        this.descricao = descricao
        this.anfitriao = anfitriao
    }
}
