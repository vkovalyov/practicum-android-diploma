package ru.practicum.android.diploma.core.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.theme.ButtonHeight
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.core.theme.Red

@Composable
fun AppTextButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        Modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = RoundedCornerShape(RadiusDefault),
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge, color = Red)
    }
}
