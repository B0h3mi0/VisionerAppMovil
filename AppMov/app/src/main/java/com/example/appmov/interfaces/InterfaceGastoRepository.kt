package com.example.appmov.interfaces

import com.example.appmov.model.Gasto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface InterfaceGastoRepository {
    /*
    * StateFlow es un stream «stateful»: siempre tiene un valor actual (la lista más reciente).
    *  Ideal para UI porque puedes leer el estado actual y reaccionar a cambios.
    * */
    val gastosFlow: StateFlow<List<Gasto>>

    /*
    * suspend fun en las operaciones: las dejamos suspend porque escribir/leer datos puede tardar (DB, red).
    * En la implementación en memoria no bloquea, pero la interfaz ya está preparada.
    * */

    suspend fun addGasto(gasto: Gasto)
    suspend fun updateGasto(gasto: Gasto)
    suspend fun removeGasto(id: Long)


    /*
    * getGastosByCategoria devuelve Flow<List<Gasto>> para que quien lo use reciba actualizaciones
    * cuando cambien los gastos de esa categoría.
    * */
    fun getGastosByCategoria(categoria: String): Flow<List<Gasto>> // flujo filtrado por categoría
}