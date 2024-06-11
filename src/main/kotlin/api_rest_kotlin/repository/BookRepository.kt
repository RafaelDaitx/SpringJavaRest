package api_rest_kotlin.repository

import api_rest_kotlin.model.Book
import api_rest_kotlin.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long?>{
}