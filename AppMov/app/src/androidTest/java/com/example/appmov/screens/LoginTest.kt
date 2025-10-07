package com.example.appmov.screens


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_usuarioValido_simulado() {
        var loginSuccess = false

        // Renderizamos LoginApp (sin cambios)
        composeTestRule.setContent {
            LoginScreen(
                onHomeClick = { loginSuccess = true } // Simulamos éxito
            )
        }

        // Simulamos entrada de usuario válido
        composeTestRule.onNodeWithText("Ingrese correo").performTextInput("sebascarrenom@gmail.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123456")

        // Simulamos clic en botón
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()

        // Como en este test simulamos login correcto → debe ser true
        assert(loginSuccess)
    }

    @Test
    fun loginScreen_usuarioInvalido_simulado() {
        var loginSuccess = false

        // Renderizamos LoginApp (sin cambios)
        composeTestRule.setContent {
            LoginScreen(
                onHomeClick = { loginSuccess = true } // Simulamos éxito
            )
        }

        // Simulamos entrada de usuario inválido
        composeTestRule.onNodeWithText("Ingrese correo").performTextInput("otro@mail.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("wrong")

        // Simulamos clic en botón
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()

        // En este caso esperamos que loginSuccess siga en false
        assert(!loginSuccess)
    }
}