package api_rest_kotlin.data.vo.v1

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel

//@JsonPropertyOrder("id", "address", "firstName", "lastName", "gender") -> Order of JSON
data class PersonVO (

    @Mapping("id")
    var key: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = ""
): RepresentationModel<PersonVO>()