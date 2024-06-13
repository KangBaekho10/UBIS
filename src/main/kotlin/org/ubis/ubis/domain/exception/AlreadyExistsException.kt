package org.ubis.ubis.domain.exception

data class AlreadyExistsException(
    val value: String,
    val parameterName: String
) : RuntimeException("${value}은 이미 존재하는 ${parameterName}입니다.")