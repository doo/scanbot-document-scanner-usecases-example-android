package com.example.scanbot.usecases

import android.net.Uri
import androidx.core.net.toFile
import com.example.scanbot.model.ExampleSingleton
import io.scanbot.demo.internal.common.data.storage.ISharingDocumentStorage
import com.example.scanbot.model.ensureFileExists
import io.scanbot.sdk.persistence.PageFileStorage
import io.scanbot.sdk.tiff.TIFFWriter
import io.scanbot.sdk.tiff.model.TIFFImageWriterParameters
import java.io.File
import javax.inject.Inject

class GenerateTiffForSharingUseCase @Inject constructor(
    sharingDocumentStorage: ISharingDocumentStorage,
    exampleSingleton: ExampleSingleton,

    ) : GenerateFilesForSharingUseCase(sharingDocumentStorage) {
    private val pageFileStorage: PageFileStorage = exampleSingleton.pageFileStorageInstance()
    private val tiffWriter: TIFFWriter = exampleSingleton.pageTIFFWriter()
    override suspend fun generateFilesForDocument(
        documentSharingDir: File,
        pages: List<String>
    ): List<File> {
        val sharingTiffFile =
            documentSharingDir.ensureFileExists().resolve("${documentSharingDir.name}.tiff")

        val originalPagesFiles = pages.map { pageId ->
            val originalPageUri =
                pageFileStorage.getImageURI(pageId, PageFileStorage.PageFileType.DOCUMENT)
            originalPageUri.toFile()
        }

        tiffWriter.writeTIFFFromFiles(
            sourceFiles = originalPagesFiles,
            targetFile = sharingTiffFile,
            sourceFilesEncrypted = false,
            parameters = TIFFImageWriterParameters.defaultParametersForBinaryImages()
        )

        return listOf(sharingTiffFile)
    }
}
