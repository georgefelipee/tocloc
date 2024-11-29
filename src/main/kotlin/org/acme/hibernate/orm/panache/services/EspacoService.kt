package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.entities.Espaco
import org.acme.hibernate.orm.panache.entities.LocalEsportivo
import org.acme.hibernate.orm.panache.forms.EspacoForm


@ApplicationScoped
class EspacoService {

    @Transactional
    fun createEspaco(espacoForm: EspacoForm): Response {
        print("Criando Espaço...")
        print(espacoForm.local)
        // Garantir que o LocalEsportivo exista
        val local = LocalEsportivo.findById(espacoForm.local)
            ?: throw IllegalArgumentException("Local não encontrado para o ID fornecido.")

        // Criar o objeto Espaco com dados válidos
        val espaco = Espaco(
            nome = espacoForm.nome,
            tipoEsporte = espacoForm.tipoEsporte,
            descricao = espacoForm.descricao,
            local = local // Certifique-se de que local está persistido
        )

        // Persistir a entidade
        espaco.persist()
        return Response.status(Response.Status.CREATED).entity(espaco).build()
    }


}