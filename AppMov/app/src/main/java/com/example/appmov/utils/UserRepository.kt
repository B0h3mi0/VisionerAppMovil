package com.example.appmov.utils

import com.example.appmov.model.User

object UserRepository {
    val usuarios = mutableListOf<User>()

    fun registrarUser(user: User) {
        usuarios.add(user)
    }
    fun validarLogin(correo:String, password: String): Boolean {
        return usuarios.any {it.correo == correo && it.password == password}
    }
}