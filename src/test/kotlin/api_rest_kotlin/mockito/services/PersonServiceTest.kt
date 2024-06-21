//package api_rest_kotlin.mockito.services
//
//import api_rest_kotlin.repository.PersonRepository
//import api_rest_kotlin.services.PersonService
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//
//import org.junit.jupiter.api.Assertions.assertTrue
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertNotNull
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//import org.mockito.junit.jupiter.MockitoExtension
//import api_rest_kotlin.mocks.MockPerson
//import java.util.*
//
//@ExtendWith(MockitoExtension::class)
//internal class PersonServiceTest {
//
//    private lateinit var inputObject: MockPerson
//
//    @InjectMocks //Injetar os mocks nele
//    private lateinit var service: PersonService
//
//    @Mock
//    private lateinit var repository: PersonRepository
//
//    @BeforeEach
//    fun setUpMock() {
//        inputObject = MockPerson()
//        MockitoAnnotations.openMocks(this)
//    }
//
//    @Test
//    fun findAll() {
//        val list = inputObject.mockEntityList()
//        `when`(repository.findAll()).thenReturn(list)
//
//        val persons = service.findAll()
//
//        assertNotNull(persons)
//        assertEquals(14, persons.size)
//
//        val personOne = persons[1]
//
//        assertNotNull(personOne)
//        assertNotNull(personOne.key)
//        assertNotNull(personOne.links)
//        assertTrue(personOne.links.toString().contains("/api/person/v1/1"))
//        assertEquals("Address Test1", personOne.address)
//        assertEquals("First Name Test1", personOne.firstName)
//        assertEquals("Last Name Test1", personOne.lastName)
//        assertEquals("Female", personOne.gender)
//
//        val personFour = persons[4]
//
//        assertNotNull(personFour)
//        assertNotNull(personFour.key)
//        assertNotNull(personFour.links)
//        assertTrue(personFour.links.toString().contains("/api/person/v1/4"))
//        assertEquals("Address Test4", personFour.address)
//        assertEquals("First Name Test4", personFour.firstName)
//        assertEquals("Last Name Test4", personFour.lastName)
//        assertEquals("Male", personFour.gender)
//
//    }
//
//    @Test
//    fun findById() {
//        //Executa a chamada no service, e la quando chama o repository,
//        //MockitoAnnotations.openMocks(this) garante que o acesso ao repository
//        //seja feeito pelo mockito, e não direto no banco de dados real
//        val person = inputObject.mockEntity(1)
//        person.id = 1L
//        `when`(repository.findById(1)).thenReturn(Optional.of(person))
//
//        val result = service.findById(1)
//
//        assertNotNull(result)
//        assertNotNull(result.key)
//        assertNotNull(result.links)
//        assertTrue(result.links.toString().contains("/api/person/v1/1"))
//        assertEquals("Address Test1", result.address)
//        assertEquals("First Name Test1", result.firstName)
//        assertEquals("Last Name Test1", result.lastName)
//        assertEquals("Female", result.gender)
//    }
//
//    @Test
//    fun create() {
//        val entity = inputObject.mockEntity(1)
//
//        val persisted = entity.copy()
//        persisted.id = 1
//
//
//        `when`(repository.save(entity)).thenReturn(persisted)
//
//        val vo = inputObject.mockVO(1)
//        val result = service.create(vo)
//
//        assertNotNull(result)
//        assertNotNull(result.key)
//        assertNotNull(result.links)
//        assertTrue(result.links.toString().contains("/api/person/v1/1"))
//        assertEquals("Address Test1", result.address)
//        assertEquals("First Name Test1", result.firstName)
//        assertEquals("Last Name Test1", result.lastName)
//        assertEquals("Female", result.gender)
//    }
//
//    @Test
//    fun update() {
//        val entity = inputObject.mockEntity(1)
//
//        val persisted = entity.copy()
//        persisted.id = 1
//
//        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
//        `when`(repository.save(entity)).thenReturn(persisted)
//
//        val vo = inputObject.mockVO(1)
//        val result = service.update(vo)
//
//        assertNotNull(result)
//        assertNotNull(result.key)
//        assertNotNull(result.links)
//        assertTrue(result.links.toString().contains("/api/person/v1/1"))
//        assertEquals("Address Test1", result.address)
//        assertEquals("First Name Test1", result.firstName)
//        assertEquals("Last Name Test1", result.lastName)
//        assertEquals("Female", result.gender)
//    }
//
//    @Test
//    fun delete() {
//        val entity = inputObject.mockEntity(1)
//
//        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
//        service.delete(1
//        )
//    }
//}