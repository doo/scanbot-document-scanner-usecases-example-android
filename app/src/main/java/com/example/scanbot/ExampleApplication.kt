package com.example.scanbot

import android.app.Application
import io.scanbot.sdk.ScanbotSDKInitializer
import io.scanbot.sdk.core.contourdetector.ContourDetector
import io.scanbot.sdk.persistence.CameraImageFormat
import io.scanbot.sdk.persistence.PageStorageSettings


class ExampleApplication : Application() {

    companion object {
        private const val LICENSE_KEY = "" // "YOUR_SCANBOT_SDK_LICENSE_KEY"
        const val USE_ENCRYPTION = false
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize the Scanbot Barcode Scanner SDK:
        ScanbotSDKInitializer()
            .withLogging(true)
            .contourDetectorType(ContourDetector.Type.ML_BASED)
            .usePageStorageSettings(PageStorageSettings.Builder()
                .imageFormat(CameraImageFormat.JPG)
                .imageQuality(80)
                .build())
            .license(this, LICENSE_KEY)
            .initialize(this)
    }


}