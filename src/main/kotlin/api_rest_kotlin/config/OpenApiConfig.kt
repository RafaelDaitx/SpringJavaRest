package api_rest_kotlin.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI{
        return OpenAPI()
            .info(
                Info()
                    .title("RestFull API with Kotlin")
                    .version("v1")
                    .description("Minha api rodando bunitao")
                    .termsOfService("https://github.com/RafaelDaitx/")
                    .license(
                        License().name("Apache 2.0")
                            .url("https://github.com/RafaelDaitx/")
                    )
            )
    }
}