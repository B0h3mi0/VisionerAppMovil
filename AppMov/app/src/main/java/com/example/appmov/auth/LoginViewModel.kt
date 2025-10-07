package com.example.appmov.auth


class LoginViewModel(private val repo: AuthRepository) {

    fun login(correo: String, password: String): LoginResult {
        // 1. Validar que los campos no estén vacíos
        if (correo.isBlank() || password.isBlank()) {
            return LoginResult(false, "Campos vacíos")
        }

        // 2. Validar formato mínimo del correo (que contenga @)
        if (!correo.contains("@")) {
            return LoginResult(false, "Correo inválido")
        }

        // 3. Validar longitud mínima de la contraseña
        if (password.length < 4) {
            return LoginResult(false, "Contraseña demasiado corta")
        }

        // 4. Si pasa las validaciones, delegar la autenticación al repositorio
        val resultado = repo.autenticar(correo, password)

        // 5. Si el repositorio confirma login correcto, devolver el usuario
        return if (resultado.ok) {
            LoginResult(true, user = resultado.user)
        } else {
            // Si no, devolver el error del repositorio
            resultado
        }
    }
}