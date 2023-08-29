package com.example.scanbot.usecases

import android.graphics.Bitmap
import com.example.scanbot.model.ExampleSingleton
import com.example.scanbot.model.ensureFileExists
import io.scanbot.demo.internal.common.data.storage.ISharingDocumentStorage
import io.scanbot.sdk.persistence.PageFileStorage
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class GeneratePngForSharingUseCase @Inject constructor(
    sharingDocumentStorage: ISharingDocumentStorage,
    exampleSingleton: ExampleSingleton, ) : GenerateFilesForSharingUseCase(sharingDocumentStorage) {
    private val pageFileStorage: PageFileStorage =  exampleSingleton.pageFileStorageInstance()
    override suspend fun generateFilesForDocument(documentSharingDir: File, pages: List<String>): List<File> {
        return pages.mapIndexed { index, page ->
            val pageFileName = if (pages.size == 1) "${page}.png" else "${page} (${index + 1}).png"
            val sharingJpgFile = documentSharingDir.ensureFileExists().resolve(pageFileName)

            val originalPageBitmap = pageFileStorage.getImage(page, PageFileStorage.PageFileType.DOCUMENT)

            FileOutputStream(sharingJpgFile).use {
                originalPageBitmap?.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            sharingJpgFile
        }
    }

}
