package com.example.appmov.model

import java.time.LocalDate

/*
* Entrega sumativa 2: Esta clase tiene como fin identificar los gastos con detalle
*
*
* */
data class Gasto (
    val id: Long,
    val categoria: String,
    val descripcion: String,
    val monto: Long,
    val fecha: LocalDate?
) {

}