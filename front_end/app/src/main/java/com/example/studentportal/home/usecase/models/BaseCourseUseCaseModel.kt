package com.example.studentportal.home.usecase.models

import com.example.studentportal.common.ui.model.BaseUiModel
import com.example.studentportal.common.usecase.BaseUseCaseModel
import com.example.studentportal.home.ui.model.BaseCourseUiModel
import com.example.studentportal.home.usecase.models.BaseCourseUseCaseModel.Type.Companion.TYPE_KEY
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import java.util.Date

@JsonClass(generateAdapter = true)
sealed class BaseCourseUseCaseModel(
    @Json(name = TYPE_KEY) val type: Type
) : BaseUseCaseModel<BaseUiModel> {
    abstract fun toBaseCourseUiModel(): BaseCourseUiModel
    override fun toUiModel(): BaseCourseUiModel {
        return toBaseCourseUiModel()
    }

    data class CourseUseCaseModel(
        val id: String,
        val instructor: String?,
        val enrolledStudents: Set<String>,
        val assignments: Set<String>,
        val semester: String,
        val published: Boolean,
        val name: String,
        val description: String
    ) : BaseCourseUseCaseModel(Type.Course) {
        override fun toBaseCourseUiModel(): BaseCourseUiModel {
            return BaseCourseUiModel.CourseUiModel(
                id = id,
                instructor = instructor,
                enrolledStudents = enrolledStudents,
                assignments = assignments,
                semester = semester,
                isPublished = published,
                name = name,
                description = description
            )
        }
    }

    data class FacultyUseCaseModel(
        val id: String,
        val password: String,
        val firstName: String,
        val lastName: String
    ) : BaseCourseUseCaseModel(Type.Faculty) {
        override fun toBaseCourseUiModel(): BaseCourseUiModel {
            return BaseCourseUiModel.FacultyUiModel(
                id = id,
                password = password,
                firstName = firstName,
                lastName = lastName
            )
        }
    }

    data class SemesterUseCaseModel(
        val id: String,
        val startDate: Date,
        val endDate: Date,
        val name: String
    ) : BaseCourseUseCaseModel(Type.Semester) {
        override fun toBaseCourseUiModel(): BaseCourseUiModel {
            return BaseCourseUiModel.SemesterUiModel(
                id = id,
                startDate = startDate,
                endDate = endDate,
                name = name
            )
        }
    }

    enum class Type {
        Course,
        Semester,
        Faculty;

        companion object {
            const val TYPE_KEY = "type"

            fun moshiPolymorphicFactory(): PolymorphicJsonAdapterFactory<BaseCourseUseCaseModel> {
                return PolymorphicJsonAdapterFactory.of(
                    BaseCourseUseCaseModel::class.java,
                    Type.TYPE_KEY
                ).withSubtype(CourseUseCaseModel::class.java, Course.name)
                    .withSubtype(FacultyUseCaseModel::class.java, Faculty.name)
                    .withSubtype(SemesterUseCaseModel::class.java, Semester.name)
            }
        }
    }
}
