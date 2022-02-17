package com.my.outsideintddexample

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Car(
    val engine: Engine,
    var fuel: Double
) {
    fun turnOn() {
        if (fuel >= 0.5) {
            fuel -= 0.5
        }
        CoroutineScope(Dispatchers.Main).launch {
            engine.turnOn()
                .collect { temperature ->
                    Log.d("COURSE", "collected engine temperature: $temperature")
                }
        }
    }
}