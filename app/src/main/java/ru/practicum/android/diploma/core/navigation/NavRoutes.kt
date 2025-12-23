package ru.practicum.android.diploma.core.navigation

import androidx.annotation.DrawableRes
import ru.practicum.android.diploma.R

sealed class NavRoutes(
    val name: String,
    val route: String,
    val showBottomNav: Boolean,
    @DrawableRes val iconRes: Int? = null
) {
    data object Search : NavRoutes(
        name = "Главная",
        route = "search",
        showBottomNav = true,
        iconRes = R.drawable.main
    )

    data object Favorites : NavRoutes(
        name = "Избранное",
        route = "favorites",
        showBottomNav = true,
        iconRes = R.drawable.favorites_off
    )

    data object Team : NavRoutes(
        name = "Команда",
        route = "team",
        showBottomNav = true,
        iconRes = R.drawable.team
    )

    data object VacancyDetails : NavRoutes(
        name = "Вакансия",
        route = "vacancy_details",
        showBottomNav = false
    )

    data object Filter : NavRoutes(
        name = "Фильтр",
        route = "filter",
        showBottomNav = false
    )

    data object FilterRegion : NavRoutes(
        name = "Выбор региона",
        route = "filter_region",
        showBottomNav = false
    )

    data object FilterCountry : NavRoutes(
        name = "Выбор страны",
        route = "filter_country",
        showBottomNav = false
    )

    data object FilterIndustry : NavRoutes(
        name = "Выбор отрасли",
        route = "filter_industry",
        showBottomNav = false
    )

    companion object {
        val bottomMenuItems by lazy { listOf(Search, Favorites, Team) }
    }
}
