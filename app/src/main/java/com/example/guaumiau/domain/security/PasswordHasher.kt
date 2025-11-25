package com.example.guaumiau.domain.security

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
// aqui usamos la utilidad para ecriptar y verificar con PBKDF2
object PasswordHasher {
    // parametros del algoritmo PBKDF2
    private const val ITER = 65_536 // este es el numero de interacciones que tiene
    private const val KEY_LEN = 256 // y esta la longiutud
    private const val ALG = "PBKDF2WithHmacSHA256"

    @RequiresApi(Build.VERSION_CODES.O)
    fun hash(plain: String): String {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) } // aleatorio
        val spec = PBEKeySpec(plain.toCharArray(), salt, ITER, KEY_LEN)
        val factory = SecretKeyFactory.getInstance(ALG)
        val hash = factory.generateSecret(spec).encoded
        return "${Base64.getEncoder().encodeToString(salt)}:${Base64.getEncoder().encodeToString(hash)}"
    }

    //verifica si la contraseña ingresada coincide con el hash almacenado
    @RequiresApi(Build.VERSION_CODES.O)
    fun verify(plain: String, stored: String): Boolean {
        val (saltB64, hashB64) = stored.split(":")
        val salt = Base64.getDecoder().decode(saltB64)
        val spec = PBEKeySpec(plain.toCharArray(), salt, ITER, KEY_LEN)
        val factory = SecretKeyFactory.getInstance(ALG)
        val calc = factory.generateSecret(spec).encoded
        return calc.contentEquals(Base64.getDecoder().decode(hashB64))
    }
}
