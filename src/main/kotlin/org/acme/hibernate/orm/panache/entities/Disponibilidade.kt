package org.acme.hibernate.orm.panache.entities


import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*
import org.acme.hibernate.orm.panache.forms.EspacoForm
import java.math.BigDecimal
import java.time.LocalTime



@Entity
@Table(name = "disponibilidade")
class Disponibilidade(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_espaco", nullable = false)
    @JsonIgnore
    var espaco: Espaco,

    @Column(nullable = false)
    var horaInicio: LocalTime, // Hora inicial do intervalo

    @Column(nullable = false)
    var horaFim: LocalTime, // Hora final do intervalo

    @Column(nullable = false)
    var intervalo: Int, // Intervalo entre os horários (em minutos)

    @Column(nullable = false)
    var valor: BigDecimal, // Valor (preço) da disponibilidade

    @ElementCollection(targetClass = DiaSemana::class, fetch = FetchType.EAGER)
    @CollectionTable(
        name = "disponibilidade_dias",
        joinColumns = [JoinColumn(name = "id_disponibilidade")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    var diasSemana: MutableSet<DiaSemana> = mutableSetOf(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: StatusDisponibilidade = StatusDisponibilidade.DISPONIVEL
) : PanacheEntity() {

    companion object : PanacheCompanion<Disponibilidade>

    // Construtor vazio protegido para uso do Hibernate
    protected constructor() : this(
        espaco = Espaco(),
        horaInicio = LocalTime.now(),
        horaFim = LocalTime.now(),
        intervalo = 0,
        valor = BigDecimal.ZERO,
        diasSemana = mutableSetOf(),
        status = StatusDisponibilidade.DISPONIVEL
    )

    @OneToOne(mappedBy = "disponibilidade", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reserva: Reserva? = null
}
enum class DiaSemana {
    SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO
}

enum class StatusDisponibilidade {
    DISPONIVEL, RESERVADO
}
