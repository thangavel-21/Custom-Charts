package zuper.dev.android.dashboard.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.model.ArrangedOrderChart
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.screens.components.StackedBarChart
import zuper.dev.android.dashboard.screens.tabs.CancelledScreen
import zuper.dev.android.dashboard.screens.tabs.InProgressScreen
import zuper.dev.android.dashboard.screens.tabs.YetToStartScreen
import zuper.dev.android.dashboard.utils.Constants
import zuper.dev.android.dashboard.viewmodel.JobViewModel
import zuper.dev.android.dashboard.data.model.JobApiModel

/**
 * The job screen is an used to show details with status
 * @param navController used to manage the navigation
 * @param viewModel used tio handle state and api request
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobScreen(navController: NavHostController, viewModel: JobViewModel = JobViewModel()) {
    var jobs by remember { mutableStateOf<List<JobApiModel>>(emptyList()) }
    val completedJob = jobs.filter { it.status == JobStatus.Completed }
    val inProgressJob = jobs.filter { it.status == JobStatus.InProgress }
    val cancelledJob = jobs.filter { it.status == JobStatus.Canceled }
    val yetToStartJob = jobs.filter { it.status == JobStatus.YetToStart }
    val inCompleteJob = jobs.filter { it.status == JobStatus.Incomplete }
    viewModel.job.observeForever { jobs = it }

    LaunchedEffect(key1 = true) {
        viewModel.getJobs()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier
                .background(Color.White)
                .padding(start = 10.dp),
            title = {
                Text(
                    text = stringResource(id = (R.string.jobs), jobs.size),
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = Constants.BACK,
                        tint = Color.Black
                    )
                }
            })
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = (R.string.jobs), jobs.size))
                Text(
                    text = stringResource(
                        id = (R.string.job_completed),
                        jobs.size,
                        completedJob.size
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            StackedBarChart(
                values =
                listOf(
                    ArrangedOrderChart(
                        stringResource(id = R.string.completed, completedJob.size),
                        Color.Green,
                        completedJob.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.inProgress, inProgressJob.size),
                        Color.Cyan,
                        inProgressJob.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.cancelled, cancelledJob.size),
                        Color.Yellow,
                        cancelledJob.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.in_completed, inCompleteJob.size),
                        Color.Red,
                        inCompleteJob.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.yet_to_start, yetToStartJob.size),
                        Color.Gray,
                        yetToStartJob.size
                    ),
                ).sortedByDescending { it.size },
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        TabScreen(yetToStartJob, inProgressJob, cancelledJob) {
            viewModel.getJobs()
        }
    }
}

/**
 * The tab screen is an show the status of job
 * All the parameter are used to show details of each screen
 */
@Composable
fun TabScreen(
    yetToStartJob: List<JobApiModel>?,
    inProgressJob: List<JobApiModel>?,
    cancelledJob: List<JobApiModel>?,
    isRefreshed: () -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.yet_to_start, yetToStartJob?.size ?: 0),
        stringResource(id = R.string.inProgress, inProgressJob?.size ?: 0),
        stringResource(id = R.string.cancelled, cancelledJob?.size ?: 0),
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            containerColor = Color.White,
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color.Gray
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = {
                    Text(
                        title, style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp,
                            color = if (tabIndex == index) Color.Black else Color.Gray,
                            fontWeight = if (tabIndex == index) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> YetToStartScreen(yetToStartJob) { isRefreshed() }
            1 -> InProgressScreen(inProgressJob) { isRefreshed() }
            2 -> CancelledScreen(cancelledJob) { isRefreshed() }
        }
    }
}