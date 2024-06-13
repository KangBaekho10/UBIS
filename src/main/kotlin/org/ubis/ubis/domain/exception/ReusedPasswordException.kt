package org.ubis.ubis.domain.exception

data class ReusedPasswordException (
    override val message: String
): RuntimeException(message)