package api_rest_kotlin.services

import api_rest_kotlin.controller.PersonController
import api_rest_kotlin.data.vo.v1.PersonVO
import api_rest_kotlin.exceptions.RequiredObjectsNullException
import api_rest_kotlin.data.vo.v2.PersonVO as PersonVOV2
import api_rest_kotlin.exceptions.ResourceNotFoundException
import api_rest_kotlin.mapper.DozerMapper
import api_rest_kotlin.mapper.custom.PersonMapper
import api_rest_kotlin.model.Person
import api_rest_kotlin.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")

        val persons = repository.findAll()
        val vos = DozerMapper.parseListObject(persons, PersonVO::class.java)
        for (person in vos){
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id!")

        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val personVO: PersonVO =  DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }

    fun create(person: PersonVO): PersonVO {
        if(person == null) throw RequiredObjectsNullException()

        logger.info("Create one person with name ${person.firstName}!")
        //Converto para uma entidade para persistir no banco
        var entity: Person =  DozerMapper.parseObject(person, Person::class.java)

        //Converto para VO novamente para devolver para a controller
        val personVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }

    fun createV2(person: PersonVOV2): PersonVOV2 {
        logger.info("Create one person with name ${person.firstName}!")
        //Converto para uma entidade para persistir no banco
        var entity: Person =  mapper.mapVOToEntiry(person)

        //Converto para VO novamente para devolver para a controller
        return mapper.mapEntityToVO(repository.save(entity))
        //Essas conversões, são basicamente o que o Dozer faz
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Updating one person with name ${person.key}!")
        val entity = repository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        val personVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with name ${id}!")

        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        repository.delete(entity)
    }

}