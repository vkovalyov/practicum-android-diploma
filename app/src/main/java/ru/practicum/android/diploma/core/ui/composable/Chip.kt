package ru.practicum.android.diploma.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.core.theme.White

val H_PADDING = 12.dp
val V_PADDING = 4.dp

@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(RadiusDefault))
            .background(Blue)
            .padding(horizontal = H_PADDING, vertical = V_PADDING)
    ) {
        Text(
            text = text,
            color = White,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}
