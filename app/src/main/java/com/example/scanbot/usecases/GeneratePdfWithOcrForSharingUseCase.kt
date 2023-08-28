package com.example.scanbot.usecases

import android.net.Uri
import com.example.scanbot.model.ExampleSingleton
import io.scanbot.demo.internal.common.data.storage.ISharingDocumentStorage
import com.example.scanbot.model.ensureFileExists
import io.scanbot.sdk.core.contourdetector.DetectionStatus
import io.scanbot.sdk.entity.Language
import io.scanbot.sdk.ocr.OpticalCharacterRecognizer
import io.scanbot.sdk.persistence.Page
import io.scanbot.sdk.process.PDFPageSize
import io.scanbot.sdk.util.PolygonHelper
import java.io.File
import javax.inject.Inject

class GeneratePdfWithOcrForSharingUseCase @Inject constructor(
    sharingDocumentStorage: ISharingDocumentStorage,
    exampleSingleton: ExampleSingleton,
) : GenerateFilesForSharingUseCase(sharingDocumentStorage) {
    private val opticalCharacterRecognizer: OpticalCharacterRecognizer = exampleSingleton.pageOpticalCharacterRecognizer()
    override suspend fun generateFilesForDocument(documentSharingDir: File, pages: List<String>): List<File> {
        val renderedPdfDocumentFile = opticalCharacterRecognizer.recognizeTextWithPdfFromPages(pages.map { pageId ->
            Page(
                pageId,
                PolygonHelper.getFullPolygon(),
                DetectionStatus.OK
            )
        }, PDFPageSize.A4, setOf(Language.DEU, Language.ENG)).sandwichedPdfDocumentFile

        val sharingPdfFile = documentSharingDir.ensureFileExists().resolve("${documentSharingDir.name}.pdf")
        renderedPdfDocumentFile?.copyTo(sharingPdfFile, overwrite = true)
        renderedPdfDocumentFile?.delete()
        return listOf(sharingPdfFile)
    }
}
