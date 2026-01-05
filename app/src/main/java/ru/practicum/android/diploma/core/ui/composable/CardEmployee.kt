package ru.practicum.android.diploma.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.LightGray
import ru.practicum.android.diploma.core.theme.PaddingIntoBase
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.core.theme.White
import ru.practicum.android.diploma.core.utils.CurrencyFormatter
import ru.practicum.android.diploma.core.utils.ImageLoader
import ru.practicum.android.diploma.features.search.domain.entity.Salary
import java.text.NumberFormat
import java.util.Locale

val IMAGE_PADDING = 2.dp

val CARD_PADDING = 9.dp

val IMAGE_SIZE = 48.dp

val BORDER_SIZE = 1.dp

@Composable
fun CardVacancy(
    vacancyName: String,
    employerName: String,
    employerLogo: String?,
    salary: Salary?,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(RadiusDefault))
            .background(backgroundColor)
            .padding(vertical = CARD_PADDING)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(IMAGE_SIZE)
                    .border(
                        width = BORDER_SIZE,
                        color = LightGray,
                        RoundedCornerShape(RadiusDefault)
                    )
                    .clip(RoundedCornerShape(RadiusDefault))
                    .background(White)

            ) {
                AsyncImage(
                    model = employerLogo,
                    imageLoader = ImageLoader.get(LocalContext.current),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(IMAGE_PADDING)
                        .matchParentSize()
                        .background(White)
                        .clip(RoundedCornerShape(RadiusDefault)),
                    placeholder = painterResource(R.drawable.placeholder),
                )
            }
            Spacer(Modifier.width(PaddingIntoBase))
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    vacancyName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    employerName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                val formatter = { value: Int ->
                    NumberFormat.getNumberInstance(Locale("ru")).format(value)
                }

                val currency = CurrencyFormatter.getDisplayName(salary?.currency)

                val salaryText = when {
                    salary?.from != null && salary.to != null -> {
                        "${
                            stringResource(
                                R.string.salary_range,
                                formatter(salary.from),
                                formatter(salary.to)
                            )
                        } $currency"
                    }

                    salary?.from != null -> {
                        "${
                            stringResource(
                                R.string.salary_from,
                                formatter(salary.from)
                            )
                        } $currency"
                    }

                    salary?.to != null -> {
                        "${
                            stringResource(
                                R.string.salary_to,
                                formatter(salary.to)
                            )
                        } $currency"
                    }

                    else -> stringResource(R.string.salary_not_specified)
                }

                Text(
                    salaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
