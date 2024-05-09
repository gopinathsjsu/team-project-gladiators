package com.example.studentportal.profile.service.repository

import com.example.studentportal.auth.usecase.model.AuthRequest
import com.example.studentportal.auth.usecase.model.AuthResponseUseCaseModel
import com.example.studentportal.common.service.Repository
import com.example.studentportal.common.service.ServiceProvider
import com.example.studentportal.common.service.serviceModule
import com.example.studentportal.profile.service.UserService
import com.example.studentportal.profile.usecase.model.UserUseCaseModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Response

class UserRepository(
    override val provider: ServiceProvider<UserService>
) : Repository<UserService> {

    suspend fun fetchUser(userId: String): Response<UserUseCaseModel> {
        return provider.service().getUser(userId).execute()
    }

    suspend fun login(authRequest: AuthRequest): Response<AuthResponseUseCaseModel> {
        return provider.service().login(authRequest).execute()
    }

    suspend fun updateUser(userUseCaseModel: UserUseCaseModel): Response<UserUseCaseModel> {
        return provider.service().updateUser(userUseCaseModel).execute()
    }

    companion object {
        fun koinModule(): Module {
            return module {
                includes(serviceModule)
                single {
                    UserRepository(
                        provider = UserServiceProvider()
                    )
                }
            }
        }
    }
}
