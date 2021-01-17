package com.githubExamples.mvvm.architecture.data.repos.local

import com.githubExamples.mvvm.architecture.di.qualifiers.CacheDirectoryPathInfo
import com.githubExamples.mvvm.architecture.domain.GetCountryListingFromLocal
import com.githubExamples.mvvm.architecture.utils.FILE_NOT_FOUND
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.*
import javax.inject.Inject

class FileRepository (
    private val fileName:String,
    @CacheDirectoryPathInfo private val cacheDirectoryFile: File
) : GetCountryListingFromLocal {


    override suspend fun saveDataToLocal(data:String) {
        val file = File(cacheDirectoryFile, fileName)
        try {
            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeBytes(data)
            }
        } catch (ex: Exception) {

        }
    }

    override suspend fun getDataFromLocal(): String {

            val file = File(cacheDirectoryFile, fileName)
            if (!file.exists())
               return FILE_NOT_FOUND
            else {
                ObjectInputStream(FileInputStream(file)).use {
                    return String(it.readBytes())
                }
            }

    }
}
