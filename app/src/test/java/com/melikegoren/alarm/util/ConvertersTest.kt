package com.melikegoren.alarm.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun fromString_correctInput_returnsList() {
        // Arrange
        val jsonString = """["item1", "item2", "item3"]"""

        // Act
        val result = converters.fromString(jsonString)

        // Assert
        assertEquals(listOf("item1", "item2", "item3"), result)
    }

    @Test
    fun fromList_correctInput_returnsString() {
        // Arrange
        val inputList = listOf("item1", "item2", "item3")

        // Act
        val result = converters.fromList(inputList)

        // Assert
        val expectedJsonString = """["item1","item2","item3"]"""
        assertEquals(expectedJsonString, result)
    }
}
