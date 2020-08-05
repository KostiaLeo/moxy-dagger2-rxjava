package com.example.viewstatemvp

import com.example.viewstatemvp.rxTask.RxTask
import org.junit.Test
import org.junit.After
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.mockito.ArgumentMatcher

class SampleMockitoTest {

    @Mock
    lateinit var mockSample: MockSample

    @Test
    fun customMatcher() {
        MockitoAnnotations.initMocks(this)
        val customMatcher = ArgumentMatcher<String> {
            it.contains("off", ignoreCase = true) || it.contains("Quavo")
        }

        println(argThat(customMatcher) == null)
        `when`(mockSample.isMigos(argThat(customMatcher))).thenReturn("Yeees sir")

        assertEquals("Yeees sir", mockSample.isMigos("Offset"))
        assertEquals("Yeees sir", mockSample.isMigos("Takeoff"))
    }
}
