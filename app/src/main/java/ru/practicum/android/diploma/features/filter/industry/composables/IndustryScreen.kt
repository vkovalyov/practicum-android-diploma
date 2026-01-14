package ru.practicum.android.diploma.features.filter.industry.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.ui.composable.ElevatedButton
import ru.practicum.android.diploma.features.filter.industry.mvvm.IndustryState
import ru.practicum.android.diploma.features.filter.industry.mvvm.IndustryViewModel

private val BOTTOM_PADDING = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndustryScreen(
    viewModel: IndustryViewModel,
    onBack: () -> Unit,
    onApply: () -> Unit
) {
    val state by viewModel.observeState().observeAsState(initial = IndustryState.Loading)
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
                        stringResource(R.string.select_industry),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            when (val currentState = state) {
                is IndustryState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.loading))
                    }
                }

                is IndustryState.Error -> {
                    IndustryErrorPlaceholder()
                }

                is IndustryState.Content -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = PaddingBase)
                    ) {
                        IndustrySearchTextField(
                            query = searchQuery,
                            onValueChange = { query ->
                                searchQuery = query
                                viewModel.search(query)
                            },
                            onClear = {
                                searchQuery = ""
                                viewModel.search("")
                            }
                        )

                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(
                                items = currentState.industries,
                                key = { it.id }
                            ) { industry ->
                                IndustryItem(
                                    industry = industry,
                                    isSelected = currentState.selectedIndustry?.id == industry.id,
                                    onClick = { viewModel.selectIndustry(industry) }
                                )
                            }
                        }

                        if (currentState.selectedIndustry != null) {
                            Spacer(modifier = Modifier.height(PaddingBase))
                            ElevatedButton(
                                text = stringResource(R.string.select),
                                onClick = {
                                    viewModel.applySelection { onApply() }
                                }
                            )
                            Spacer(modifier = Modifier.height(BOTTOM_PADDING))
                        }
                    }
                }
            }
        }
    }
}
