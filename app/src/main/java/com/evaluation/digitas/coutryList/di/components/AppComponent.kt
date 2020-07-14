package com.evaluation.digitas.coutryList.di.components

import com.evaluation.digitas.coutryList.di.modules.RetrofitModule
import android.app.Application
import com.evaluation.digitas.coutryList.MyApplication
import com.evaluation.digitas.coutryList.di.ActivityBuilderModule
import com.evaluation.digitas.coutryList.di.modules.AppModule
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
