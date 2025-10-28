package com.example.guaumiau.domain.security

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordHasher {
    private const val ITER = 65_536
    private const val KEY_LEN = 256
    private const val ALG = "PBKDF2WithHmacSHA256"

    @RequiresApi(Build.VERSION_CODES.O)
    fun hash(plain: String): String {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        val spec = PBEKeySpec(plain.toCharArray(), salt, ITER, KEY_LEN)
        val factory = SecretKeyFactory.getInstance(ALG)
        val hash = factory.generateSecret(spec).encoded
        return "${Base64.getEncoder().encodeToString(salt)}:${Base64.getEncoder().encodeToString(hash)}"
    }

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
