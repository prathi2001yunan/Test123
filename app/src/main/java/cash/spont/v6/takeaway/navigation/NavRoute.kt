package cash.spont.v6.takeaway.navigation

sealed class NavRoute(val route: String) {
    object OnBoardingScreen : NavRoute("on_boarding_screen")
    object MainScreen : NavRoute("main_screen")
}