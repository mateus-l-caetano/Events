package com.mateus.events.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventBottomSheetData (
    val id: Int,
    val title: String,
    val date: String,
    val address: String,
) : Parcelable
