package api_rest_kotlin.integrationtest.controller.withjson

import api_rest_kotlin.integrationtest.TestConfigs
import api_rest_kotlin.integrationtest.testcontainers.AbstractIntegrationTest
import api_rest_kotlin.integrationtest.vo.AccountCredentialsVO
import api_rest_kotlin.integrationtest.vo.TokenVO
import io.restassured.RestAssured
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerJsonTest  : AbstractIntegrationTest() {

    //armazenar o token
    private lateinit var tokenVO: TokenVO


    @BeforeAll
    fun setupTests(){
        tokenVO = TokenVO()
    }

    @Test
    @Order(0)
    fun loginTest() {
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        tokenVO = RestAssured.given()
            .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
            .`when`()
                .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }
    @Test
    @Order(1)
    fun refresthTest() {

        tokenVO = RestAssured.given()
            .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", tokenVO.username)
            .header(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer ${tokenVO.refreshToken}" )
            .`when`()
                .put("{username}")
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }


}