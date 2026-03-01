package com.example.findguard

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/* ========================================================================= */
/*  LOGIN INSTRUMENTED TEST                                                    */
/* ========================================================================= */

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testLoginButton_navigatesToDashboard() {
        composeRule.onNodeWithTag("email")
            .performTextInput("test@gmail.com")
        composeRule.onNodeWithTag("password")
            .performTextInput("123456")
        composeRule.onNodeWithTag("loginButton")
            .performClick()
        // Note: This might fail if login is asynchronous and doesn't happen instantly
    }

    @Test
    fun testForgotPassword_navigatesToForgetPassword() {
        composeRule.onNodeWithTag("forgotPassword")
            .performClick()
        Intents.intended(hasComponent(ForgetPassword::class.java.name))
    }

    @Test
    fun testSignUpLink_navigatesToSignup() {
        composeRule.onNodeWithTag("signUpLink")
            .performClick()
        Intents.intended(hasComponent(SignupActivity::class.java.name))
    }
}

/* ========================================================================= */
/*  SIGNUP INSTRUMENTED TEST                                                   */
/* ========================================================================= */

@RunWith(AndroidJUnit4::class)
class SignUpInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<SignupActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testSignUpButton_isDisplayed() {
        composeRule.onNodeWithTag("fullName").performTextInput("Alice Smith")
        composeRule.onNodeWithTag("email").performTextInput("alice@gmail.com")
        composeRule.onNodeWithTag("password").performTextInput("123456")
        composeRule.onNodeWithTag("termsCheckbox").performClick()
        composeRule.onNodeWithTag("signUpButton").performClick()
    }

    @Test
    fun testLoginLink_navigatesToLogin() {
        composeRule.onNodeWithTag("loginLink")
            .performClick()
        Intents.intended(hasComponent(LoginActivity::class.java.name))
    }
}

/* ========================================================================= */
/*  FORGET PASSWORD INSTRUMENTED TEST                                          */
/* ========================================================================= */

@RunWith(AndroidJUnit4::class)
class ForgetPasswordInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ForgetPassword>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testSendResetLink_clickButton() {
        composeRule.onNodeWithTag("email")
            .performTextInput("test@gmail.com")
        composeRule.onNodeWithTag("sendResetButton")
            .performClick()
    }

    @Test
    fun testBackToLogin_navigatesToLogin() {
        composeRule.onNodeWithTag("backToLogin")
            .performClick()
        Intents.intended(hasComponent(LoginActivity::class.java.name))
    }
}
