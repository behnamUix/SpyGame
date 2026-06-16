package com.behnamuix.spygame.di

import android.content.Context
import androidx.room.Room
import com.behnamuix.spygame.data.local.db.config.SpyDatabase
import com.behnamuix.spygame.data.local.db.repository.keyword.KeywordRepository
import com.behnamuix.spygame.data.local.db.repository.keyword.KeywordRepositoryImpl
import com.behnamuix.spygame.data.local.ds.config.dataStore
import com.behnamuix.spygame.data.local.ds.repository.DataStoreRepository
import com.behnamuix.spygame.data.local.ds.viewModel.DataStoreViewModel
import com.behnamuix.spygame.data.remote.authentication.repository.ApiRepository
import com.behnamuix.spygame.data.remote.authentication.viewModel.ApiViewModel
import com.behnamuix.spygame.media.config.getMediaPlayer
import com.behnamuix.spygame.media.repo.MediaPlayerRepository
import com.behnamuix.spygame.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spygame.viewModel.ConfigGameViewModel
import com.behnamuix.spygame.viewModel.GameViewModel
import com.behnamuix.spygame.viewModel.RoleManagerViewModel
import com.behnamuix.spygame.viewModel.SplashViewModel
import com.behnamuix.spygame.viewModel.TrainingViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val clientModule=module {
    single {
        HttpClient(CIO) {

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }



        }
    }
}
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


val dataStoreModule = module {
    single { get<Context>().dataStore }
}
val repositoryModule = module {
    single<KeywordRepository> { KeywordRepositoryImpl(get()) }
    single { MediaPlayerRepository(get()) }
    single { DataStoreRepository(get()) }
    single { ApiRepository(get()) }

}

val viewModelModule = module {
    viewModel { ConfigGameViewModel(get(), get()) }
    viewModel { SplashViewModel() }
    viewModel { RoleManagerViewModel(get()) }
    viewModel { GameViewModel(get()) }
    viewModel { MediaPlayerViewModel(get()) }
    viewModel { TrainingViewModel(get()) }
    viewModel { DataStoreViewModel(get()) }
    viewModel { ApiViewModel(get(),get()) }

}

