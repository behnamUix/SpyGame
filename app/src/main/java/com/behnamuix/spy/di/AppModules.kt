package com.behnamuix.spy.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.room.Room
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
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

val dataStoreModule = module {
    single { get<Context>().dataStore }
}
val repositoryModule = module {
    single<KeywordRepository> { KeywordRepositoryImpl(get()) }
    single { MediaPlayerRepository(get()) }
    single { DataStoreRepository(get()) }
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
}
