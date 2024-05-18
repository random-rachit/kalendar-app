package com.rachitbhutani.kalendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rachitbhutani.kalendar.R
import com.rachitbhutani.kalendar.util.CalendarHeaderListener
import com.rachitbhutani.kalendar.util.CalendarInteractionListener
import com.rachitbhutani.kalendar.util.Constants

@Composable
fun CalendarBox(
    modifier: Modifier = Modifier,
    offsetDays: Int,
    days: Int,
    month: String,
    year: String,
    listener: CalendarInteractionListener?
) {
    Column(modifier.fillMaxSize()) {
        CalendarHeader(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            month,
            year,
            listener
        )
        CalendarDateGrid(Modifier.fillMaxWidth(), days, offsetDays) { date ->
            listener?.onDateSelected(date)
        }
    }
}

@Composable
fun CalendarDateGrid(
    modifier: Modifier = Modifier,
    days: Int,
    offsetDays: Int,
    onClick: (date: Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(horizontal = 16.dp),
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        items(Constants.getDaysOfWeek()) {
            Text(text = it, textAlign = TextAlign.Center)
        }
        items(span = { GridItemSpan(offsetDays) }, count = 1) {
            Box {}
        }
        items(days) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .border(
                        2.dp,
                        if (isSystemInDarkTheme()) Color.DarkGray else Color.Gray,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        onClick.invoke(it)
                    }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = (it + 1).toString(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CalendarHeader(
    modifier: Modifier,
    month: String,
    year: String,
    listener: CalendarHeaderListener?
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .clickable { listener?.onGridChange(false) },
            painter = painterResource(id = R.drawable.round_arrow_back_ios_new_24),
            contentDescription = "Left"
        )

        Text(modifier = Modifier.clickable {
            listener?.onHeaderClick()
        }, text = "$month, $year")

        Icon(
            modifier = Modifier
                .padding(16.dp)
                .rotate(180f)
                .clickable { listener?.onGridChange(true) },
            painter = painterResource(id = R.drawable.round_arrow_back_ios_new_24),
            contentDescription = "Right"
        )
    }
}


@Preview
@Composable
fun CalendarBoxPrev() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        CalendarBox(offsetDays = 4, days = 31, month = "May", year = "2024", listener = null)
    }
}