package ru.practicum.android.diploma.core.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.core.theme.AppTheme

@Composable
fun JobSearchApp() {
    val navController = rememberNavController()

    AppTheme {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        val showBottomNav = currentDestination?.let { route ->
            !route.startsWith(NavRoutes.VacancyDetails.route) &&
            !route.startsWith(NavRoutes.Filter.route) &&
            !route.startsWith(NavRoutes.FilterRegion.route) &&
            !route.startsWith(NavRoutes.FilterCountry.route) &&
            !route.startsWith(NavRoutes.FilterIndustry.route)
        } ?: true

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            bottomBar = {
                if (showBottomNav) {
                    BottomNavigationBar(navController)
                }
            }
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
