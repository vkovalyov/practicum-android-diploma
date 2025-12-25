package ru.practicum.android.diploma.core.utils

object CurrencyFormatter {

    private val currencyNames = mapOf(
        "RUB" to "₽",
        "BYR" to "Br",
        "USD" to "$",
        "EUR" to "€",
        "KZT" to "₸",
        "UAH" to "₴",
        "AZN" to "₼",
        "UZS" to "сум",
        "GEL" to "ლ",
        "KGS" to "сом"
    )

    fun getDisplayName(code: String?): String {
        if (code == null) return ""
        return currencyNames[code.uppercase()] ?: code
    }
}
