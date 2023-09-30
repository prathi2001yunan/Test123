package cash.spont.v6.takeaway.ui.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cash.spont.v6.takeaway.ui.components.Auth1View
import cash.spont.v6.takeaway.ui.components.Auth2View
import cash.spont.v6.takeaway.ui.components.ErrorView
import cash.spont.v6.takeaway.ui.components.LoadingView
import cash.spont.v6.takeaway.ui.manager.SessionManager
import cash.spont.v6.takeaway.ui.viewmodel.MyViewModelFactory
import cash.spont.v6.takeaway.ui.viewmodel.OnBoardingScreenViewModel


@Composable
fun OnBoardingScreen(navigateToMainScreen: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val viewOnBoarding: OnBoardingScreenViewModel = viewModel(
            factory = MyViewModelFactory(context = LocalContext.current)
        )
        when (viewOnBoarding.screenView.value) {
            OnBoardingScreenViewModel.AuthFlow.ErrorView ->
                ErrorView(errorMsg = viewOnBoarding.errorMessage.value) {
                    viewOnBoarding.resetAuth()
                }

            OnBoardingScreenViewModel.AuthFlow.Auth1View ->
                Auth1View { viewOnBoarding.startAuth() }

            OnBoardingScreenViewModel.AuthFlow.Auth2View ->
                Auth2View(
                    { viewOnBoarding.userCode.value },
                    { viewOnBoarding.verifyUrl.value },
                    { viewOnBoarding.completeVerifyUrl.value }) {
                }

            else -> LoadingView { }
        }
    }
}





