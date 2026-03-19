package com.example.modernnotes.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.modernnotes.R
import com.example.modernnotes.domain.notes.models.SortOrder

@Composable
fun SortOrder.toDisplayString(): String {
    return stringResource(
        when (this) {
            SortOrder.TITLE -> R.string.sort_order_title
            SortOrder.DATE_DESC -> R.string.sort_order_date_descendant
            SortOrder.DATE_ASC -> R.string.sort_order_date_ascendant
        }
    )
}