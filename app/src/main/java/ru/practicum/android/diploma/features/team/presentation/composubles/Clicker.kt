package ru.practicum.android.diploma.features.team.presentation.composubles

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.features.team.presentation.mvvm.TeamViewModel

const val PAUSE_TIME = 50L

@Composable
fun Clicker(viewModel: TeamViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val score by viewModel.observeStateScore().observeAsState(initial = 0)

        var isPressed by remember { mutableStateOf(false) }

        LaunchedEffect(isPressed) {
            if (isPressed) {
                viewModel.changeScore()
                delay(PAUSE_TIME)
                isPressed = false
            }
        }
        val scale by animateFloatAsState(
            targetValue = if (isPressed) 1.1f else 1f,
            animationSpec = tween(durationMillis = 100),
            label = "imageScale"
        )

        Text(
            stringResource(R.string.cliker),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            "${stringResource(R.string.score)}: $score",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Image(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    isPressed = true
                },
            painter = painterResource(id = R.drawable.vacancy_not_found),
            contentDescription = null,
        )
    }
}
