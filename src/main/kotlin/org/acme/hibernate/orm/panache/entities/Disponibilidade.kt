package org.acme.hibernate.orm.panache.entities


import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import java.time.LocalTime


@Entity
@Table(name = "disponibilidade")
class Disponibilidade() : PanacheEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local", nullable = false)
    lateinit var local: LocalEsportivo

    @ElementCollection(targetClass = DiaSemana::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dias_semana", joinColumns = [JoinColumn(name = "id_disponibilidade")])
    @Column(name = "dia", nullable = false)
    @Enumerated(EnumType.STRING)
    var diasSemana: MutableSet<DiaSemana> = mutableSetOf()

    @Column(nullable = false)
    lateinit var dataHoraInicio: LocalTime

    @Column(nullable = false)
    lateinit var dataHoraFim: LocalTime

    @Column(nullable = false)
    var intervaloMinutos: Int = 0

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: StatusDisponibilidade = StatusDisponibilidade.DISPONIVEL

    // Construtor adicional para inicialização simplificada
    constructor(
        local: LocalEsportivo,
        diasSemana: Set<DiaSemana>,
        dataHoraInicio: LocalTime,
        dataHoraFim: LocalTime,
        intervaloMinutos: Int,
        status: StatusDisponibilidade
    ) : this() {
        this.local = local
        this.diasSemana = diasSemana.toMutableSet()
        this.dataHoraInicio = dataHoraInicio
        this.dataHoraFim = dataHoraFim
        this.intervaloMinutos = intervaloMinutos
        this.status = status
    }
}

enum class DiaSemana {
    SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO
}

enum class StatusDisponibilidade {
    DISPONIVEL, RESERVADO
}
