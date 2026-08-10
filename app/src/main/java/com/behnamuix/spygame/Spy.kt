package com.behnamuix.spygame

import android.app.Application
import com.behnamuix.spygame.core.di.clientModule
import com.behnamuix.spygame.core.di.configGameUseCaseModule
import com.behnamuix.spygame.core.di.daoModule
import com.behnamuix.spygame.core.di.dataSourceModule
import com.behnamuix.spygame.core.di.dataStoreModule
import com.behnamuix.spygame.core.di.databaseModule
import com.behnamuix.spygame.core.di.mediaModule
import com.behnamuix.spygame.core.di.repositoryModule
import com.behnamuix.spygame.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    configGameUseCaseModule,
                    databaseModule,
                    repositoryModule,
                    daoModule,
                    viewModelModule,
                    mediaModule,
                    dataStoreModule,
                    clientModule,
                    dataSourceModule
                )
            )
        }
    }
}