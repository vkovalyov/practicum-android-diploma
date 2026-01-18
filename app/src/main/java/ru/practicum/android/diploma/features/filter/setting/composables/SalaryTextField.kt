package ru.practicum.android.diploma.features.filter.setting.composables

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.theme.PaddingIntoBase
import ru.practicum.android.diploma.core.theme.RadiusDefault
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel

val TEXT_FILED_HEIGHT = 19.dp

@Composable
fun SalaryTextField(
    query: String,
    hintText: String,
    onValueChange: (String) -> Unit,
    viewModel: SettingFilterViewModel,
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val textColor = if (isFocused) {
        Blue
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = PaddingBase,
                        end = PaddingBase,
                        top = PaddingIntoBase,
                        bottom = PaddingIntoBase
                    ),
            ) {
                Text(
                    stringResource(
                        R.string.expected_salary
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
                Box() {
                    BasicTextField(
                        modifier = Modifier
                            .focusable()
                            .height(TEXT_FILED_HEIGHT)
                            .background(MaterialTheme.colorScheme.surface)
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                isFocused = it.isFocused
                            },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = query,
                        onValueChange = { newValue ->
                            onValueChange(newValue)
                            viewModel.changeSalary(newValue)
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        cursorBrush = SolidColor(Blue),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    if (query.isEmpty()) {
                        Text(
                            text = hintText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            if (query.isNotEmpty()) {
                Icon(
                    modifier = Modifier

                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            val view = (context as? Activity)?.currentFocus
                            view?.let {
                                imm.hideSoftInputFromWindow(it.windowToken, 0)
                            }
                            onValueChange("")
                            viewModel.changeSalary(null)
                        },
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
