package org.acme.hibernate.orm.panache.entities


import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reserva")
class Reserva() : PanacheEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    lateinit var usuario: Usuario

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disponibilidade", nullable = false, unique = true)
    lateinit var disponibilidade: Disponibilidade

    @Column(nullable = false)
    var dataReserva: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var statusReserva: StatusReserva = StatusReserva.PENDENTE


    constructor(usuario: Usuario, disponibilidade: Disponibilidade, statusReserva: StatusReserva) : this() {
        this.usuario = usuario
        this.disponibilidade = disponibilidade
        this.statusReserva = statusReserva
    }
}

enum class StatusReserva {
    PENDENTE, CONFIRMADA, CANCELADA
}
