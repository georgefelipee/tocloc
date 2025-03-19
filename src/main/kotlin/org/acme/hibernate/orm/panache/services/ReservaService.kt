package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.hibernate.orm.panache.entities.*


@ApplicationScoped
class ReservaService {


    @Transactional
    fun criarReserva(
        disponibilidadeId: Long,
        usuarioId: Long
    ): Reserva {
        // Busca a disponibilidade pelo ID
        val disponibilidade = Disponibilidade.findById(disponibilidadeId)
            ?: throw IllegalArgumentException("Disponibilidade com ID $disponibilidadeId não encontrada.")

        // Valida se a disponibilidade já está reservada
        if (disponibilidade.status == StatusDisponibilidade.RESERVADO) {
            throw IllegalArgumentException("A disponibilidade com ID $disponibilidadeId já está reservada.")
        }

        // Busca o usuário pelo ID
        val usuario = Usuario.findById(usuarioId)
            ?: throw IllegalArgumentException("Usuário com ID $usuarioId não encontrado.")

        // Cria e persiste a reserva
        val reserva = Reserva(
            usuario = usuario,
            disponibilidade = disponibilidade
        ).apply { persist() }

        // Atualiza a disponibilidade para 'RESERVADO' e associa a reserva
        disponibilidade.apply {
            status = StatusDisponibilidade.RESERVADO
            this.reserva = reserva
        }.persist()

        return reserva
    }





}