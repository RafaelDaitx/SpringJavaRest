package api_rest_kotlin.repository

import api_rest_kotlin.model.Person
import api_rest_kotlin.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long?>{

    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
    fun DisablePerson(@Param("id") id: Long?)

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%',':firstName','%'))")
    fun FindPersonByName(@Param("firstName") firstName: String, pageable: Pageable): Page<Person>
}