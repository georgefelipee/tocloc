package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.entities.Disponibilidade
import org.acme.hibernate.orm.panache.entities.Espaco
import org.acme.hibernate.orm.panache.entities.LocalEsportivo
import org.acme.hibernate.orm.panache.forms.DisponibilidadeForm
import org.acme.hibernate.orm.panache.forms.EspacoForm
import java.math.BigDecimal
import java.time.LocalTime


@ApplicationScoped
class DisponibilidadeService {

    @Transactional
    fun persistirDisponibilidades(
        espaco: Espaco,
        horaInicio: LocalTime,
        horaFim: LocalTime,
        intervalo: Int,
        valor: BigDecimal,
        diasSemana: Set<org.acme.hibernate.orm.panache.entities.DiaSemana>
    ): List<Disponibilidade> {
        val disponibilidades = mutableListOf<Disponibilidade>()
        // Excluir disponibilidades existentes para os dias especificados
        diasSemana.forEach { dia ->
            Disponibilidade.delete("espaco = :espaco AND :dia MEMBER OF diasSemana",
                mapOf("espaco" to espaco, "dia" to dia))
        }


        diasSemana.forEach { dia ->
            var horarioAtual = horaInicio

            while (horarioAtual.plusMinutes(intervalo.toLong()) <= horaFim) {
                val proximoHorario = horarioAtual.plusMinutes(intervalo.toLong())

                // Cria uma nova disponibilidade
                val disponibilidade = Disponibilidade(
                    espaco = espaco,
                    horaInicio = horarioAtual,
                    horaFim = proximoHorario,
                    intervalo = intervalo,
                    diasSemana = mutableSetOf(dia),
                    valor = valor,
                    status = org.acme.hibernate.orm.panache.entities.StatusDisponibilidade.DISPONIVEL
                )

                // Adiciona à lista e persiste no banco
                disponibilidades.add(disponibilidade)
                disponibilidade.persist()

                // Move para o próximo intervalo
                horarioAtual = proximoHorario
            }
        }

        return disponibilidades
    }



    fun criarDisponibilidades(espacoId: Long, form: DisponibilidadeForm): List<Disponibilidade> {
        val espaco = Espaco.findById(espacoId)
            ?: throw IllegalArgumentException("Espaço não encontrado")

        // Conversão de horaInicio e horaFim para LocalTime
        val horaInicio = LocalTime.parse(form.horaInicio)
        val horaFim = LocalTime.parse(form.horaFim)

        if (horaInicio.isAfter(horaFim)) {
            throw IllegalArgumentException("A hora de início deve ser anterior à hora de fim.")
        }

        val intervalo = form.intervalo
        val diasSemana = form.diasSemana

        return persistirDisponibilidades(espaco, horaInicio, horaFim, intervalo,form.valor, diasSemana)
    }

}