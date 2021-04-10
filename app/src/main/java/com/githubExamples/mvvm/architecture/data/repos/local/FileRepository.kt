package com.githubExamples.mvvm.architecture.data.repos.local

import com.githubExamples.mvvm.architecture.di.qualifiers.CacheDirectoryPathInfo
import com.githubExamples.mvvm.architecture.domain.Repository
import com.githubExamples.mvvm.architecture.utils.FILE_NOT_FOUND
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.*
import javax.inject.Inject

class FileRepository @Inject constructor(
    @CacheDirectoryPathInfo private val cacheDirectoryFile: File
) : Repository<String> {

    var fileName = ""
    override fun saveData(data: String): Completable {

        return Completable.create { emitter ->
            val file = File(cacheDirectoryFile, fileName)
            try {
                ObjectOutputStream(FileOutputStream(file)).use {
                    it.writeBytes(data)
                }
                emitter.onComplete()
            } catch (ex: Exception) {
                emitter.onComplete()
            }
        }
    }


    override fun getData(vararg params: Any): Observable<String> {
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
