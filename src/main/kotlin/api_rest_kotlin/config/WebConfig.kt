package api_rest_kotlin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {


    @Value("\${cors:\n" +
            "  originPatterns:default}")
    private val corsOriginPatterns: String = ""

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        val allowedOrigins = corsOriginPatterns.split(",")
                            .toTypedArray()
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(*allowedOrigins)
            .allowCredentials(true)
    }
}