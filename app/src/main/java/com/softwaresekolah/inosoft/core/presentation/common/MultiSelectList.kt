package com.softwaresekolah.inosoft.core.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MultiSelectionState (initialSelectModeEnabled: Boolean = false){
    var isMultiSelectionModeEnabled by mutableStateOf(initialSelectModeEnabled)
}

object MultiSelectionStateSaver: Saver<MultiSelectionState, Boolean> {
    override fun restore(value: Boolean): MultiSelectionState? {
        return MultiSelectionState(value)
    }

    override fun SaverScope.save(value: MultiSelectionState): Boolean? {
        return value.isMultiSelectionModeEnabled
    }
}

@Composable
fun rememberMultiSelectionState(): MultiSelectionState {
    return rememberSaveable(saver = MultiSelectionStateSaver) {
        MultiSelectionState()
    }
}

@Composable
fun <T> MultiSelectList(
    modifier: Modifier = Modifier,
    state: MultiSelectionState,
    items: List<T>,
    selectedItems: List<T>,
    itemContent: @Composable (T) -> Unit,
    key: ((T) -> Any)? = null,
    onClick: (T) -> Unit
) {
    LazyColumn (modifier = modifier) {
        items(
            items,
            key = key
        ){item ->
            MultiSelectionContainer(
                multiSelectionModeChange = {
                    state.isMultiSelectionModeEnabled = it
                },
                onClick = { onClick(item) })
            {
                itemContent(item)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultiSelectionContainer(
    modifier: Modifier = Modifier,
    multiSelectionModeChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box (
        modifier = modifier
            .fillMaxSize()
            .heightIn(min = 50.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    multiSelectionModeChange(true)
                    onClick()
                }
            ),
        contentAlignment = Alignment.CenterEnd
    ){
        content()
    }
}