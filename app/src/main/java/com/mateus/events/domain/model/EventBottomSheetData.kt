package com.mateus.events.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventBottomSheetData (
    val id: String,
    val title: String,
    val date: String,
    val address: String,
) : Parcelable
