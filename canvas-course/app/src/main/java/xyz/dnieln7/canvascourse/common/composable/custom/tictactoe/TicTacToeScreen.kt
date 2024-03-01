package xyz.dnieln7.canvascourse.common.composable.custom.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TicTactToeScreen() {
    var currentTicTacToeEvent: TicTacToeEvent by remember {
        mutableStateOf(TicTacToeEvent.Waiting(MoveType.CROSS))
    }

    var showRestarting by remember { mutableStateOf(false) }

    BoxWithConstraints {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TicTacToe(
                modifier = Modifier
                    .size(400.dp)
                    .background(Color.White)
                    .padding(20.dp),
                restatingTimeInMilliseconds = 5000,
                onRestarting = { showRestarting = it },
                onTicTacToeEvent = { currentTicTacToeEvent = it },
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (showRestarting) {
                Text(
                    text = "Restarting in 5 seconds...",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            TicTacToeAlerts(currentTicTacToeEvent)
        }
    }
}

@Composable
private fun TicTacToeAlerts(ticTacToeEvent: TicTacToeEvent) {
    when (ticTacToeEvent) {
        is TicTacToeEvent.Waiting -> {
            Text(
                text = "Now is ${ticTacToeEvent.turn.name} turn!",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is TicTacToeEvent.Finished -> {
            if (ticTacToeEvent.winner != null) {
                Text(
                    text = "The game is over ${ticTacToeEvent.winner.name} won!",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    text = "The game is over, it's a tie",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
