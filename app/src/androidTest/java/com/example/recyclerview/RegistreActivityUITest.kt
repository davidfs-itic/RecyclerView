package com.example.recyclerview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/*
Elements clau d'Espresso en els tests
onView(withId(...)) → Localitza un element de la UI.
perform(typeText(...)) → Escriu text en un EditText.
perform(click()) → Fa clic en un Button.
check(matches(...)) → Verifica que l'element compleix una condició.
hasErrorText("Missatge d'error") → Comprova si un EditText té un error assignat.
isDisplayed() → Comprova si un element és visible a la pantalla.

 */

@RunWith(AndroidJUnit4::class)
class RegistreActivityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(ActivityRegistre::class.java)

    @Test
    fun testNomUsuariBuit() {

        onView(withId(R.id.reg_nomusuari)).perform(clearText())
        onView(withId(R.id.reg_btnregistre)).perform(click())
        onView(withId(R.id.reg_nomusuari))
            .check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }

}
