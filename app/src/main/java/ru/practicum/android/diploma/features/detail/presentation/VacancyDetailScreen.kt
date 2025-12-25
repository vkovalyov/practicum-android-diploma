package ru.practicum.android.diploma.features.detail.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.LightGray
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.core.ui.composable.Chip
import ru.practicum.android.diploma.core.utils.CurrencyFormatter
import ru.practicum.android.diploma.core.utils.ImageLoader
import ru.practicum.android.diploma.features.detail.domain.entity.Contacts
import ru.practicum.android.diploma.features.detail.domain.entity.Salary
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.detail.presentation.mvvm.VacancyDetailState
import ru.practicum.android.diploma.features.detail.presentation.mvvm.VacancyDetailViewModel
import java.text.NumberFormat
import java.util.Locale

private val LOGO_SIZE = 48.dp
private val BORDER_SIZE = 1.dp
private val IMAGE_PADDING = 2.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailScreen(
    viewModel: VacancyDetailViewModel,
    onBackClick: () -> Unit,
    onFavoriteClick: (VacancyDetail) -> Unit
) {
    val state by viewModel.state.observeAsState(VacancyDetailState.Loading)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.vacancy),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (state is VacancyDetailState.Content) {
                        IconButton(onClick = {
                            val vacancy = (state as VacancyDetailState.Content).vacancy
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, vacancy.url ?: "")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, null))
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.sharing),
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = {
                            onFavoriteClick((state as VacancyDetailState.Content).vacancy)
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.favorites_off),
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                is VacancyDetailState.Loading -> LoadingContent()
                is VacancyDetailState.Content -> VacancyContent(
                    vacancy = currentState.vacancy,
                    onEmailClick = { email ->
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$email")
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    },
                    onPhoneClick = { phone ->
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${phone.filter { it.isDigit() || it == '+' }}")
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    }
                )
                is VacancyDetailState.Error -> ErrorContent()
                is VacancyDetailState.NoInternet -> NoInternetContent()
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VacancyContent(
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
            Text(
                text = stringResource(R.string.key_skills),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                skills.forEach { skill -> Chip(skill) }
            }
            Spacer(Modifier.height(PaddingBase))
        }

        vacancy.contacts?.let { contacts ->
            ContactsSection(contacts, onEmailClick, onPhoneClick)
        }

        Spacer(Modifier.height(PaddingBase))
    }
}

@Composable
private fun EmployerSection(vacancy: VacancyDetail) {
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

@Composable
private fun ContactsSection(
    contacts: Contacts,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit
) {
    val hasContent = !contacts.name.isNullOrEmpty() ||
        !contacts.email.isNullOrEmpty() ||
        !contacts.phones.isNullOrEmpty()

    if (!hasContent) return

    Text(
        text = stringResource(R.string.contacts),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(Modifier.height(8.dp))

    contacts.name?.takeIf { it.isNotEmpty() }?.let {
        Text(stringResource(R.string.contact_person), style = MaterialTheme.typography.bodySmall)
        Text(it, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
    }

    contacts.email?.takeIf { it.isNotEmpty() }?.let { email ->
        Text(stringResource(R.string.email), style = MaterialTheme.typography.bodySmall)
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onEmailClick(email) }
        )
        Spacer(Modifier.height(8.dp))
    }

    contacts.phones?.forEach { phone ->
        Text(stringResource(R.string.phone), style = MaterialTheme.typography.bodySmall)
        Text(
            text = phone,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onPhoneClick(phone) }
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun HtmlText(html: String) {
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
private fun ErrorContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(R.drawable.server_error_vacancy), contentDescription = null)
        Spacer(Modifier.height(PaddingBase))
        Text(
            text = stringResource(R.string.server_error),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun NoInternetContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(R.drawable.no_internet), contentDescription = null)
        Spacer(Modifier.height(PaddingBase))
        Text(
            text = stringResource(R.string.no_internet),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}
