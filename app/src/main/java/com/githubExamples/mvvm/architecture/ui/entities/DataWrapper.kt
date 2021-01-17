package com.githubExamples.mvvm.architecture.ui.entities

import com.githubExamples.mvvm.architecture.data.repos.remote.models.CountryListResponse
import com.githubExamples.mvvm.architecture.domain.entity.CountryItem
import com.githubExamples.mvvm.architecture.domain.usecase.Source

data class DataWrapper(val list: List<CountryItem>, val source: Source)