package ru.practicum.android.diploma.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.AppTheme

class VacancySearchFragment : Fragment() {

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
                    Scaffold { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = innerPadding.calculateTopPadding())
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Column() {
                                    Text("SEARCH")
                                    Button(onClick = {
                                        findNavController().navigate(R.id.action_to_detail)
                                    }) {
                                        Text("Поисковой элемент - детальное")
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(onClick = {
                                        findNavController().navigate(R.id.action_to_filter)
                                    }) {
                                        Text("Фильтр")
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return composeView
    }
}
