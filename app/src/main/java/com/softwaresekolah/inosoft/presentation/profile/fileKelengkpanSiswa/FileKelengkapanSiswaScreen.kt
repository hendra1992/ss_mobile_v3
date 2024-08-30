package com.softwaresekolah.inosoft.presentation.profile.fileKelengkpanSiswa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.profile.component.KelengkapanItem
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileKelengkapanSiswaScreen(
    navigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = { ProfileMenuTopBar(title = "Data Diri", scrollBehavior = scrollBehavior, navigateUp = navigateUp) }
    ) {padding ->
        val scrollState = rememberLazyListState()

        val list = listOf("Kartu Keluarga", "Ijasah", "Akte Kelahiran", "asdasdasdas", "dgsfuihiuasfuihduifhuihfui asjddhsjahdsja")
        LazyColumn(
            modifier = Modifier.padding(padding),
            state = scrollState,
            contentPadding = PaddingValues(16.dp)
        ){
            val itemCount = if(list.size % 2 == 0){
                list.size / 2
            }else{
                list.size / 2 +1
            }
            items(itemCount){
                KelengkapanRow(rowIndex = it, entries = list)
            }
        }
    }
}

@Composable
fun KelengkapanRow(
    rowIndex: Int,
    entries: List<String>,
//    navController: NavController
){
    Column {
        Row {
            KelengkapanItem(
//                entry = entries[rowIndex * 2],
//                navController = navController,
                entries = entries[rowIndex * 2],
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2){
                KelengkapanItem(
//                    entry = entries[rowIndex * 2 + 1],
//                    navController = navController,
                    modifier = Modifier.weight(1f),
                    entries = entries[rowIndex * 2 + 1]
                )
            }else{
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}