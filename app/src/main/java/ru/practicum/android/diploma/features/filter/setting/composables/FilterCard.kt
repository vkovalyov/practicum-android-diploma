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

@Composable
fun FilterCard(name: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow__forward),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
