package zuper.dev.android.dashboard.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.model.ArrangedOrderChart
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.navigation.NavigationItem
import zuper.dev.android.dashboard.screens.components.DashboardDetails
import zuper.dev.android.dashboard.utils.Constants
import zuper.dev.android.dashboard.utils.HelperFunction
import zuper.dev.android.dashboard.viewmodel.DashboardViewModel

/**
 * The dashboard screen is an initial screen of the application
 * @param navController used to manage the navigation
 * @param viewModel used tio handle state and api request
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = DashboardViewModel()
) {
    val jobs = remember { mutableStateOf<List<JobApiModel>>(emptyList()) }
    val invoice = remember { mutableStateOf<List<InvoiceApiModel>>(emptyList()) }
    val completedJob = jobs.value.filter { it.status == JobStatus.Completed }
    val inProgressJob = jobs.value.filter { it.status == JobStatus.InProgress }
    val cancelledJob = jobs.value.filter { it.status == JobStatus.Canceled }
    val yetToStartJob = jobs.value.filter { it.status == JobStatus.YetToStart }
    val inCompleteJob = jobs.value.filter { it.status == JobStatus.Incomplete }
    val draftInvoice = invoice.value.filter { it.status == InvoiceStatus.Draft }
    val pendingInvoice = invoice.value.filter { it.status == InvoiceStatus.Pending }
    val paidInvoice = invoice.value.filter { it.status == InvoiceStatus.Paid }
    val badDebtInvoice = invoice.value.filter { it.status == InvoiceStatus.BadDebt }
    val totalInvoice = invoice.value.sumOf { it.total }
    val completedTotalInvoice = paidInvoice.sumOf { it.total }
    viewModel.job.observeForever { jobs.value = it }
    viewModel.invoice.observeForever { invoice.value = it }

    LaunchedEffect(true) {
        viewModel.getObserveJobs()
        viewModel.getObserveInvoice()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.background(color = Color.White),
            title = {
                Text(
                    text = stringResource(id = R.string.dashboard),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.Black
                    )
                )
            })
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.welcome_message),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = HelperFunction.getCurrentDate(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.Gray,
                                fontSize = 14.sp,
                            )
                        )
                    }
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = Constants.USER_IMAGE
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            DashboardDetails(
                stringResource(id = R.string.job_stats),
                stringResource(id = R.string.jobs, jobs.value.size),
                stringResource(id = R.string.job_completed, completedJob.size, jobs.value.size),
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
                ),
            ) {
                navController.navigate(NavigationItem.Job.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            DashboardDetails(
                stringResource(id = R.string.invoice_stats),
                stringResource(id = R.string.total_value, totalInvoice),
                stringResource(id = R.string.collected, completedTotalInvoice),
                listOf(
                    ArrangedOrderChart(
                        stringResource(id = R.string.draft, draftInvoice.size),
                        Color.Yellow,
                        draftInvoice.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.pending, pendingInvoice.size),
                        Color.Cyan,
                        pendingInvoice.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.paid, paidInvoice.size),
                        Color.Green,
                        paidInvoice.size
                    ),
                    ArrangedOrderChart(
                        stringResource(id = R.string.bad_debt, badDebtInvoice.size),
                        Color.Red,
                        badDebtInvoice.size
                    ),
                ),
            )
        }
    }
}