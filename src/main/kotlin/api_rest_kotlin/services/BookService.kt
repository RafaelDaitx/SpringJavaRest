package api_rest_kotlin.services

import api_rest_kotlin.controller.BookController
import api_rest_kotlin.data.vo.v1.BookVO
import api_rest_kotlin.data.vo.v1.PersonVO
import api_rest_kotlin.exceptions.RequiredObjectsNullException
import api_rest_kotlin.exceptions.ResourceNotFoundException
import api_rest_kotlin.mapper.DozerMapper
import api_rest_kotlin.model.Book
import api_rest_kotlin.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<BookVO>


    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<BookVO>> {
        logger.info("Finding all books!")

        val books = repository.findAll(pageable)
        val vos = books.map { b -> DozerMapper.parseObject(b, BookVO::class.java) }
        vos.map { b-> b.add(linkTo(BookController::class.java).slash(b.key).withSelfRel()) }

        return assembler.toModel(vos)
    }

    fun findById(id: Long): BookVO {
        logger.info("Finding one book with ID $id!")

        val book = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val bookVO: BookVO =  DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO
    }

    fun create(book: BookVO): BookVO {
        if(book == null) throw RequiredObjectsNullException()

        logger.info("Create one book with title ${book.title}!")
        //Converto para uma entidade para persistir no banco
        var entity: Book =  DozerMapper.parseObject(book, Book::class.java)

        //Converto para VO novamente para devolver para a controller
        val bookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)

        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO
    }

    fun update(book: BookVO): BookVO {
        logger.info("Updating one book with name ${book.key}!")
        val entity = repository.findById(book.key)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        entity.author = book.author
        entity.title = book.title
        entity.launchDate = book.launchDate
        entity.price = book.price

        val bookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one book with name ${id}!")

        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        repository.delete(entity)
    }

}