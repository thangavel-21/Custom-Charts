package zuper.dev.android.dashboard.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.data.model.ArrangedOrderChart
import zuper.dev.android.dashboard.utils.HelperFunction

/**
 * This function used to draw the stacked chart
 * @param values is an list of details for draw and chart
 */
@Composable
fun StackedBarChart(values: List<ArrangedOrderChart>) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val totalSize = values.sumOf { it.size }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        values.forEach { input ->
            val itemHeight = HelperFunction.calculatePercentage(input.size, totalSize) * screenWidth.value / 100
            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .width(itemHeight.dp)
                    .background(input.color)
            )
        }
    }
}