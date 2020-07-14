package com.evaluation.digitas.coutryList.repos.local

import com.evaluation.digitas.coutryList.di.qualifiers.CacheDirectoryPathInfo
import com.evaluation.digitas.coutryList.repos.LocalRepository
import com.evaluation.digitas.coutryList.utils.FILE_NOT_FOUND
import io.reactivex.Observable
import java.io.*
import javax.inject.Inject

class FileRepository @Inject constructor(
    @CacheDirectoryPathInfo private val cacheDirectoryFile: File
) : LocalRepository<String> {

    var fileName = ""
    override fun saveData(data: String) {

        val file = File(cacheDirectoryFile, fileName)
        try {
            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeBytes(data)
            }
        } catch (ex: Exception) {

        }
    }


    override fun getDataFromLocal(): Observable<String> {

        return Observable.create { emitter ->
            val file = File(cacheDirectoryFile, fileName)
            if (!file.exists())
                emitter.onNext(FILE_NOT_FOUND)
            else {
                ObjectInputStream(FileInputStream(file)).use {
                    val data = String(it.readBytes())
                    emitter.onNext(data)
                }
            }
            emitter.onComplete()

        }
    }
}
