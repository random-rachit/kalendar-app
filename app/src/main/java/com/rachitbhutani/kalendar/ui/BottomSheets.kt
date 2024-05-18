package com.rachitbhutani.kalendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rachitbhutani.kalendar.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    modifier: Modifier = Modifier,
    onDone: (title: String, description: String) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberModalBottomSheetState()
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke() },
        modifier = modifier.fillMaxWidth(),
        sheetState = state
    ) {
        val focusManager = LocalFocusManager.current

        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(horizontal = 16.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            singleLine = true,
            value = title,
            onValueChange = { title = it }, placeholder = {
                Text(text = "Add the task")
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ), shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            placeholder = {
                Text(text = "Add the description")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = {
                    onDone.invoke(title, description)
                }
            ), shape = RoundedCornerShape(16.dp))
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                onDone.invoke(title, description)
            }) {
            Text(text = "Done")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateChangeBottomSheet(
    modifier: Modifier = Modifier,
    currentMonth: Int,
    currentYear: Int,
    onDone: (month: Int, year: Int) -> Unit,
    onDismiss: (month: Int, year: Int) -> Unit
) {
    var month by remember { mutableIntStateOf(currentMonth) }
    var year by remember { mutableIntStateOf(currentYear) }

    val state = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke(month, year) },
        modifier = modifier.fillMaxWidth(),
        sheetState = state,
    ) {
        val monthScrollState = rememberLazyListState()
        val yearScrollState = rememberLazyListState()

        val monthList = Constants.getMonthsOfYear()
        val yearList = Constants.getYearList()

        LaunchedEffect(Unit) {
            monthScrollState.scrollToItem(currentMonth)
            yearScrollState.scrollToItem(yearList.indexOf(currentYear), -230)
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(max = 240.dp),
                state = monthScrollState
            ) {
                itemsIndexed(monthList) { index, item ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (index == month) Color.Yellow else Color.Transparent
                            )
                            .clickable {
                                month = index
                            }, text = item,
                        textAlign = TextAlign.Center,
                        color = if (index == month) Color.Black else Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(max = 240.dp),
                state = yearScrollState
            ) {
                itemsIndexed(Constants.getYearList()) { index, item ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (item == year) Color.Yellow else Color.Transparent
                            )
                            .clickable {
                                year = item
                            }, text = item.toString(),
                        textAlign = TextAlign.Center,
                        color = if (item == year) Color.Black else Color.Unspecified
                    )
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    onDone.invoke(-1, -1)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Today")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    onDone.invoke(month, year)
                }) {
                Text(text = "Done")
            }
        }
    }
}