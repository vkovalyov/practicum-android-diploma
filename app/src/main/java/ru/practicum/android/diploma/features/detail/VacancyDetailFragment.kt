package ru.practicum.android.diploma.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.core.theme.AppTheme
import ru.practicum.android.diploma.features.detail.presentation.VacancyDetailScreen
import ru.practicum.android.diploma.features.detail.presentation.mvvm.VacancyDetailViewModel

class VacancyDetailFragment : Fragment() {

    private val vacancyId: String by lazy {
        arguments?.getString("vacancyId") ?: ""
    }

    private val viewModel: VacancyDetailViewModel by viewModel { parametersOf(vacancyId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppTheme {
                    VacancyDetailScreen(
                        viewModel = viewModel,
                        onBackClick = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }
}
