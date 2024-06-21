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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonVO>

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("Finding all people!")

        val persons = repository.findAll(pageable)

        //val vos = DozerMapper.parseListObject(persons, PersonVO::class.java)
        val vos = persons.map { p -> DozerMapper.parseObject(p, PersonVO::class.java) } //conmverte de identidade para PersonVO
        vos.map { p -> p.add(linkTo(PersonController::class.java).slash(p.key).withSelfRel()) }
        //Intera sobre todca a lista de Persons e adiciona o HateOS(para ficar com o url certo)

        return assembler.toModel(vos)
    }

    fun findPersonByName(firstName: String, pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("Finding person by name!")

        val persons = repository.FindPersonByName(firstName, pageable)

        //val vos = DozerMapper.parseListObject(persons, PersonVO::class.java)
        val vos = persons.map { p -> DozerMapper.parseObject(p, PersonVO::class.java) } //conmverte de identidade para PersonVO
        vos.map { p -> p.add(linkTo(PersonController::class.java).slash(p.key).withSelfRel()) }
        //Intera sobre todca a lista de Persons e adiciona o HateOS(para ficar com o url certo)

        return assembler.toModel(vos)
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

    @Transactional
    fun disablePerson(id: Long): PersonVO {
        //@Transactional garante que todo processo deva ser
        // executado com êxito, é “tudo ou nada” (princípio da atomicidade).
        //famoso Begin work;
        logger.info("Disabling one person with ID $id!")
        repository.DisablePerson(id) //Desabilito e busco o cara que foi desabilitado

        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val personVO: PersonVO =  DozerMapper.parseObject(person, PersonVO::class.java)
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