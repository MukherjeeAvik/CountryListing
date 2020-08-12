package com.githubExamples.mvvm.architecture.domain

interface Mapper<K, V> {
    fun mapFrom(k: K): V
}
