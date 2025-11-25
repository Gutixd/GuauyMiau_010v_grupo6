package com.example.guaumiau.data.repository

import com.example.guaumiau.data.local.dao.UserDao
import com.example.guaumiau.data.local.entity.User
import com.example.guaumiau.data.session.SessionManager
import com.example.guaumiau.domain.security.PasswordHasher
import com.example.guaumiau.domain.validation.ValidationError
import com.example.guaumiau.domain.validation.Validators
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
//  Repositorio encargado del registro, login y manejo de sesión del usuario
@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val session: SessionManager
) {
    /**
     * Registra un usuario nuevo después de validar sus datos
     * Retorna éxito o error según el resultado de la operación
     */
    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        phone: String?
    ): Result<Unit> {
        // validaciones de campos
        val errors = buildList {
            addAll(Validators.validateFullName(fullName))
            addAll(Validators.validateEmail(email))
            addAll(Validators.validatePassword(password))
            addAll(Validators.validatePhone(phone))
        }
        if (errors.isNotEmpty())
            return Result.failure(IllegalArgumentException(errors.joinToString()))
        // Hash de contraseña e inserción
        val hash = PasswordHasher.hash(password)
        return try {
            userDao.insert(
                User(
                    fullName = fullName.trim(),
                    email = email.lowercase(),
                    passwordHash = hash,
                    phone = phone
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            //gmail duplicado
            Result.failure(IllegalStateException(ValidationError.EmailAlreadyUsed.toString()))
        }
    }

    suspend fun login(email: String, plainPassword: String): Result<Long> {
        val user = userDao.getByEmail(email.lowercase())
            ?: return Result.failure(IllegalArgumentException("Credenciales inválidas"))
        return if (PasswordHasher.verify(plainPassword, user.passwordHash)) {
            session.setCurrentUser(user.id)
            Result.success(user.id)
        } else Result.failure(IllegalArgumentException("Credenciales inválidas"))
    }

    //cierra sesion del usuario actual
    suspend fun logout() { session.setCurrentUser(null) }
    // devuelve el ID del usuario logeado como Flow
    fun currentUserId(): Flow<Long?> = session.currentUserId
}
