package com.example.guaumiau.domain.validation

sealed interface ValidationError {
    // nombre
    data object NameEmpty : ValidationError
    data object NameInvalidChars : ValidationError
    data object NameTooLong : ValidationError

    // gmail
    data object EmailInvalidFormat : ValidationError
    data object EmailNotDuoc : ValidationError
    data object EmailAlreadyUsed : ValidationError

    // contra
    data object PasswordTooShort : ValidationError
    data object PasswordNoUpper : ValidationError
    data object PasswordNoLower : ValidationError
    data object PasswordNoDigit : ValidationError
    data object PasswordNoSpecial : ValidationError
    data object ConfirmPasswordMismatch : ValidationError

    // celular
    data object PhoneInvalid : ValidationError

    // animales
    data object PetTypeMissing : ValidationError
    data object PetNameEmpty : ValidationError
    data object PetNameTooLong : ValidationError
}
