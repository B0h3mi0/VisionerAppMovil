package com.example.appmov.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.util.Locale
import java.time.format.DateTimeFormatter
import java.time.LocalDate


fun formatCurrencyCLP(amount: Long): String {
    val nf = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return nf.format(amount) // para CLP no usamos decimales
}

@RequiresApi(Build.VERSION_CODES.O)
val simpleDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(d: LocalDate?): String = d?.format(simpleDateFormatter) ?: "Fecha no disponible"