package com.my.outsideintddexample.acceptancetests

import com.example.outsideintddexample.acceptancetests.MainCoroutineScopeRule
import com.my.outsideintddexample.Car
import com.my.outsideintddexample.Engine
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

/**
 * Acceptance test 는 androidTest bucket 에 속해야한다.
 * 지금은 테스트 목적으로 test bucket 에 존재
 */
class CarFeature {

    // real object, not mocking in acceptance tests
    private val engine = Engine()
    private val car = Car(engine, 6.0)

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() = runBlockingTest {
        car.turnOn()
        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnItsEngineAndIncreasesTheTemperatureGradually() = runBlockingTest {
        car.turnOn()
        coroutinesTestRule.advanceTimeBy(2000L)
        assertEquals(25, car.engine.temperature)
        coroutinesTestRule.advanceTimeBy(2000L)
        assertEquals(50, car.engine.temperature)
        coroutinesTestRule.advanceTimeBy(2000L)
        assertEquals(95, car.engine.temperature)
        assertTrue(car.engine.isTurnedOn)
    }
}