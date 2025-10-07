package com.example.appmov.auth


data class LoginResult(
    val ok: Boolean,
    val mensaje: String? = null,
    val user: User? = null
)

data class User(val correo: String, val nombre: String, val password: String)

interface AuthRepository {
    fun autenticar(correo: String, password: String): LoginResult
}