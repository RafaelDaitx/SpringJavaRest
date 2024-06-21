package api_rest_kotlin.integrationtest.vo.wrappers

import com.fasterxml.jackson.annotation.JsonProperty

class WrapperPersonV0 {

    @JsonProperty("_embedded")
    var embedded: PersonEmbeddedV0? = null
}