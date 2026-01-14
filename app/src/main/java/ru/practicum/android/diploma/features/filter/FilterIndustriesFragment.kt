package ru.practicum.android.diploma.features.filter

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
import ru.practicum.android.diploma.core.theme.AppTheme
import ru.practicum.android.diploma.features.filter.industry.composables.IndustryScreen
import ru.practicum.android.diploma.features.filter.industry.mvvm.IndustryViewModel

class FilterIndustriesFragment : Fragment() {

    private val viewModel: IndustryViewModel by viewModel()

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
                    IndustryScreen(
                        viewModel = viewModel,
                        onBack = { findNavController().popBackStack() },
                        onApply = ::onApply
                    )
                }
            }
        }
        return composeView
    }

    fun onApply(industryId: String, industryName: String) {
        val result = bundleOf(
            "industryId" to industryId,
            "industryName" to industryName
        )

        findNavController().previousBackStackEntry?.savedStateHandle?.set("industry_result", result)

        findNavController().popBackStack()
    }
}
