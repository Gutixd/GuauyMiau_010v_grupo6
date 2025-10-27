package com.example.guaumiau.domain.validation

sealed interface ValidationError {
    // Nombre
    data object NameEmpty : ValidationError
    data object NameInvalidChars : ValidationError
    data object NameTooLong : ValidationError

    // Email
    data object EmailInvalidFormat : ValidationError
    data object EmailNotDuoc : ValidationError
    data object EmailAlreadyUsed : ValidationError

    // Password
    data object PasswordTooShort : ValidationError
    data object PasswordNoUpper : ValidationError
    data object PasswordNoLower : ValidationError
    data object PasswordNoDigit : ValidationError
    data object PasswordNoSpecial : ValidationError
    data object ConfirmPasswordMismatch : ValidationError

    // Teléfono
    data object PhoneInvalid : ValidationError

    // Mascotas
    data object PetTypeMissing : ValidationError
    data object PetNameEmpty : ValidationError
    data object PetNameTooLong : ValidationError
}
