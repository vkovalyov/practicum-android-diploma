package ru.practicum.android.diploma.features.filter.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.AppTheme
import ru.practicum.android.diploma.features.filter.setting.composables.SettingFilterScreen
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel
import kotlin.getValue

class SettingFilterFragment : Fragment() {

    private val viewModel: SettingFilterViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = ::onBack) {
                                        Icon(
                                            painter = painterResource(R.drawable.back_arrow),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                                ),
                                title = {
                                    Text(
                                        stringResource(R.string.filter_settings),
                                        maxLines = 1,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .padding(top = innerPadding.calculateTopPadding())
                        ) {
                            SettingFilterScreen(viewModel,::onBack)
                        }
                    }
                }
            }
        }

        return composeView
    }


    fun onBack(){
        findNavController().popBackStack()
    }
}
