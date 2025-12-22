package ru.practicum.android.diploma.core.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.core.theme.ButtonHeight
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.core.theme.White

@Composable
fun ElevatedButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        Modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = RoundedCornerShape(RadiusDefault),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue, contentColor = White
        ),
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
