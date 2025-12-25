package ru.practicum.android.diploma.features.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.core.theme.AppTheme
import ru.practicum.android.diploma.features.team.presentation.TeamScreen
import ru.practicum.android.diploma.features.team.presentation.mvvm.TeamViewModel
import kotlin.getValue

class TeamFragment : Fragment() {
    private val viewModel: TeamViewModel by viewModel()

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
                    TeamScreen(viewModel)
                }
            }
        }
        return composeView
    }
}
