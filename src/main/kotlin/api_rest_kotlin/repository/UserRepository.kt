package api_rest_kotlin.repository

import api_rest_kotlin.model.Book
import api_rest_kotlin.model.Person
import api_rest_kotlin.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User?, Long?>{

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    fun findByUsername(@Param("userName") userName: String?) : User?
}