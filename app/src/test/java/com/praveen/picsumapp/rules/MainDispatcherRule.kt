package com.praveen.picsumapp.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Created by Praveen.Sharma on 04/03/25 - 11:47..
 *
 ***/
class MainDispatcherRule : TestWatcher() {
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher to test dispatcher
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description?) {
        Dispatchers.resetMain() // Reset the dispatcher after the test
    }
}