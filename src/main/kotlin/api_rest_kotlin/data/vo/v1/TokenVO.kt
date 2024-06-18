package api_rest_kotlin.data.vo.v1

import java.util.*

data class TokenVO(
    val username: String? = null,
    val authenticate: Boolean? = null,
    val created: Date? = null,
    val expiration: Date? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null

    //VO de autenticacao, quando foi feito o login
)
