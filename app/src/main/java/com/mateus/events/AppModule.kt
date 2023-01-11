package com.mateus.events

import com.mateus.events.common.BASE_URL
import com.mateus.events.data.network.EventsApiService
import com.mateus.events.data.repository.EventRepository
import com.mateus.events.domain.use_case.CheckInUseCase
import com.mateus.events.domain.use_case.GetEventsUseCase
import com.mateus.events.presenter.viewModel.EventViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideEventsApiService(): EventsApiService{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        val retrofitService: EventsApiService by lazy { retrofit.create(EventsApiService::class.java) }

        return retrofitService
    }

    @Provides
    @Singleton
    fun provideEventRepository(api: EventsApiService): EventRepository = EventRepository(api)

    @Provides
    @Singleton
    fun provideGetEventsUseCase(repository: EventRepository): GetEventsUseCase = GetEventsUseCase(repository)

    @Provides
    @Singleton
    fun provideCheckInUseCase(repository: EventRepository): CheckInUseCase = CheckInUseCase(repository)

    @Provides
    @Singleton
    fun provideEventViewModel(getEventsUseCase: GetEventsUseCase, checkInUseCase: CheckInUseCase): EventViewModel = EventViewModel(getEventsUseCase, checkInUseCase)
}