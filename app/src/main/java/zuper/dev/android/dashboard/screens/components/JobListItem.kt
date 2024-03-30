package zuper.dev.android.dashboard.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.utils.HelperFunction
import zuper.dev.android.dashboard.R

/**
 * The list item is used to show list of job based on job status
 * @param job the data used to iterate the list details
 */
@Composable
fun JobListItem(job: JobApiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = job.jobNumber.toString(), style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Gray
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = job.title, style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = stringResource(
                    id = R.string.date_time,
                    HelperFunction.convertUtcToLocalTime(job.startTime) ?: "",
                    HelperFunction.convertUtcToLocalTime(
                        job.endTime
                    ) ?: ""
                ),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        }
    }
}