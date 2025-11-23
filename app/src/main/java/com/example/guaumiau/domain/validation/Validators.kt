package com.example.guaumiau.domain.validation

import android.util.Patterns

object Validators {
    private val nameRegex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,50}$")
    private val duocRegex = Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$")

    fun validateFullName(name: String) = buildList {
        if (name.isBlank()) add(ValidationError.NameEmpty)
        else if (!nameRegex.matches(name)) add(ValidationError.NameInvalidChars)
    }

    fun validateEmail(email: String) = buildList {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) add(ValidationError.EmailInvalidFormat)
        if (!duocRegex.matches(email)) add(ValidationError.EmailNotDuoc)
    }

    fun validatePassword(pw: String) = buildList {
        if (pw.length < 8) add(ValidationError.PasswordTooShort)
        if (!pw.any(Char::isUpperCase)) add(ValidationError.PasswordNoUpper)
        if (!pw.any(Char::isLowerCase)) add(ValidationError.PasswordNoLower)
        if (!pw.any(Char::isDigit)) add(ValidationError.PasswordNoDigit)
        if (!pw.any { "!@#\$%^&*()_+-=[]{}|;':\",./<>?".contains(it) }) add(ValidationError.PasswordNoSpecial)
    }

    fun validateConfirmPassword(pw: String, confirm: String) = buildList {
        if (pw != confirm) add(ValidationError.ConfirmPasswordMismatch)
    }

    fun validatePhone(phone: String?) = buildList {
        if (phone.isNullOrBlank()) return@buildList
        val clean = phone.filter(Char::isDigit)
        if (clean.length !in 8..12) add(ValidationError.PhoneInvalid)
    }
}
