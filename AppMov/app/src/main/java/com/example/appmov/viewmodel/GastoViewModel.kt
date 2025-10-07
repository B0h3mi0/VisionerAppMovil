package com.example.appmov.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmov.interfaces.InMemoryGastoRepository
import com.example.appmov.interfaces.InterfaceGastoRepository
import com.example.appmov.model.CategoriaGasto
import com.example.appmov.model.Gasto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

open class GastoViewModel : ViewModel() {

    private val repo: InterfaceGastoRepository = InMemoryGastoRepository() // repositorio en memoria

    val gastos: StateFlow<List<Gasto>> = repo.gastosFlow

    val categoriasConTotales: StateFlow<List<CategoriaGasto>> =
        gastos.map { list ->
            list.groupBy { it.categoria }
                .map { (cat, gastosDeCat) -> CategoriaGasto(nombre = cat, gastos = gastosDeCat) }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _ingresoMensual = MutableStateFlow(500000L)
    val ingresoMensual: StateFlow<Long> = _ingresoMensual.asStateFlow()

    val saldoDisponible: StateFlow<Long> =
        combine(_ingresoMensual, categoriasConTotales) { ingreso, cats ->
            ingreso - cats.sumOf { it.total }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, 0L)

    @RequiresApi(Build.VERSION_CODES.O)
    fun addGasto(categoria: String, descripcion: String, monto: Long, fecha: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            repo.addGasto(Gasto(id = 0L, categoria = categoria, descripcion = descripcion, monto = monto, fecha = fecha))
        }
    }

    fun removeGasto(id: Long) {
        viewModelScope.launch { repo.removeGasto(id) }
    }

    fun updateIngresoMensual(nuevo: Long) {
        _ingresoMensual.value = nuevo
    }

    open fun gastosPorCategoriaFlow(categoria: String): StateFlow<List<Gasto>> =
        repo.getGastosByCategoria(categoria)
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
