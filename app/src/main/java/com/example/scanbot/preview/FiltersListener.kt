package com.example.scanbot.preview

import io.scanbot.sdk.process.ImageFilterType

interface FiltersListener {
    fun onFilterApplied(filterType: ImageFilterType)
}
