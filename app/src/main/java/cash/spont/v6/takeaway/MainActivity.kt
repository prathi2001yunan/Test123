package cash.spont.v6.takeaway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cash.spont.v6.takeaway.navigation.NavGraph
import cash.spont.v6.takeaway.ui.manager.PreferenceManager
import cash.spont.v6.takeaway.ui.theme.TvTakeAwayTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setup(applicationContext)
        setContent {
            TvTakeAwayTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
