package com.example.appmov.utils

import android.util.Patterns

object RegistroUtils {
    // Variable global en el archivo (antes de cualquier función o @Composable)
    val registros = mutableListOf<Map<String, String>>()



    data class ValidationResult(
        val ok: Boolean,
        val errores: List<String> = emptyList()
    )

    data class LoginResult(
        val ok: Boolean,
        val mensaje: String? = null,
        val usuario: Map<String, String>? = null
    )


    /** Función genérica para validar datos */
    fun validarRegistro(
        nombre: String,
        correo: String,
        password: String,
        pais: String
    ): ValidationResult {
        val errores = mutableListOf<String>()

        if (nombre.isBlank()) errores += "El nombre es obligatorio."

        if (correo.isBlank()) {
            errores += "El correo es obligatorio."
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errores += "El correo no tiene un formato válido."
        } else if (registros.any { it["correo"] == correo }) {
            errores += "El correo ya está registrado."
        }

        if (password.isBlank()) {
            errores += "La contraseña es obligatoria."
        } else if (password.length < 6) {
            errores += "La contraseña debe tener al menos 6 caracteres."
        }

        if (pais.isBlank()) errores += "Selecciona un país."

        return if (errores.isEmpty()) ValidationResult(true) else ValidationResult(false, errores)
    }



    fun guardarUsuario(nombre: String, password: String, correo: String, pais: String) : ValidationResult {
        val validacion = validarRegistro(
            nombre,
            correo,
            password,
            pais
        )
        if (!validacion.ok) return validacion

        val registro = mapOf(
            "nombre" to nombre,
            "correo" to correo,
            "password" to password,
            "pais" to pais
        )
        registros.add(registro)

        return ValidationResult(true)
    }

    /**
     * Autentica por correo y password contra 'registros'.
     * Normaliza el correo (trim y lowercase).
     */
    fun autenticar(correo: String, password: String): RegistroUtils.LoginResult {
        val correoNorm = correo.trim().lowercase()
        val user = RegistroUtils.registros.firstOrNull { it["correo"]?.trim()?.lowercase() == correoNorm }
            ?: return LoginResult(false, "Usuario o contraseña incorrectos")

        return if (user["password"] == password) {
            LoginResult(true, usuario = user)
        } else {
            LoginResult(false, "Usuario o contraseña incorrectos")
        }
    }

}