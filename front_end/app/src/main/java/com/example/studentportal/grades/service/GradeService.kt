import com.example.studentportal.grades.usecase.model.GradeListUseCaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GradeService {
    @GET("/grades/{assignmentId}")
    fun fetchGradesByAssignment(@Path("assignmentId") assignmentId: String): Call<GradeListUseCaseModel>
}