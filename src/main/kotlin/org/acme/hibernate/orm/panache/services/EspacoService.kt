package org.acme.hibernate.orm.panache.services

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.hibernate.orm.panache.entities.LocalEsportivo
import org.acme.hibernate.orm.panache.entities.TipoUsuario
import org.acme.hibernate.orm.panache.entities.Usuario
import org.acme.hibernate.orm.panache.forms.LocalForm


@ApplicationScoped
class EspacoService {

    @Transactional
    fun createLocal(localForm: LocalForm): LocalEsportivo {
        val user = Usuario.findById(localForm.anfitriao)
            ?: throw IllegalArgumentException("Usuário não encontrado")

        val local = LocalEsportivo(
            localForm.nome,
            localForm.endereco,
            localForm.descricao,
            user

        ).apply { persist() }

        return local
    }

    fun listAll(): List<LocalEsportivo> = LocalEsportivo.listAll()

    fun getLocalById(id: Long): LocalEsportivo = LocalEsportivo.findById(id)
        ?: throw IllegalArgumentException("Local não encontrado")


}