package com.example.scanbot.usecases

import android.net.Uri
import androidx.core.net.toFile
import com.example.scanbot.model.ExampleSingleton
import com.example.scanbot.model.ensureFileExists
import io.scanbot.demo.internal.common.data.storage.ISharingDocumentStorage
import io.scanbot.sdk.persistence.PageFileStorage
import java.io.File
import javax.inject.Inject

class GenerateJpgForSharingUseCase @Inject constructor(
    sharingDocumentStorage: ISharingDocumentStorage,
    exampleSingleton: ExampleSingleton, ) : GenerateFilesForSharingUseCase(sharingDocumentStorage) {
    private val pageFileStorage: PageFileStorage =  exampleSingleton.pageFileStorageInstance()
    override suspend fun generateFilesForDocument(documentSharingDir: File, pages: List<String>): List<File> {
        return pages.mapIndexed { index, page ->
            val pageFileName = if (pages.size == 1) "${page}.jpg" else "${page} (${index + 1}).jpg"
            val sharingJpgFile = documentSharingDir.ensureFileExists().resolve(pageFileName)

            val originalPageImage = pageFileStorage.getImageURI(page, PageFileStorage.PageFileType.DOCUMENT)
            originalPageImage.toFile().copyTo(sharingJpgFile, overwrite = true)
           sharingJpgFile
        }
    }

}
