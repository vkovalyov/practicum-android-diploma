package ru.practicum.android.diploma.features.detail.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.utils.CurrencyFormatter
import ru.practicum.android.diploma.features.detail.domain.entity.Salary
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import java.text.NumberFormat
import java.util.Locale

@Composable
fun VacancyContent(
    vacancy: VacancyDetail,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = PaddingBase)
    ) {
        Text(
            text = vacancy.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = formatSalary(vacancy.salary),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(PaddingBase))

        EmployerSection(vacancy)
        Spacer(Modifier.height(PaddingBase))

        vacancy.experience?.let {
            Text(
                text = stringResource(R.string.required_experience),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
        }

        vacancy.employment?.let { employment ->
            val scheduleText = vacancy.schedule?.let { ", $it" } ?: ""
            Text(
                text = "$employment$scheduleText",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(PaddingBase))
        }

        vacancy.description?.let {
            Text(
                text = stringResource(R.string.vacancy_description),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            HtmlText(it)
            Spacer(Modifier.height(PaddingBase))
        }

        vacancy.skills?.takeIf { it.isNotEmpty() }?.let { skills ->
            SkillsSection(skills)
            Spacer(Modifier.height(PaddingBase))
        }

        vacancy.contacts?.let { contacts ->
            ContactsSection(contacts, onEmailClick, onPhoneClick)
        }

        Spacer(Modifier.height(PaddingBase))
    }
}

@Composable
private fun formatSalary(salary: Salary?): String {
    val formatter = { value: Int -> NumberFormat.getNumberInstance(Locale("ru")).format(value) }
    val currency = CurrencyFormatter.getDisplayName(salary?.currency)

    return when {
        salary?.from != null && salary.to != null ->
            "${stringResource(R.string.salary_range, formatter(salary.from), formatter(salary.to))} $currency"
        salary?.from != null ->
            "${stringResource(R.string.salary_from, formatter(salary.from))} $currency"
        salary?.to != null ->
            "${stringResource(R.string.salary_to, formatter(salary.to))} $currency"
        else -> stringResource(R.string.salary_not_specified)
    }
}

@Composable
fun HtmlText(html: String) {
    val cleanText = html
        .replace(Regex("<br\\s*/?>"), "\n")
        .replace(Regex("<li>"), "• ")
        .replace(Regex("</li>"), "\n")
        .replace(Regex("<[^>]*>"), "")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .trim()

    Text(
        text = cleanText,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}
