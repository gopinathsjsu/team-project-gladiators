package com.example.studentportal.home.service.models

import com.example.studentportal.home.usecase.models.StudentUseCaseModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StudentServiceModelTest {

    @Test
    fun `test StudentServiceModel`() {
        assertThat(
            StudentServiceModel(
                id = "ID",
                name = "Name",
                email = "Email"
            ).toUseCaseModel()
        ).isEqualTo(
            StudentUseCaseModel(
                id = "ID",
                name = "Name",
                email = "Email"
            )
        )
    }
}
