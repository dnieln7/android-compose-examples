package xyz.dnieln7.canvascourse.common.composable.custom.tictactoe

import java.io.Serializable

sealed class TicTacToeEvent : Serializable {
    class Waiting(val turn: MoveType) : TicTacToeEvent()
    class Finished(val winner: MoveType?) : TicTacToeEvent()
}