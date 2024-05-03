package com.example.studentportal.students.ui.model

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin

class StudentTypeTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `StudentType enum contains correct values`() {
        // Extract all enum values into a list
        val types = StudentType.entries.map { it.name }

        // Assert that all expected enum values are present and correct
        assertThat(types).containsExactly("UNDERGRADUATE", "GRADUATE", "PHD").inOrder()
    }

    @Test
    fun `StudentType enum values are in correct order`() {
        // Assert that the ordinal values are as expected
        assertThat(StudentType.UNDERGRADUATE.ordinal).isEqualTo(0)
        assertThat(StudentType.GRADUATE.ordinal).isEqualTo(1)
        assertThat(StudentType.PHD.ordinal).isEqualTo(2)
    }
}
