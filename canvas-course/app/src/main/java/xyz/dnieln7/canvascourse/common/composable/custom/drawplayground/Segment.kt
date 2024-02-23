package xyz.dnieln7.canvascourse.common.composable.custom.drawplayground

import androidx.compose.ui.geometry.Offset

data class Segment(
    val id: Long,
    val startPosition: Offset,
    val endPosition: Offset,
)