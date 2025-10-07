package com.example.appmov.auth


import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.*

class LoginViewModelTest {

    // Se usa un repositorio simulado (mock) para no depender de base de datos real
    private val repo = mock<AuthRepository>()
    private val viewModel = LoginViewModel(repo)

    @Test
    fun `campos vacíos`() {
        // Caso: usuario y contraseña en blanco
        val resultado = viewModel.login("", "")

        // Se espera que el login falle y devuelva el mensaje correcto
        assertFalse(resultado.ok)
        assertEquals("Campos vacíos", resultado.mensaje)

        // Se verifica que nunca se haya llamado al repositorio
        verify(repo, never()).autenticar(any(), any())
    }

    @Test
    fun `correo inválido`() {
        // Caso: correo sin arroba
        val resultado = viewModel.login("correoInvalido", "1234")

        // Se espera fallo con mensaje de correo inválido
        assertFalse(resultado.ok)
        assertEquals("Correo inválido", resultado.mensaje)

        // Nunca debe llamar al repositorio
        verify(repo, never()).autenticar(any(), any())
    }

    @Test
    fun `password demasiado corta`() {
        // Caso: contraseña de menos de 4 caracteres
        val resultado = viewModel.login("miguel@mail.com", "123")

        // Se espera fallo con mensaje de contraseña corta
        assertFalse(resultado.ok)
        assertEquals("Contraseña demasiado corta", resultado.mensaje)

        // Nunca debe llamar al repositorio
        verify(repo, never()).autenticar(any(), any())
    }

    @Test
    fun `login correcto`() {
        // Caso: credenciales válidas
        val user = User("seba1212@gmail.com", "Sebastian", "123456")

        // Se prepara el mock para que devuelva éxito con este usuario
        whenever(repo.autenticar("seba1212@gmail.com", "123456"))
            .thenReturn(LoginResult(true, user = user))

        // Se ejecuta el login
        val resultado = viewModel.login("seba1212@gmail.com", "123456")

        // Se espera éxito y que el usuario no sea nulo
        assertTrue(resultado.ok)
        assertNotNull(resultado.user)
        assertEquals("seba1212@gmail.com", resultado.user?.correo)

        // Verificar que el repositorio fue invocado correctamente
        verify(repo).autenticar("seba1212@gmail.com", "123456")
    }
}