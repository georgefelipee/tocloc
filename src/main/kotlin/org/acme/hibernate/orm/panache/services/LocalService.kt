package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.hibernate.orm.panache.entities.LocalEsportivo
import org.acme.hibernate.orm.panache.entities.TipoUsuario
import org.acme.hibernate.orm.panache.entities.Usuario
import org.acme.hibernate.orm.panache.forms.LocalForm


@ApplicationScoped
class LocalService {

    @Transactional
    fun createLocal(localForm: LocalForm): LocalEsportivo {
        val user = Usuario.findById(localForm.anfitriao)
            ?: throw IllegalArgumentException("Usuário não encontrado")

        val local = LocalEsportivo(
            nome = localForm.nome,
            endereco = localForm.endereco,
            tipoEsporte = localForm.tipoEsporte,
            descricao = localForm.descricao,
            anfitriao = user
        ).apply { persist() }

        return local
    }



}