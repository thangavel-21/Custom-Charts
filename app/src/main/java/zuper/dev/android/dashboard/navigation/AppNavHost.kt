package zuper.dev.android.dashboard.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import zuper.dev.android.dashboard.screens.DashBoardScreen
import zuper.dev.android.dashboard.screens.JobScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.DashBoard.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.DashBoard.route) {
            DashBoardScreen(navController)
        }
        composable(NavigationItem.Job.route) {
            Log.e("TAG", "AppNavHost: called ", )
            JobScreen(navController)
        }
    }
}