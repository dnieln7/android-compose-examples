package xyz.dnieln7.canvascourse.common.composable.custom.tictactoe

enum class MoveType {
    CIRCLE, CROSS;

    fun opposite(): MoveType {
        return if (this == CIRCLE) {
            CROSS
        } else {
            CIRCLE
        }
    }
}
