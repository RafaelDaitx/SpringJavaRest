package api_rest_kotlin.services

import api_rest_kotlin.data.vo.v1.AccountCredentialsVO
import api_rest_kotlin.data.vo.v1.TokenVO
import api_rest_kotlin.repository.UserRepository
import api_rest_kotlin.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger
import javax.naming.AuthenticationException

@Service
class AuthService {

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun signin(data: AccountCredentialsVO) : ResponseEntity<*>{
        return try {
            val username = data.username
            val password = data.password

            //authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username,password))
            val user = repository.findByUsername(username)

            val tokenResponse: TokenVO = if(user != null){
                tokenProvider.createAccessToken(username!!, user.roles)
            } else {
                throw UsernameNotFoundException("Username $username not found!")
            }
            ResponseEntity.ok(tokenResponse)
        }catch (e: AuthenticationException){
            throw BadCredentialsException("Invalid username or password supplied!")
        }
    }

    fun refreshToken(username: String, refreshToken: String) : ResponseEntity<*> {
        val user = repository.findByUsername(username)

        val tokenResponse: TokenVO = if (user != null) {
            tokenProvider.refreshToken(refreshToken!!)
        } else {
            throw UsernameNotFoundException("Username $username not found!")
        }
        return ResponseEntity.ok(tokenResponse)
    }
}