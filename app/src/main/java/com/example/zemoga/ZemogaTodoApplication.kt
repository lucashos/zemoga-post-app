package com.example.zemoga

import android.app.Application
import com.example.zemoga.core.coreModule
import com.example.zemoga.domain.domainModule
import com.example.zemoga.feature.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ZemogaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ZemogaApplication)
            modules(coreModule, domainModule, featureModule)
        }
    }
}