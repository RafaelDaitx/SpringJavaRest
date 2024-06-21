package api_rest_kotlin.integrationtest.vo.wrappers

import api_rest_kotlin.integrationtest.vo.PersonVO
import com.fasterxml.jackson.annotation.JsonProperty

class PersonEmbeddedV0 {

    @JsonProperty("_embedded")
    var persons: List<PersonVO?>? = null
}