package com.example.appmov.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Clase User, aqui definimos los atributos que tiene user.
@Entity (tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val password: String,
    val pais: String
)