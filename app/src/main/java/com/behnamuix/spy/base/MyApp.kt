package com.behnamuix.spy.base

import android.app.Application
import com.behnamuix.spy.di.daoModule
import com.behnamuix.spy.di.databaseModule
import com.behnamuix.spy.di.mediaModule
import com.behnamuix.spy.di.repositoryModule
import com.behnamuix.spy.di.viewModelModule
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
                    mediaModule
                )
            )
        }
    }
}