package com.behnamuix.spy.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.room.Room
import com.behnamuix.spy.authentication.config.getUser
import com.behnamuix.spy.authentication.config.getGoogleIdOption
import com.behnamuix.spy.authentication.repository.AuthRepository
import com.behnamuix.spy.authentication.viewModel.AuthViewModel
import com.behnamuix.spy.viewModel.GameViewModel
import com.behnamuix.spy.data.local.db.config.SpyDatabase
import com.behnamuix.spy.data.local.db.repository.keyword.KeywordRepository
import com.behnamuix.spy.data.local.db.repository.keyword.KeywordRepositoryImpl
import com.behnamuix.spy.data.local.ds.config.dataStore
import com.behnamuix.spy.data.local.ds.repository.DataStoreRepository
import com.behnamuix.spy.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spy.media.config.getMediaPlayer
import com.behnamuix.spy.media.repo.MediaPlayerRepository
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.viewModel.ConfigGameViewModel
import com.behnamuix.spy.viewModel.RoleManagerViewModel
import com.behnamuix.spy.viewModel.SplashViewModel
import com.behnamuix.spy.viewModel.TrainingViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            SpyDatabase::class.java,
            "spy_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}
val daoModule = module {
    single { get<SpyDatabase>().keyWordDao() }


}
val mediaModule = module {
    single { getMediaPlayer() }
}
val gioModule = module {
    single { getGoogleIdOption() }
}
val authModule = module {
    single { CredentialManager.create(get()) }

    single { FirebaseAuth.getInstance() }
}

val dataStoreModule = module {
    single { get<Context>().dataStore }
}
val repositoryModule = module {
    single<KeywordRepository> { KeywordRepositoryImpl(get()) }
    single { MediaPlayerRepository(get()) }
    single { DataStoreRepository(get()) }
    single { AuthRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel {
        ConfigGameViewModel(
            get(),
            get()
        )
    }
    viewModel { SplashViewModel() }
    viewModel { RoleManagerViewModel(get()) }
    viewModel { GameViewModel(get()) }
    viewModel { MediaPlayerViewModel(get()) }
    viewModel { TrainingViewModel(get()) }
    viewModel { DataStoreViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
}
