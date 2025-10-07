package com.example.appmov.interfaces

import com.example.appmov.model.Gasto
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicLong
import java.time.LocalDate

class InMemoryGastoRepository : InterfaceGastoRepository {
    private val idCounter = AtomicLong(0)
    private val _gastos = MutableStateFlow<List<Gasto>>(seedData())
    override val gastosFlow: StateFlow<List<Gasto>> = _gastos.asStateFlow()



    override suspend fun addGasto(gasto: Gasto) {
        val withId = gasto.copy(id = idCounter.incrementAndGet().toLong())
        _gastos.update { it + withId }
    }

    override suspend fun updateGasto(gasto: Gasto) {
        _gastos.update { list -> list.map { if (it.id == gasto.id) gasto else it } }
    }

    override suspend fun removeGasto(id: Long) {
        _gastos.update { list -> list.filter { it.id.toLong() != id } }
    }

    override fun getGastosByCategoria(categoria: String): Flow<List<Gasto>> =
        gastosFlow.map { list -> list.filter { it.categoria == categoria } }

    private fun seedData(): List<Gasto> = listOf(
        Gasto( id = idCounter.incrementAndGet(), categoria = "Gastos básicos", descripcion = "Cuenta de luz", monto = 35000L, fecha = LocalDate.now().minusDays(10)),
        Gasto( id = idCounter.incrementAndGet(), categoria = "Entretenimiento", descripcion = "Netflix", monto = 7990L, fecha = LocalDate.now().minusDays(3)),
        Gasto( id = idCounter.incrementAndGet(), categoria = "Deportes", descripcion = "Gimnasio", monto = 25000L, fecha = LocalDate.now())
    )
}
