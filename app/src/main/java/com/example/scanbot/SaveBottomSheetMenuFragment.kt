package com.example.scanbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.scanbot.example.SaveListener
import io.scanbot.sdk.usecases.documents.R

/**
 * Represents bottom menu sheet for document screen with saving dialog
 */
class SaveBottomSheetMenuFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.save_bottom_sheet, container, false)

        view.findViewById<Button>(R.id.save_with_ocr).setOnClickListener {
            (activity as SaveListener).saveWithOcr()
            dismissAllowingStateLoss()
        }
        view.findViewById<Button>(R.id.save_tiff).setOnClickListener {
            (activity as SaveListener).saveTiff()
            dismissAllowingStateLoss()
        }
        return view
    }
}
