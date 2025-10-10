package com.example.appmov.screens


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.appmov.utils.RegistroUtils
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_usuarioValido_simulado() {
        var loginSuccess = false

        composeTestRule.setContent {
            LoginScreen(
                onHomeClick = { loginSuccess = true },
                onForgotPassword = {},
                onBack = {},
                onRegisterClick = {},
                autenticarFn = { correo, password ->
                    if (correo == "sebascarrenom@gmail.com" && password == "123456") {
                        RegistroUtils.LoginResult(true)
                    } else {
                        RegistroUtils.LoginResult(false, "Usuario o contraseña incorrectos")
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag("correoField").performTextInput("sebascarrenom@gmail.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("123456")
        composeTestRule.onNodeWithTag("loginButton").performClick()

        assert(loginSuccess)
    }

    @Test
    fun loginScreen_usuarioInvalido_simulado() {
        var loginSuccess = false

        // Renderizamos LoginApp (sin cambios)
        composeTestRule.setContent {
            LoginScreen(
                onHomeClick = { loginSuccess = true }, // Simulamos éxito
                onForgotPassword = {},
                onBack = {},
                onRegisterClick = {}
            )
        }

        // Simulamos entrada de usuario inválido
        composeTestRule.onNodeWithTag("correoField").performTextInput("otro@mail.com")

        composeTestRule.onNodeWithTag("passwordField").performTextInput("wrong")

        composeTestRule.onNodeWithTag("loginButton").performClick()

        // En este caso esperamos que loginSuccess siga en false
        assert(!loginSuccess)
    }
}