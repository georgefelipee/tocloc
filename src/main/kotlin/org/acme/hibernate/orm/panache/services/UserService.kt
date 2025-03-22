package org.acme.hibernate.orm.panache.services

import com.arjuna.ats.jdbc.TransactionalDriver.password
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.acme.hibernate.orm.panache.dto.LoginResponseDTO
import org.acme.hibernate.orm.panache.dto.TokenDTO
import org.acme.hibernate.orm.panache.dto.UsuarioDTO
import org.acme.hibernate.orm.panache.entities.TipoUsuario
import org.acme.hibernate.orm.panache.entities.Usuario
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import java.util.*


data class KeycloakTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val tokenType: String
)

@ApplicationScoped
class UserService {

    var keycloakUrl: String = "https://keycloack.gfsoftwarebrasil.com.br"


    var realm: String = "desenvolvimento"

    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "quarkus.oidc.client-id")
    lateinit var clientId: String

    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "quarkus.oidc.credentials.secret")
    lateinit var clientSecret: String

    @Transactional
    fun createUser(nome: String, email: String, senha: String, tipoUsuario: TipoUsuario): Usuario {
       Usuario.find("email", email).firstResult()?.let {
            throw IllegalArgumentException("Email já cadastrado")
       }
        criarUsuarioNoKeycloak(email, nome, senha, tipoUsuario.name)

        val usuario = Usuario(nome, email, senha, tipoUsuario)
        usuario.persist()
        return usuario
    }
    @Transactional
    fun loginUser(email: String, senha: String): LoginResponseDTO {
        val usuario = Usuario.find("email = ?1 and senha = ?2", email, senha).firstResult()
            ?: throw IllegalArgumentException("Credenciais inválidas")

        if (!usuarioExisteNoKeycloak(email)) {
            criarUsuarioNoKeycloak(email, usuario.nome, senha, usuario.tipoUsuario.name)
        }

        val token = obterTokenDoKeycloak(email, senha)
            ?: throw RuntimeException("Erro ao obter token do Keycloak")

        return LoginResponseDTO(
            token = TokenDTO(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
                expiresIn = token.expiresIn,
                tokenType = token.tokenType
            ),
            usuario = UsuarioDTO(
                id = usuario.id!!,
                nome = usuario.nome,
                email = usuario.email,
                tipoUsuario = usuario.tipoUsuario.name
            )
        )
    }


    fun obterTokenDoKeycloak(email: String, senha: String): KeycloakTokenResponse? {
        val client = HttpClients.createDefault()
        val url = "$keycloakUrl/realms/$realm/protocol/openid-connect/token"

        val request = HttpPost(url)

        val params: List<NameValuePair> = listOf(
            BasicNameValuePair("grant_type", "password"),
            BasicNameValuePair("client_id", clientId),
            BasicNameValuePair("username", email),
            BasicNameValuePair("password", senha),
            BasicNameValuePair("client_secret", clientSecret)
        )
        request.entity = UrlEncodedFormEntity(params)

        val response = client.execute(request)
        val statusCode = response.statusLine.statusCode
        val json = response.entity.content.bufferedReader().readText()

        client.close()

        return if (statusCode == 200) {
            val jsonObject = JsonObject(json)
            KeycloakTokenResponse(
                accessToken = jsonObject.getString("access_token"),
                refreshToken = jsonObject.getString("refresh_token"),
                expiresIn = jsonObject.getInteger("expires_in"),
                tokenType = jsonObject.getString("token_type")
            )
        } else null
    }
   private  fun usuarioExisteNoKeycloak(email: String): Boolean {
        val client: CloseableHttpClient = HttpClients.createDefault()
        val url = "$keycloakUrl/admin/realms/$realm/users?username=$email"

        val request = HttpGet(url)
        request.addHeader("Authorization", "Bearer ${obterServiceTokenManageUsers()}")

        val response = client.execute(request)
        response.let { if (it.statusLine.statusCode != 200) throw IllegalArgumentException("Erro ao buscar usuário no Keycloak") }
        val jsonResponse = JsonArray(response.entity.content.reader().readText())
        client.close()

        return jsonResponse.isEmpty.not()
    }

    private fun criarUsuarioNoKeycloak(email: String, nome: String, senha: String, grupo: String?): Boolean {
        val client: CloseableHttpClient = HttpClients.createDefault()
        val url = "$keycloakUrl/admin/realms/$realm/users"

        val request = HttpPost(url)
        request.addHeader("Content-Type", "application/json")
        request.addHeader("Authorization", "Bearer ${obterServiceTokenManageUsers()}")

        val userJson = JsonObject()
        userJson.put("enabled", true)
        userJson.put("username", email)
        userJson.put("email", email)
        userJson.put("firstName", nome)
        userJson.put("credentials", listOf(
            mapOf("type" to "password", "value" to senha, "temporary" to false)
        ))

        if (grupo != null) {
            userJson.put("groups", listOf(grupo))
        }

        request.entity = StringEntity(userJson.toString())

        val response: CloseableHttpResponse = client.execute(request)
        val statusCode = response.statusLine.statusCode

        client.close()
        return statusCode == Response.Status.CREATED.statusCode
    }

    private fun obterServiceToken(): String {
        val client: CloseableHttpClient = HttpClients.createDefault()
        val url = "$keycloakUrl/realms/$realm/protocol/openid-connect/token"

        val request = HttpPost(url)
        request.addHeader("Content-Type", "application/x-www-form-urlencoded")

        val body = "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"
        request.entity = StringEntity(body)

        val response: CloseableHttpResponse = client.execute(request)
        val jsonResponse = JsonObject(response.entity.content.reader().readText())
        client.close()

        return jsonResponse.getString("access_token")
    }

    private fun obterServiceTokenManageUsers(): String {
        val client: CloseableHttpClient = HttpClients.createDefault()
        val url = "$keycloakUrl/realms/$realm/protocol/openid-connect/token"

        val request = HttpPost(url)
        request.addHeader("Content-Type", "application/x-www-form-urlencoded")

        val body = "grant_type=client_credentials&client_id=user-management-client-tocloc-quarkus&client_secret=WpctPKe5PrN72N4UXtfSwWl05rV6JYbM"
        request.entity = StringEntity(body)

        val response: CloseableHttpResponse = client.execute(request)
        val jsonResponse = JsonObject(response.entity.content.reader().readText())
        client.close()

        return jsonResponse.getString("access_token")
    }


    @Transactional
    fun getAllUsers(): List<Usuario> {
        return Usuario.listAll()  // Retorna todos os usuários
    }

    @Transactional
    fun getUserById(id: Long): Usuario {
        return Usuario.findById(id) ?: throw IllegalArgumentException("Usuário não encontrado")
    }

    @Transactional
    fun getUserByEmail(email: String): Usuario? {
        return Usuario.find("email", email).firstResult()  // Retorna o usuário pelo email ou null
    }
}