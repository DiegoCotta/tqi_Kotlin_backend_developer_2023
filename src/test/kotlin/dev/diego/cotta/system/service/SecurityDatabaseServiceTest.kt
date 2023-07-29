package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.User
import dev.diego.cotta.system.repository.UserRepository
import dev.diego.cotta.system.stubs.Stubs.buildUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.userdetails.UsernameNotFoundException

@ExtendWith(MockKExtension::class)
class SecurityDatabaseServiceTest {


    @MockK
    lateinit var userRepository: UserRepository

    @InjectMockKs
    lateinit var securityDatabaseService: SecurityDatabaseService

    @Test
    fun `SecurityDatabaseService loadUserByUsername should return User`() {
        //given
        val fakeUser: User = buildUser()
        val expectedResult = org.springframework.security.core.userdetails.User(
            fakeUser.username, fakeUser.password, hashSetOf()
        )
        every { userRepository.findUserByUsername(any()) } returns fakeUser
        //when
        val response = securityDatabaseService.loadUserByUsername("username")
        //then

        Assertions.assertThat(response).isEqualTo(expectedResult)
        verify { userRepository.findUserByUsername("username") }
    }

    @Test
    fun `SecurityDatabaseService loadUserByUsername should return Error`() {
        //given
        every { userRepository.findUserByUsername(any()) } returns null
        //when
        Assertions.assertThatExceptionOfType(UsernameNotFoundException::class.java)
            .isThrownBy { securityDatabaseService.loadUserByUsername("username") }
            .withMessage("username")
        verify { userRepository.findUserByUsername("username") }
        //then
    }

}
