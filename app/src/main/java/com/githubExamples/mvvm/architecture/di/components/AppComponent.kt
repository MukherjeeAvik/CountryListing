package com.githubExamples.mvvm.architecture.di.components

import android.app.Application
import com.githubExamples.mvvm.architecture.MyApplication
import com.githubExamples.mvvm.architecture.di.ActivityBuilderModule
import com.githubExamples.mvvm.architecture.di.modules.AppModule
import com.githubExamples.mvvm.architecture.di.modules.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class, AppModule::class,
        RetrofitModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}
