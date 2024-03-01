package xyz.dnieln7.canvascourse.common.composable.custom.tictactoe

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

@Composable
fun TicTacToe(
    modifier: Modifier = Modifier,
    restatingTimeInMilliseconds: Long,
    onRestarting: (Boolean) -> Unit,
    onTicTacToeEvent: (TicTacToeEvent) -> Unit,
) {
    var sizeValue by remember { mutableFloatStateOf(0F) }
    var firstThird by remember { mutableFloatStateOf(0F) }
    var secondThird by remember { mutableFloatStateOf(0F) }

    val touchPadding by remember { mutableFloatStateOf(20F) }
    var boundSize by remember { mutableStateOf(Size.Zero) }

    var rect7 by remember { mutableStateOf(Rect.Zero) }
    var rect4 by remember { mutableStateOf(Rect.Zero) }
    var rect1 by remember { mutableStateOf(Rect.Zero) }
    var rect8 by remember { mutableStateOf(Rect.Zero) }
    var rect5 by remember { mutableStateOf(Rect.Zero) }
    var rect2 by remember { mutableStateOf(Rect.Zero) }
    var rect9 by remember { mutableStateOf(Rect.Zero) }
    var rect6 by remember { mutableStateOf(Rect.Zero) }
    var rect3 by remember { mutableStateOf(Rect.Zero) }

    var moves by remember { mutableStateOf(mapOf<Int, Move>()) }
    var turn by remember { mutableStateOf(MoveType.CROSS) }

    var currentEvent: TicTacToeEvent by remember {
        mutableStateOf(TicTacToeEvent.Waiting(MoveType.CROSS))
    }

    val crossLastMoveVisibility by animateFloatAsState(
        targetValue = if (turn == MoveType.CROSS) 0F else 1F,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing,
        ),
        label = "crossLastMoveVisibility",
    )

    val circleLastMoveVisibility by animateFloatAsState(
        targetValue = if (turn == MoveType.CIRCLE) 0F else 1F,
        animationSpec = tween(
            durationMillis = 1500,
            easing = LinearOutSlowInEasing,
        ),
        label = "circleLastMoveVisibility",
    )

    LaunchedEffect(key1 = currentEvent) {
        if (currentEvent is TicTacToeEvent.Finished) {
            onTicTacToeEvent(currentEvent)
            onRestarting(true)
            delay(restatingTimeInMilliseconds)
            onRestarting(false)

            moves = emptyMap()
            turn = MoveType.CROSS
            currentEvent = TicTacToeEvent.Waiting(turn)
        } else {
            onTicTacToeEvent(currentEvent)
        }
    }

    Canvas(
        modifier = modifier.pointerInput(currentEvent !is TicTacToeEvent.Finished) {
            detectTapGestures {
                when {
                    rect1.contains(it) && !moves.contains(1) -> {
                        val move = 1 to Move(rect1, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect2.contains(it) && !moves.contains(2) -> {
                        val move = 2 to Move(rect2, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect3.contains(it) && !moves.contains(3) -> {
                        val move = 3 to Move(rect3, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect4.contains(it) && !moves.contains(4) -> {
                        val move = 4 to Move(rect4, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect5.contains(it) && !moves.contains(5) -> {
                        val move = 5 to Move(rect5, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect6.contains(it) && !moves.contains(6) -> {
                        val move = 6 to Move(rect6.copy(), turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect7.contains(it) && !moves.contains(7) -> {
                        val move = 7 to Move(rect7, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect8.contains(it) && !moves.contains(8) -> {
                        val move = 8 to Move(rect8, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }

                    rect9.contains(it) && !moves.contains(9) -> {
                        val move = 9 to Move(rect9, turn)

                        moves = moves.plus(move)
                        turn = turn.opposite()

                        val winner = checkGame(moves)

                        currentEvent = if (winner != null || moves.size >= 9) {
                            TicTacToeEvent.Finished(winner)
                        } else {
                            TicTacToeEvent.Waiting(turn)
                        }
                    }
                }
            }
        },
    ) {
        if (sizeValue == 0F) {
            sizeValue = size.width
            firstThird = sizeValue * 0.33F
            secondThird = sizeValue * 0.66F
            boundSize = Size(
                width = firstThird - touchPadding * 2,
                height = firstThird - touchPadding * 2,
            )

            rect7 = Rect(
                offset = Offset(
                    x = 0F + touchPadding,
                    y = 0F + touchPadding,
                ),
                size = boundSize,
            )

            rect4 = Rect(
                offset = Offset(
                    x = 0F + touchPadding,
                    y = firstThird + touchPadding,
                ),
                size = boundSize,
            )

            rect1 = Rect(
                offset = Offset(
                    x = 0F + touchPadding,
                    y = secondThird + touchPadding,
                ),
                size = boundSize,
            )

            rect8 = Rect(
                offset = Offset(
                    x = firstThird + touchPadding,
                    y = 0F + touchPadding,
                ),
                size = boundSize,
            )

            rect5 = Rect(
                offset = Offset(
                    x = firstThird + touchPadding,
                    y = firstThird + touchPadding,
                ),
                size = boundSize,
            )

            rect2 = Rect(
                offset = Offset(
                    x = firstThird + touchPadding,
                    y = secondThird + touchPadding,
                ),
                size = boundSize,
            )

            rect9 = Rect(
                offset = Offset(
                    x = secondThird + touchPadding,
                    y = 0F + touchPadding,
                ),
                size = boundSize,
            )

            rect6 = Rect(
                offset = Offset(
                    x = secondThird + touchPadding,
                    y = firstThird + touchPadding,
                ),
                size = boundSize,
            )

            rect3 = Rect(
                offset = Offset(
                    x = secondThird + touchPadding,
                    y = secondThird + touchPadding,
                ),
                size = boundSize,
            )
        }

        drawLine(
            start = Offset(firstThird, 0F),
            end = Offset(firstThird, sizeValue),
            color = Color.Black,
            strokeWidth = 20F,
        )

        drawLine(
            start = Offset(secondThird, 0F),
            end = Offset(secondThird, sizeValue),
            color = Color.Black,
            strokeWidth = 20F,
        )

        drawLine(
            start = Offset(0F, firstThird),
            end = Offset(sizeValue, firstThird),
            color = Color.Black,
            strokeWidth = 20F,
        )

        drawLine(
            start = Offset(0F, secondThird),
            end = Offset(sizeValue, secondThird),
            color = Color.Black,
            strokeWidth = 20F,
        )

        for (move in moves.values) {
            if (move.moveType == MoveType.CIRCLE) {
                val movePath = if (move == moves.values.last()) {
                    Path().apply {
                        moveTo(move.bounds.topLeft.x, move.bounds.topLeft.y)
                        arcTo(move.bounds, 180F, 359F * circleLastMoveVisibility, true)
                    }
                } else {
                    Path().apply {
                        moveTo(move.bounds.topLeft.x, move.bounds.topLeft.y)
                        arcTo(move.bounds, 180F, 359F, true)
                    }
                }

                drawPath(
                    path = movePath, color = Color.Blue, style = Stroke(
                        width = 10F,
                        cap = StrokeCap.Round,
                    )
                )
            } else {
                val topToBottomEnd = if (move == moves.values.last()) {
                    Offset(
                        x = move.bounds.topLeft.x + (move.bounds.bottomRight.x * crossLastMoveVisibility) - (move.bounds.topLeft.x * crossLastMoveVisibility),
                        y = move.bounds.topLeft.y + (move.bounds.bottomRight.y * crossLastMoveVisibility) - (move.bounds.topLeft.y * crossLastMoveVisibility),
                    )
                } else {
                    Offset(
                        x = move.bounds.bottomRight.x,
                        y = move.bounds.bottomRight.y,
                    )
                }

                drawLine(
                    color = Color.Red,
                    start = move.bounds.topLeft,
                    end = topToBottomEnd,
                    strokeWidth = 10F,
                    cap = StrokeCap.Round
                )

                val bottomToTopEnd = if (move == moves.values.last()) {
                    Offset(
                        x = move.bounds.bottomLeft.x + (move.bounds.topRight.x * crossLastMoveVisibility) - (move.bounds.bottomLeft.x * crossLastMoveVisibility),
                        y = move.bounds.bottomLeft.y + (move.bounds.topRight.y * crossLastMoveVisibility) - (move.bounds.bottomLeft.y * crossLastMoveVisibility),
                    )
                } else {
                    Offset(
                        x = move.bounds.topRight.x,
                        y = move.bounds.topRight.y,
                    )
                }

                drawLine(
                    color = Color.Red,
                    start = move.bounds.bottomLeft,
                    end = bottomToTopEnd,
                    strokeWidth = 10F,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

fun checkGame(map: Map<Int, Move>): MoveType? {
    val matrix: Array<Array<Int>> = arrayOf(
        arrayOf(
            map[7]?.moveType?.ordinal ?: -4,
            map[8]?.moveType?.ordinal ?: -4,
            map[9]?.moveType?.ordinal ?: -4
        ),
        arrayOf(
            map[4]?.moveType?.ordinal ?: -4,
            map[5]?.moveType?.ordinal ?: -4,
            map[6]?.moveType?.ordinal ?: -4
        ),
        arrayOf(
            map[1]?.moveType?.ordinal ?: -4,
            map[2]?.moveType?.ordinal ?: -4,
            map[3]?.moveType?.ordinal ?: -4
        ),
    )

    for (row in matrix) {
        println(row.contentToString())
    }

    val cross1 = matrix[0][0] + matrix[1][1] + matrix[2][2]

    if (cross1 == 3) {
        return MoveType.CROSS
    } else if (cross1 == 0) {
        return MoveType.CIRCLE
    }

    val cross2 = matrix[0][2] + matrix[1][1] + matrix[2][0]

    if (cross2 == 3) {
        return MoveType.CROSS
    } else if (cross2 == 0) {
        return MoveType.CIRCLE
    }

    for (i in matrix.indices) {
        val row = matrix[i][0] + matrix[i][1] + matrix[i][2]

        if (row == 3) {
            return MoveType.CROSS
        } else if (row == 0) {
            return MoveType.CIRCLE
        }

        val column = matrix[0][i] + matrix[1][i] + matrix[2][i]

        if (column == 3) {
            return MoveType.CROSS
        } else if (column == 0) {
            return MoveType.CIRCLE
        }
    }

    return null
}