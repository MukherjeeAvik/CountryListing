package com.evaluation.digitas.coutryList.domain

interface Mapper<K, V> {
    fun mapFrom(k: K): V
}
