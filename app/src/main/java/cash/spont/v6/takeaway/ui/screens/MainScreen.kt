package cash.spont.v6.takeaway.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cash.spont.v6.takeaway.navigation.NavRoute
import cash.spont.v6.takeaway.ui.components.OrderItemView
import cash.spont.v6.takeaway.ui.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(navigateToOnBoardingScreen: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val mainView: MainScreenViewModel = viewModel()
        OrderItemView(
            alertDialog = { mainView.alertDialog.value },
            onStopDialog = { mainView.stopDialog() },
            onShowDialog = { mainView.showDialog() }) {
            navigateToOnBoardingScreen()
        }
    }
}
