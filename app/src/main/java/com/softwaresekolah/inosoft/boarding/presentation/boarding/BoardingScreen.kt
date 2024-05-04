package com.softwaresekolah.inosoft.boarding.presentation.boarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.boarding.data.pages
import com.softwaresekolah.inosoft.boarding.presentation.component.BoardingPage
import com.softwaresekolah.inosoft.boarding.presentation.component.PageIndicator
import com.softwaresekolah.inosoft.core.presentation.Dimens.MediumPadding2
import com.softwaresekolah.inosoft.core.presentation.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.core.presentation.common.SoftwareSekolahTextButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoardingScreen(
    onEvent: (BoardingEvent) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    val buttonState = remember {
        derivedStateOf {
            when(pagerState.currentPage){
                0-> listOf("", "Next")
                1-> listOf("Prev", "Next")
                2-> listOf("Prev", "Get Started")
                else -> listOf("", "")
            }
        }
    }

    Scaffold (
        bottomBar = {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MediumPadding2)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    PageIndicator(modifier = Modifier.width(52.dp), pageSize = pages.size,
                        selectedPage = pagerState.currentPage)

                    Row (verticalAlignment = Alignment.CenterVertically){

                        val scope = rememberCoroutineScope()

                        if (buttonState.value[0].isNotEmpty()){
                            SoftwareSekolahTextButton(
                                text = buttonState.value[0],
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                                    }
                                }
                            )
                        }

                        SoftwareSekolahButton(
                            text = buttonState.value[1],
                            onClick = {
                                scope.launch {
                                    if(pagerState.currentPage == 2){
                                        onEvent(BoardingEvent.saveAppEntry)
                                    }else{
                                        pagerState.animateScrollToPage(
                                            page = pagerState.currentPage + 1
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.clip(RoundedCornerShape(6.dp))
                        )
                    }
            }
        }
    ){  padding->
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(padding)
        ){
            HorizontalPager(state = pagerState) {index->
                BoardingPage(page = pages[index])
            }
        }
    }


}