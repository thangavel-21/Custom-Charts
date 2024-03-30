package zuper.dev.android.dashboard.screens.tabs

import androidx.compose.runtime.Composable
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.screens.components.RefreshJobs


@Composable
fun CancelledScreen(cancelledJob: List<JobApiModel>?, isRefreshed: () -> Unit) {
    RefreshJobs(cancelledJob) {
        isRefreshed()
    }
}