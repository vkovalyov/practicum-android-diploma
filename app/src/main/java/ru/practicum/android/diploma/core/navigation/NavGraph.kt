package ru.practicum.android.diploma.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Search.route,
        modifier = modifier
    ) {
        composable(route = NavRoutes.Search.route) {
            SearchScreenStub(navController = navController)
        }

        composable(route = NavRoutes.Favorites.route) {
            FavoritesScreenStub(navController = navController)
        }

        composable(route = NavRoutes.Team.route) {
            TeamScreenStub()
        }

        composable(
            route = "${NavRoutes.VacancyDetails.route}/{vacancyId}",
            arguments = listOf(navArgument("vacancyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val vacancyId = backStackEntry.arguments?.getString("vacancyId") ?: ""
            VacancyDetailsScreenStub(vacancyId = vacancyId, navController = navController)
        }

        composable(route = NavRoutes.Filter.route) {
            FilterScreenStub(navController = navController)
        }

        composable(route = NavRoutes.FilterRegion.route) {
            FilterRegionScreenStub(navController = navController)
        }

        composable(route = NavRoutes.FilterCountry.route) {
            FilterCountryScreenStub(navController = navController)
        }

        composable(route = NavRoutes.FilterIndustry.route) {
            FilterIndustryScreenStub(navController = navController)
        }
    }
}

@Composable
fun SearchScreenStub(navController: NavHostController) {
    ScreenStub(
        title = "Поиск вакансий",
        buttonText = "Открыть фильтр",
        onButtonClick = { navController.navigate(NavRoutes.Filter.route) }
    )
}

@Composable
fun FavoritesScreenStub(navController: NavHostController) {
    ScreenStub(title = "Избранное")
}

@Composable
fun TeamScreenStub() {
    ScreenStub(title = "Команда")
}

@Composable
fun VacancyDetailsScreenStub(vacancyId: String, navController: NavHostController) {
    ScreenStub(title = "Вакансия: $vacancyId")
}

@Composable
fun FilterScreenStub(navController: NavHostController) {
    ScreenStub(
        title = "Фильтр",
        buttonText = "Выбор региона",
        onButtonClick = { navController.navigate(NavRoutes.FilterRegion.route) }
    )
}

@Composable
fun FilterRegionScreenStub(navController: NavHostController) {
    ScreenStub(title = "Выбор региона")
}

@Composable
fun FilterCountryScreenStub(navController: NavHostController) {
    ScreenStub(title = "Выбор страны")
}

@Composable
fun FilterIndustryScreenStub(navController: NavHostController) {
    ScreenStub(title = "Выбор отрасли")
}

@Composable
fun ScreenStub(
    title: String,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title)
            if (buttonText != null && onButtonClick != null) {
                androidx.compose.foundation.layout.Spacer(
                    modifier = Modifier.padding(8.dp)
                )
                androidx.compose.material3.Button(onClick = onButtonClick) {
                    Text(text = buttonText)
                }
            }
        }
    }
}
