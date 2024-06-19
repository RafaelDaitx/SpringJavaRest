package api_rest_kotlin.integrationtest.vo

import java.util.*
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement
data class TokenVO(
    var username: String? = null,
    var authenticate: Boolean? = null,
    var created: Date? = null,
    var expiration: Date? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null

    //VO de autenticacao, quando foi feito o login
)
