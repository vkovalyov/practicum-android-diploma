package ru.practicum.android.diploma.features.filter.industry.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase

@Composable
fun IndustryErrorPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.failed_get_list),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(PaddingBase))
        Text(
            text = stringResource(R.string.unable_retrieve_industries),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}
