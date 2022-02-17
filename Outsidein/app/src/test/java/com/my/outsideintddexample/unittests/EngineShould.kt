package com.my.outsideintddexample.unittests

import com.example.outsideintddexample.acceptancetests.MainCoroutineScopeRule
import com.my.outsideintddexample.Engine
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class EngineShould {
    private val engine: Engine = Engine()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun turnOn() = runBlockingTest {
        engine.turnOn()
        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun riseTheTemperatureGraduallyWhenItTurnsOn() = runBlockingTest {
        val flow = engine.turnOn()
        val actual  = flow.toList()

        assertEquals(listOf(25, 50, 95), actual)
    }
}