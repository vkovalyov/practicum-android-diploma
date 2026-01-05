package ru.practicum.android.diploma.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.AppTheme
import ru.practicum.android.diploma.features.search.presentation.SearchScreen
import ru.practicum.android.diploma.features.search.presentation.mvvm.SearchVacancyViewModel
import kotlin.getValue

class VacancySearchFragment : Fragment() {
    private val viewModel: SearchVacancyViewModel by viewModel()

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
                    SearchScreen(viewModel, onVacancyClick = ::openVacancyDetail)

                }
            }
        }
        return composeView
    }

    fun openVacancyDetail(id: String) {
        val bundle = bundleOf("vacancyId" to id)
        findNavController().navigate(R.id.action_to_detail, bundle)
    }
}
