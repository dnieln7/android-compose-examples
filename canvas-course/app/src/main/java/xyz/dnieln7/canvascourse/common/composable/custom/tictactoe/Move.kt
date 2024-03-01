package xyz.dnieln7.canvascourse.common.composable.custom.tictactoe

import androidx.compose.ui.geometry.Rect

data class Move(
    val bounds: Rect,
    val moveType: MoveType,
)