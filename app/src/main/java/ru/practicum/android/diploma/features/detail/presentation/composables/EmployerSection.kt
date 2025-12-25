package ru.practicum.android.diploma.features.detail.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.LightGray
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.core.utils.ImageLoader
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail

private val LOGO_SIZE = 48.dp
private val BORDER_SIZE = 1.dp
private val IMAGE_PADDING = 2.dp

@Composable
fun EmployerSection(vacancy: VacancyDetail) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(LOGO_SIZE)
                .border(BORDER_SIZE, LightGray, RoundedCornerShape(RadiusDefault))
        ) {
            AsyncImage(
                model = vacancy.employer?.logo,
                imageLoader = ImageLoader.get(LocalContext.current),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(IMAGE_PADDING)
                    .matchParentSize()
                    .clip(RoundedCornerShape(RadiusDefault)),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)
            )
        }
        Spacer(Modifier.width(PaddingBase))
        Column {
            Text(
                text = vacancy.employer?.name ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            val location = vacancy.address?.fullAddress
                ?: vacancy.address?.city
                ?: vacancy.areaName ?: ""
            if (location.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
