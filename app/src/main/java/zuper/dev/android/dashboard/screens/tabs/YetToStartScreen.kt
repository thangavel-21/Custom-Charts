package zuper.dev.android.dashboard.screens.tabs

import androidx.compose.runtime.Composable
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.screens.components.RefreshJobs

@Composable
fun YetToStartScreen(yetToStartJob: List<JobApiModel>?, isRefreshed: () -> Unit) {
    RefreshJobs(yetToStartJob) {
        isRefreshed()
    }
}