package xyz.dnieln7.portfolio.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import xyz.dnieln7.portfolio.data.remote.dto.AuthenticationDTO
import xyz.dnieln7.portfolio.data.remote.dto.ProjectDTO
import xyz.dnieln7.portfolio.data.remote.request.LoginRequest
import xyz.dnieln7.portfolio.data.remote.request.UpdateProjectRequest
import xyz.dnieln7.portfolio.data.remote.response.PortfolioResponse

interface PortfolioApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): AuthenticationDTO

    @GET("projects")
    suspend fun getProjects(): List<ProjectDTO>

    @PUT("projects/{id}")
    suspend fun updateProject(
        @Path("id") id: Int,
        @Body body: UpdateProjectRequest,
    ): PortfolioResponse

    @DELETE("projects/{id}")
    suspend fun deleteProject(@Path("id") id: Int): PortfolioResponse
}
