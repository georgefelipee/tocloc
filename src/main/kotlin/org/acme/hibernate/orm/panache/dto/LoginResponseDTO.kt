package org.acme.hibernate.orm.panache.dto
data class LoginResponseDTO(
    val token: TokenDTO,
    val usuario: UsuarioDTO
)

data class TokenDTO(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val tokenType: String
)

data class UsuarioDTO(
    val id: Long,
    val nome: String,
    val email: String,
    val tipoUsuario: String
)
