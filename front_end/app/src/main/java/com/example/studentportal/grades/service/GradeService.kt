
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GradeService {
    @GET("/grades/assignment/{assignmentId}")
    fun fetchGradesByAssignment(
        @Path("assignmentId") assignmentId: String,
        @Query("userId") userId: String
    ): Call<List<GradeUseCaseModel>>

    @PUT("/grades/update")
    fun updateGrade(
        @Body gradeWithStudentName: GradeUseCaseModel
    ): Call<GradeUseCaseModel>
}
