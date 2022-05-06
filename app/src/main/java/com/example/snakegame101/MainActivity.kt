package com.example.snakegame101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.snakegame101.ui.theme.Shapes
import com.example.snakegame101.ui.theme.SnakeGame101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val game = Game(lifecycleScope)
        setContent {
            SnakeGame101Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    snake(game)
                }

            }
        }
    }

    @Composable
    private fun snake(game: Game) {
        val state = game.state.collectAsState(initial = null)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            state.value?.let {
                board(it)
            }
            buttons {
                game.move = it
            }

        }

    }

    @Composable
    private fun buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
        val buttonSize = Modifier.size(64.dp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Button(onClick = { onDirectionChange(Pair(0, -1)) }, modifier = buttonSize) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
            Row {
                Button(onClick = { onDirectionChange(Pair(-1, 0)) }, modifier = buttonSize) {
                    Icon(Icons.Default.KeyboardArrowLeft, null)
                }
                Spacer(modifier = buttonSize)
                Button(onClick = { onDirectionChange(Pair(1, 0)) }, modifier = buttonSize) {
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }

            }

            Button(onClick = { onDirectionChange(Pair(0, 1)) }, modifier = buttonSize) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }

    }

    @Composable
    private fun board(state: State) {
        BoxWithConstraints(Modifier.padding(16.dp)) {
            val tileSize = maxWidth / Game.BOARD_SIZE
            Box(
                modifier = Modifier
                    .size(maxWidth)
                    .border(2.dp, Color.Green)
            )
            Box(
                modifier = Modifier
                    .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                    .size(tileSize)
                    .background(
                        Color.DarkGray, CircleShape
                    )
            )
            state.snake.forEach {
                Box(
                    modifier = Modifier
                        .offset(x = tileSize * it.first, y = tileSize * it.second)
                        .size(tileSize)
                        .background(Color.DarkGray, Shapes.small)
                )
            }

        }

    }
}

//
//
//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SnakeGame101Theme {
//        Greeting("Android")
//    }
//}