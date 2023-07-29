package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.User
import dev.diego.cotta.system.stubs.EntitiesStubs.buildUser
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var user: User

    @BeforeEach
    fun setup() {
        user = testEntityManager.persist(buildUser(id = null, username = "userNameTeste"))
    }

    @Test
    fun `should find User by username`() {
        //given
        val username = "userNameTeste"

        //when
        val response: User = userRepository.findUserByUsername(username)!!
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(user).isSameAs(response)
    }

    @Test
    fun `should not find user by username`() {
        //given
        val couponCode = "error"

        //when
        val response: User? = userRepository.findUserByUsername(couponCode)
        //then
        Assertions.assertThat(response).isNull()
    }

}



