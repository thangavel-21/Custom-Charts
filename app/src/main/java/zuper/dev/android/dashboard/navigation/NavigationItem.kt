package zuper.dev.android.dashboard.navigation

enum class Screen {
    DASHBOARD,
    JOB,
}
sealed class NavigationItem(val route: String) {
    object DashBoard : NavigationItem(Screen.DASHBOARD.name)
    object Job : NavigationItem(Screen.JOB.name)
}