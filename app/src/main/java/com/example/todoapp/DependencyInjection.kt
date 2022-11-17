package com.example.todoapp

import android.app.Application
import androidx.room.Room
import com.example.todoapp.data.LoginDataSource
import com.example.todoapp.data.local.UserDataBase
import com.example.todoapp.data.model.repository.LoginRepository
import com.example.todoapp.data.model.repository.TodoRepository
import com.example.todoapp.viewmodel.LoginViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DependencyInjection {

    @Provides
    @Singleton
    fun provideLoginRepository(loginDataSource: LoginDataSource): LoginRepository {
        return LoginRepository(loginDataSource)
    }

    @Provides
    @Singleton
    fun provideTodoRepo(dao: UserDataBase): TodoRepository {
        return TodoRepository(dao.getDao())
    }

    @Provides
    @Singleton
    fun provideLoginDataSource(dao: UserDataBase): LoginDataSource {
        return LoginDataSource(dao.getDao())
    }

    @Singleton
    @Provides
    fun provideViewModel(repo: LoginRepository, todo: TodoRepository): LoginViewModelFactory {
        return LoginViewModelFactory(repo, todo)
    }

    @Provides
    @Singleton
    fun provideDataBase(app: Application) = Room.databaseBuilder(
        app,
        UserDataBase::class.java,
        UserDataBase.USER_TABLE_NAME
    ).build()
}
