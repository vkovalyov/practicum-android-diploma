package ru.practicum.android.diploma.features.detail.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.features.detail.domain.entity.Contacts

@Composable
fun ContactsSection(
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
