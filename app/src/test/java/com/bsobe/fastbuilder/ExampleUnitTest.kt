package com.bsobe.fastbuilder

import com.bsobe.fastbuilder.model.ItemBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val item = ItemBuilder()
            .name("name")
            .value(14L)
            .build()
    }
}
