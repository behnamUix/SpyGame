package com.behnamuix.spy.di

import androidx.room.Room
import com.behnamuix.spy.viewModel.ConfigGameViewModel
import com.behnamuix.retrofittest.SpyGame.viewModel.GameViewModel
import com.behnamuix.spy.viewModel.RoleManagerViewModel
import com.behnamuix.spy.data.local.db.config.SpyDatabase
import com.behnamuix.spy.data.local.db.repository.KeywordRepository
import com.behnamuix.spy.data.local.db.repository.KeywordRepositoryImpl
import com.behnamuix.spy.media.config.getMediaPlayer
import com.behnamuix.spy.media.repo.MediaPlayerRepository
import com.behnamuix.spy.media.viewmodel.MediaPlayerViewModel
import com.behnamuix.spy.viewModel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            SpyDatabase::class.java,
            "spy_database"
        ).build()
    }
}
val daoModule = module {
    single {
        get<SpyDatabase>().keyWordDao()
    }
}
val mediaModule = module {
    single { getMediaPlayer() }
}
val repositoryModule = module {
    single<KeywordRepository> { KeywordRepositoryImpl(get()) }
    single { MediaPlayerRepository(get()) }
}

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { ConfigGameViewModel(get()) }
    viewModel { RoleManagerViewModel(get(), get()) }
    viewModel { GameViewModel(get()) }
    viewModel { MediaPlayerViewModel(get()) }
}
