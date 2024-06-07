package api_rest_kotlin.controller

import api_rest_kotlin.data.vo.v1.PersonVO
import api_rest_kotlin.data.vo.v2.PersonVO as PersonVOV2
import api_rest_kotlin.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person/v1")
class PersonController {

    @Autowired
    private lateinit var service: PersonService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<PersonVO> {
        return service.findAll()
    }

    @RequestMapping("/{id}", method = [RequestMethod.GET],
                    produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByid(@PathVariable(value="id") id: Long): PersonVO {
                return service.findById(id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
                 produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody person: PersonVO): PersonVO {
                return service.create(person)
    }

    @PostMapping(value = ["/V2"],
                 consumes = [MediaType.APPLICATION_JSON_VALUE],
                 produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createV2(@RequestBody person: PersonVOV2): PersonVOV2 {
                return service.createV2(person)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
                produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun update(@RequestBody person: PersonVO): PersonVO {
                return service.update(person)
    }

    @DeleteMapping("/{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable(value="id") id: Long) : ResponseEntity<*>{
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}