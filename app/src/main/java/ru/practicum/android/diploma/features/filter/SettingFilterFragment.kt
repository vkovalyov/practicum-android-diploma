package ru.practicum.android.diploma.features.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.AppTheme

class SettingFilterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
                            Column(){
                                Text("Настройки фильтрации")
                                Button(onClick = {
                                    findNavController().navigate(R.id.action_to_place_of_work_filter)
                                }) {
                                    Text("Место работы")
                                }
                                Button(onClick = {
                                    findNavController().navigate(R.id.action_to_industries_filter)
                                }) {
                                    Text("Выбор отрасли")
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
