package com.example.words.navigation

sealed class Screen(
    val route: String
) {
    object Home : Screen(ROUTE_HOME)
    object Word : Screen(ROUTE_WORD)
    object WebView : Screen(ROUTE_WEB_VIEW) {

        private const val ROUTE_FOR_ARGS = "veb_view"

        fun getRouteWithArgs(word: String): String {
            return "$ROUTE_FOR_ARGS/$word"
        }
    }

    object Search : Screen(ROUTE_SEARCH)
    object Statistics : Screen(ROUTE_STATISTICS)


    companion object {
        const val ROUTE_HOME = "home"
        const val KEY_WORD = "key_word"
        const val ROUTE_WORD = "word"
        const val ROUTE_WEB_VIEW = "veb_view/{$KEY_WORD}"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_STATISTICS = "statistics"
    }
}


