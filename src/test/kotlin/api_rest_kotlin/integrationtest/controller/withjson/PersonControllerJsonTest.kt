package api_rest_kotlin.integrationtest.controller.withjson

import api_rest_kotlin.integrationtest.TestConfigs
import api_rest_kotlin.integrationtest.testcontainers.AbstractIntegrationTest
import api_rest_kotlin.integrationtest.vo.AccountCredentialsVO
import api_rest_kotlin.integrationtest.vo.PersonVO
import api_rest_kotlin.integrationtest.vo.TokenVO
import api_rest_kotlin.integrationtest.vo.wrappers.WrapperPersonV0
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerJsonTest : AbstractIntegrationTest() {

    private lateinit var objectMapper: ObjectMapper
    private lateinit var specification: RequestSpecification
    //armazenar o token
    private lateinit var person: PersonVO


    @BeforeAll
    fun setupTests(){
        objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        person = PersonVO()
    }

    @Test
    @Order(0)
    fun loginTest() {
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        val token = RestAssured.given()
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
            .accessToken

        specification = RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer $token")
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()
    }

    @Test
    @Order(1)
    fun testCreate(){
        mockPerson()

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(person)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertTrue(item.id > 0)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)
        assertEquals("Rafael", item.firstName)
        assertEquals("Daitx", item.lastName)
        assertEquals("Rua do teste", item.address)
        assertEquals("Male", item.gender)
        assertEquals(true, item.enabled)

    }

    @Test
    @Order(2)
    fun testUpdate(){
        person.lastName = "Rodgers"

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(person)
            .`when`()
            .put()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertTrue(item.id > 0)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)
        assertEquals("Rafael", item.firstName)
        assertEquals("Rodgers", item.lastName)
        assertEquals("Rua do teste", item.address)
        assertEquals("Male", item.gender)
        assertEquals(true, item.enabled)

    }

    @Test
    @Order(3)
    fun disablePerson(){

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", person.id)
            .`when`()
            .patch("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertTrue(item.id > 0)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)
        assertEquals("Rafael", item.firstName)
        assertEquals("Rodgers", item.lastName)
        assertEquals("Rua do teste", item.address)
        assertEquals("Male", item.gender)
        assertEquals(false, item.enabled)
    }

    @Test
    @Order(4)
    fun testFindById(){

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", person.id)
            .`when`()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        person = item

        assertNotNull(item.id)
        assertTrue(item.id > 0)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)
        assertEquals("Rafael", item.firstName)
        assertEquals("Rodgers", item.lastName)
        assertEquals("Rua do teste", item.address)
        assertEquals("Male", item.gender)

    }
    @Test
    @Order(5)
    fun testDelete(){
             given()
            .spec(specification)
            .pathParam("id", person.id)
            .`when`()
            .delete("{id}")
            .then()
            .statusCode(204)
    }

    @Test
    @Order(6)
    fun testFindAll(){

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val wrapper = objectMapper.readValue(content, WrapperPersonV0::class.java)
        val people = wrapper.embedded!!.persons
        val item = people?.get(0)

        assertNotNull(item!!.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)
        assertEquals("Ayrton", item.firstName)
        assertEquals("Senna", item.lastName)
        assertEquals("São Paulo", item.address)
        assertEquals("Male", item.gender)

    }

    private fun mockPerson() {
        person.firstName = "Rafael"
        person.lastName = "Daitx"
        person.address = "Rua do teste"
        person.gender = "Male"
        person.enabled = true
    }

}