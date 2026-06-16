package com.behnamuix.spygame.base

import android.app.Application
import com.behnamuix.spygame.di.clientModule
import com.behnamuix.spygame.di.daoModule
import com.behnamuix.spygame.di.dataStoreModule
import com.behnamuix.spygame.di.databaseModule
import com.behnamuix.spygame.di.mediaModule
import com.behnamuix.spygame.di.repositoryModule
import com.behnamuix.spygame.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    daoModule,
                    viewModelModule,
                    mediaModule,
                    dataStoreModule,
                    clientModule
                )
            )
        }
    }
}