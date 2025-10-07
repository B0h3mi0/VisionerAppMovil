package com.example.appmov.model
/*
* Entrega sumativa 2:  CLASE CategoriGasto
* El objetivo de esta clase es dividir los tipos de gastos en
* categorias para poder organizar de mejor forma el presupuesto mensual, cada Categoria tenga la suma total de sus gastos
*
* */
data class CategoriaGasto (
    val nombre: String,
    val gastos: List<Gasto>
){
    val total: Long
        get() = gastos.sumOf { it.monto } // suma de todos los gastos de la categoria
}