package cash.spont.v6.takeaway.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cash.spont.v6.takeaway.ui.screens.MainScreen
import cash.spont.v6.takeaway.ui.screens.OnBoardingScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.OnBoardingScreen.route) {
        composable(route = NavRoute.OnBoardingScreen.route) {
            OnBoardingScreen {
                navController.navigate(NavRoute.MainScreen.route) {
                    navController.popBackStack(NavRoute.OnBoardingScreen.route, true)
                }
            }
        }
        composable(route = NavRoute.MainScreen.route) {
            MainScreen {
                navController.navigate(NavRoute.OnBoardingScreen.route) {
                    navController.popBackStack(NavRoute.MainScreen.route, true)
                }
            }
        }

    }
}


