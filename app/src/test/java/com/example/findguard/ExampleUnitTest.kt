package com.example.findguard

import com.example.findguard.model.UserModel
import com.example.findguard.repository.UserRepo
import com.example.findguard.viewmodel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ExampleUnitTest {

    @Test
    fun login_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Login success")
            null
        }.`when`(repo).login(eq("test@gmail.com"), eq("123456"), any())

        var successResult = false
        var messageResult = ""

        viewModel.login("test@gmail.com", "123456") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Login success", messageResult)
        verify(repo).login(eq("test@gmail.com"), eq("123456"), any())
    }

    @Test
    fun login_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(false, "Invalid credentials")
            null
        }.`when`(repo).login(eq("wrong@gmail.com"), eq("wrongpass"), any())

        var successResult = true
        var messageResult = ""

        viewModel.login("wrong@gmail.com", "wrongpass") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Invalid credentials", messageResult)
        verify(repo).login(eq("wrong@gmail.com"), eq("wrongpass"), any())
    }

    @Test
    fun signup_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, String) -> Unit>(2)
            callback(true, "Signup success", "uid123")
            null
        }.`when`(repo).signup(eq("new@gmail.com"), eq("123456"), any())

        var successResult = false
        var messageResult = ""
        var userIdResult = ""

        viewModel.signup("new@gmail.com", "123456") { success, msg, userId ->
            successResult = success
            messageResult = msg
            userIdResult = userId
        }

        assertTrue(successResult)
        assertEquals("Signup success", messageResult)
        assertEquals("uid123", userIdResult)
        verify(repo).signup(eq("new@gmail.com"), eq("123456"), any())
    }

    @Test
    fun signup_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, String) -> Unit>(2)
            callback(false, "Email already in use", "")
            null
        }.`when`(repo).signup(eq("existing@gmail.com"), eq("123456"), any())

        var successResult = true
        var messageResult = ""

        viewModel.signup("existing@gmail.com", "123456") { success, msg, _ ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Email already in use", messageResult)
        verify(repo).signup(eq("existing@gmail.com"), eq("123456"), any())
    }

    @Test
    fun forgetPassword_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Reset link sent")
            null
        }.`when`(repo).forgetPassword(eq("test@gmail.com"), any())

        var successResult = false
        var messageResult = ""

        viewModel.forgetPassword("test@gmail.com") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Reset link sent", messageResult)
        verify(repo).forgetPassword(eq("test@gmail.com"), any())
    }

    @Test
    fun forgetPassword_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(false, "Email not registered")
            null
        }.`when`(repo).forgetPassword(eq("notfound@gmail.com"), any())

        var successResult = true
        var messageResult = ""

        viewModel.forgetPassword("notfound@gmail.com") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Email not registered", messageResult)
        verify(repo).forgetPassword(eq("notfound@gmail.com"), any())
    }

    @Test
    fun addUserToDatabase_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val userModel = UserModel("uid1", "Alice", "alice@gmail.com", "123456")

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "User saved")
            null
        }.`when`(repo).addUserToDatabase(eq("uid1"), eq(userModel), any())

        var successResult = false
        var messageResult = ""

        viewModel.addUserToDatabase("uid1", userModel) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("User saved", messageResult)
        verify(repo).addUserToDatabase(eq("uid1"), eq(userModel), any())
    }

    @Test
    fun addUserToDatabase_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val userModel = UserModel("uid2", "Bob", "bob@gmail.com", "123456")

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(false, "Database error")
            null
        }.`when`(repo).addUserToDatabase(eq("uid2"), eq(userModel), any())

        var successResult = true
        var messageResult = ""

        viewModel.addUserToDatabase("uid2", userModel) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Database error", messageResult)
        verify(repo).addUserToDatabase(eq("uid2"), eq(userModel), any())
    }
}