package com.behnamuix.spygame

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.behnamuix.spygame.ui.navigation.screens.otp.SquareOtpInputComp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SquareOtpInputCompTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun otpInput_AllDigitsEntered_CallbackCalled() {
        var resultOtp = ""

        composeTestRule.setContent {
            SquareOtpInputComp(
                otpLength = 4,
                onOtpComplete = { otp -> resultOtp = otp }
            )
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("1")
        composeTestRule.onNodeWithTag("otp_1").performTextInput("2")
        composeTestRule.onNodeWithTag("otp_2").performTextInput("3")
        composeTestRule.onNodeWithTag("otp_3").performTextInput("4")

        composeTestRule.waitForIdle()

        assert(resultOtp == "1234")
    }

    @Test
    fun otpInput_SingleDigit_DisplayedCorrectly() {
        composeTestRule.setContent {
            SquareOtpInputComp(otpLength = 4)
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("7")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("otp_0").assert(hasText("7"))
    }

    @Test
    fun otpInput_Letter_NotAccepted() {
        composeTestRule.setContent {
            SquareOtpInputComp(otpLength = 4)
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("A")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("otp_0").assert(hasText(""))
    }

    @Test
    fun otpInput_MultipleChars_OnlyFirstAccepted() {
        composeTestRule.setContent {
            SquareOtpInputComp(otpLength = 4)
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("56")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("otp_0").assert(hasText("5"))
    }

    @Test
    fun otpInput_TwoFieldsFilled_ThirdEmpty() {
        composeTestRule.setContent {
            SquareOtpInputComp(otpLength = 4)
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("1")
        composeTestRule.onNodeWithTag("otp_1").performTextInput("2")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("otp_0").assert(hasText("1"))
        composeTestRule.onNodeWithTag("otp_1").assert(hasText("2"))
        composeTestRule.onNodeWithTag("otp_2").assert(hasText(""))
    }

    @Test
    fun otpInput_NotAllFields_CallbackNotCalled() {
        var callbackCalled = false

        composeTestRule.setContent {
            SquareOtpInputComp(
                otpLength = 4,
                onOtpComplete = { callbackCalled = true }
            )
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("1")
        composeTestRule.onNodeWithTag("otp_1").performTextInput("2")
        composeTestRule.onNodeWithTag("otp_2").performTextInput("3")
        composeTestRule.waitForIdle()

        assert(!callbackCalled)
    }

    @Test
    fun otpInput_SpecialChar_NotAccepted() {
        composeTestRule.setContent {
            SquareOtpInputComp(otpLength = 4)
        }

        composeTestRule.onNodeWithTag("otp_0").performTextInput("@")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("otp_0").assert(hasText(""))
    }

    @Test
    fun otpInput_EmptyField_ShowsEmptyText() {
        composeTestRule.setContent {
            SquareOtpInputComp(otpLength = 4)
        }

        composeTestRule.onNodeWithTag("otp_0").assert(hasText(""))
        composeTestRule.onNodeWithTag("otp_1").assert(hasText(""))
        composeTestRule.onNodeWithTag("otp_2").assert(hasText(""))
        composeTestRule.onNodeWithTag("otp_3").assert(hasText(""))
    }
}