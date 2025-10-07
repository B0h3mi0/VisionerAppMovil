package com.example.appmov.data

import android.content.Context
import android.util.Patterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object RegistroDbHelper {

    data class ValidationResult(
        val ok: Boolean,
        val errores: List<String> = emptyList()
    )

    data class LoginResult(
        val ok: Boolean,
        val mensaje: String? = null,
        val user: User? = null
    )


    // 🔹 Validar datos antes de guardar
    fun validarRegistro(
        context: Context,
        nombre: String,
        correo: String,
        password: String,
        pais: String,
        callback: (ValidationResult) -> Unit
    ) {
        val errores = mutableListOf<String>()

        if (nombre.isBlank()) errores += "El nombre es obligatorio."

        if (correo.isBlank()) {
            errores += "El correo es obligatorio."
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errores += "El correo no tiene un formato válido."
        }

        if (password.isBlank()) {
            errores += "La contraseña es obligatoria."
        } else if (password.length < 6) {
            errores += "La contraseña debe tener al menos 6 caracteres."
        }

        if (pais.isBlank()) errores += "Selecciona un país."

        // 🔹 Validar correo único en la base de datos (consulta en Room)
        if (errores.isEmpty()) {
            val db = AppDataB.getDatab(context)
            CoroutineScope(Dispatchers.IO).launch {
                val userExistente = db.userDao().buscarPorCorreo(correo)
                if (userExistente != null) {
                    callback(ValidationResult(false, listOf("El correo ya está registrado.")))
                } else {
                    callback(ValidationResult(true))
                }
            }
        } else {
            callback(ValidationResult(false, errores))
        }
    }

    // 🔹 Guardar registro solo si pasa validaciones
    fun guardarRegistro(
        context: Context,
        nombre: String,
        correo: String,
        password: String,
        pais: String,
        callback: (ValidationResult) -> Unit
    ) {
        validarRegistro(context, nombre, correo, password, pais) { validacion ->
            if (!validacion.ok) {
                callback(validacion)
                return@validarRegistro
            }

            val user = User(
                nombre = nombre,
                correo = correo,
                password = password,
                pais = pais
            )

            val db = AppDataB.getDatab(context)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    db.userDao().insertar(user)

                    // 👇 Cambio a hilo principal para el callback
                    withContext(Dispatchers.Main) {
                        callback(ValidationResult(true))
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        callback(ValidationResult(false, listOf("Error BD: ${e.message}")))
                    }
                }
            }
        }
    }


    // 🔹 Autenticar contra la base de datos
    fun autenticar(
        context: Context,
        correo: String,
        password: String,
        callback: (LoginResult) -> Unit
    ) {
        val correoNorm = correo.trim().lowercase()
        val db = AppDataB.getDatab(context)

        CoroutineScope(Dispatchers.IO).launch {
            val user = db.userDao().buscarPorCorreo(correoNorm)

            if (user == null) {
                withContext(Dispatchers.Main) {
                    callback(LoginResult(false, "Correo no existe"))
                }
            } else if (user.password != password) {
                withContext(Dispatchers.Main) {
                    callback(LoginResult(false, "Contraseña incorrecta"))
                }
            } else {
                // ✅ Guardar sesión en SharedPreferences
                val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                prefs.edit()
                    .putBoolean("isLogged", true)
                    .putString("correo", user.correo)
                    .putString("nombre", user.nombre)
                    .apply()

                withContext(Dispatchers.Main) {
                    callback(LoginResult(true, user = user))
                }
            }
        }
    }




}