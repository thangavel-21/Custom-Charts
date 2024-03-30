package zuper.dev.android.dashboard.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.data.model.ArrangedOrderChart

/**
 * The function used to show card view for chart
 * The details will update for an each 30 second
 * @param title used to show the title of the card
 * @param total used to show the total of records based on job or invoice
 * @param completed used to show completed count
 * @param values used to show descending ordered arrangement
 * @param onClick used to n avigate
 */
@Composable
fun DashboardDetails(
    title: String,
    total: String,
    completed: String,
    values: List<ArrangedOrderChart>,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick?.let { onClick() }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = title, style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black
            )
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = total, style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Gray
                    )
                )
                Text(
                    text = completed, style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Gray
                    )
                )
            }
            StackedBarChart(
                values.sortedByDescending { it.size }
            )
            Spacer(modifier = Modifier.height(20.dp))
            CenteredStaggeredGrid(values)
        }
    }
}

@Composable
fun CenteredStaggeredGrid(list: List<ArrangedOrderChart>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth()
    ) {
        val lastItem = list.lastIndex
        items(list.size) { index ->
            val isLastItem = index == lastItem && list.size % 2 != 0 // Check for odd last item
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Fill the column width
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isLastItem) { // Add spacer only for odd last item
                        Spacer(modifier = Modifier.weight(1f)) // Add a spacer to center the last item
                    }
                    Spacer(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .size(14.dp)
                            .background(list[index].color)
                    )
                    Text(
                        modifier = Modifier.padding(start = 6.dp),
                        text = list[index].title,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    }
}