package api_rest_kotlin.mapper.custom

import api_rest_kotlin.data.vo.v2.PersonVO
import api_rest_kotlin.model.Person
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PersonMapper {

    fun mapEntityToVO(person: Person): PersonVO {
        val vo = PersonVO()

        vo.id = person.id
        vo.firstName = person.firstName
        vo.lastName = person.lastName
        vo.address = person.address
        vo.gender = person.gender
        vo.birthDay = Date()

        return vo
    }

    fun mapVOToEntiry(person: PersonVO ): Person {
        val entity = Person()

        entity.id = person.id
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        //entity.birthDay = person.birthDay -> caso tivesse no banco, mas aqui sao versoes
        //diferentes

        return entity
    }
}