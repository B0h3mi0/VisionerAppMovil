package com.example.appmov.model

data class PresupuestoMensual (
    val ingresoMensual: Long,
    val categorias: List<CategoriaGasto>
){
    val saldoDisponible: Long
        get() = ingresoMensual - categorias.sumOf { it.total }
}