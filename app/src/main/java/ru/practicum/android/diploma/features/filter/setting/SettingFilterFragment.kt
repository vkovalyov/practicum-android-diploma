package ru.practicum.android.diploma.features.filter.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
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
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("industry_result")
            ?.observe(viewLifecycleOwner) { result ->
                val industryId = result.getString("industryId")
                val industryName = result.getString("industryName")

                if (industryId != null && industryName != null) {
                    viewModel.changeIndustry(industryId, industryName)
                }
            }

        val composeView = ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppTheme {
                    SettingFilterScreen(
                        viewModel = viewModel,
                        onBack = ::onBack,
                        onIndustryClick = ::onIndustryClick
                    )
                }
            }
        }
        return composeView
    }

    fun onBack() {
        findNavController().popBackStack()
    }

    fun onIndustryClick() {
        findNavController().navigate(R.id.action_to_industries_filter)
    }
}
