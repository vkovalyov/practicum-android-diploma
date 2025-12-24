package ru.practicum.android.diploma.core.ui.composable

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.core.theme.HeightBase
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.theme.PaddingIntoBase
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.features.search.presentation.mvvm.SearchVacancyViewModel

val END_PADDING = 17.dp

@Composable
fun SearchTextField(
    query: String,
    hintText: String,
    onValueChange: (String) -> Unit,
    viewModel: SearchVacancyViewModel,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = PaddingIntoBase, bottom = PaddingIntoBase),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(RadiusDefault))
                .background(MaterialTheme.colorScheme.surface)
                .padding(end = PaddingBase),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = { newValue ->
                        onValueChange(newValue)
                        viewModel.search(newValue)
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    cursorBrush = SolidColor(Blue),
                    singleLine = true,
                    modifier = Modifier
                        .height(HeightBase)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(
                            start = PaddingBase,
                            end = PaddingBase,
                            top = END_PADDING,
                            bottom = END_PADDING
                        ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                )
                if (query.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = PaddingBase),
                        text = hintText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (query.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                    ) {
                        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        val view = (context as? Activity)?.currentFocus
                        view?.let {
                            imm.hideSoftInputFromWindow(it.windowToken, 0)
                        }
                        onValueChange("")
                        viewModel.clear()
                    },
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
