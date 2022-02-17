package com.my.outsideintddexample.unittests

import com.example.outsideintddexample.acceptancetests.MainCoroutineScopeRule
import com.my.outsideintddexample.Car
import com.my.outsideintddexample.Engine
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CarShould {
    private val engine: Engine = mock()
    private val car: Car

    // under this test, all of its dependencies must be mocked or fake objects
    // Whenever make a mock object, we also need to mock the behavior of all of it's function
    // 테스트 대상은 Car 이므로 isolation 을 위해 연관된 dependencies 들을 모두 mocking 한다
    // * Car 에 연관된 engine 테스트는 여기서 하지 않는다
    // 연동 테스트는 acceptance test 에서 한다 -> kotest 라이브러리를 이용해보면 어떨까?
    init {
        runBlockingTest {
            whenever(engine.turnOn()).thenReturn(flow {
                delay(2000)
                emit(25)
                delay(2000)
                emit(50)
                delay(2000)
                emit(95)
            })
        } // dependency 설정 전에 behavior mock
        car = Car(engine, 5.0)
    }

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    /** Car should - looseFuelWhenTurnsOn */
    @Test
    fun looseFuelWhenTurnsOn() = runBlockingTest {
        car.turnOn()
        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnOnItsEngine() = runBlockingTest {
        car.turnOn()
        verify(engine, times(1)).turnOn()
    }
}