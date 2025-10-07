package com.example.appmov.data

// DAO (Data Acces Object) para manejar SQLite con room library
// Room es una capa de abstraccion de SQLite
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun  insertar(user: User)

    @Query("SELECT * FROM users WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): User?

}