package xyz.dnieln7.portfolio.domain.model

data class Authentication(
    val successful: Boolean,
    val message: String,
    val token: String,
)
