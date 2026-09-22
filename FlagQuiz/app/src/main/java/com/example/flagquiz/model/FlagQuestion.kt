package com.example.flagquiz.model

import java.io.Serializable

data class FlagQuestion(
    val countryName: String,
    val imageResId: Int
) : Serializable