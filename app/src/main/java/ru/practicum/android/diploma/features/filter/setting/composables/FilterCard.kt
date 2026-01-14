package ru.practicum.android.diploma.features.filter.setting.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.Gray

val FILTER_CARD_HEIGHT = 48.dp

@Composable
fun FilterCard(
    name: String,
    value: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(FILTER_CARD_HEIGHT)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = value ?: name,
            style = MaterialTheme.typography.bodyMedium,
            color = if (value != null) MaterialTheme.colorScheme.onBackground else Gray
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow__forward),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
