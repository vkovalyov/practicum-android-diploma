package ru.practicum.android.diploma.features.favorite

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
import ru.practicum.android.diploma.features.favorite.presentation.FavoriteScreen
import ru.practicum.android.diploma.features.favorite.presentation.mvvm.FavoriteViewModel

class VacancyFavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModel()

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
                    FavoriteScreen(viewModel, onVacancyClick = ::openVacancyDetail)
                }
            }
        }
        return composeView
    }

    private fun openVacancyDetail(id: String) {
        val bundle = bundleOf("vacancyId" to id)
        findNavController().navigate(R.id.action_favorite_to_detail, bundle)
    }
}
