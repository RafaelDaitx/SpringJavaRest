package api_rest_kotlin.services

import api_rest_kotlin.controller.BookController
import api_rest_kotlin.data.vo.v1.BookVO
import api_rest_kotlin.exceptions.RequiredObjectsNullException
import api_rest_kotlin.exceptions.ResourceNotFoundException
import api_rest_kotlin.mapper.DozerMapper
import api_rest_kotlin.model.Book
import api_rest_kotlin.repository.BookRepository
import api_rest_kotlin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(@field:Autowired var repository: UserRepository ) : UserDetailsService {

    private val logger = Logger.getLogger(UserService::class.java.name)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Finding one User by Username $username!")

        val user = repository.findByUsername(username)

        //Se não tiver user, joga o throw (operador Elvis)
        return user ?: throw UsernameNotFoundException("Username $username not found")

    }

}