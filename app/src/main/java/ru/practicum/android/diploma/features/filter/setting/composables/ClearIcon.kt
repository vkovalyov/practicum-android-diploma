package ru.practicum.android.diploma.features.filter.setting.composables

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel

@Composable
fun ClearIcon(query: String, onValueChange: (String) -> Unit, viewModel: SettingFilterViewModel) {
    val context = LocalContext.current

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
