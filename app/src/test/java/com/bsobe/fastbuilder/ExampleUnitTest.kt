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
    fun `given itemName is equals`() {
        // Given
        val givenName = "givenName"

        // When
        val item = ItemBuilder()
            .name(givenName)
            .value(14L)
            .build()

        // Then
        assertEquals(item.name, givenName)
    }
}
