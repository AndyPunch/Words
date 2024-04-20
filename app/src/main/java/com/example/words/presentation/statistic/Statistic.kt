package com.example.words.presentation.statistic

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.words.R
import com.example.words.domain.entity.TopBarState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun StatisticsScreen(
    navHostController: NavHostController,
    onComposingTopBar: (TopBarState) -> Unit
) {
    val title = stringResource(R.string.statistics_screen_title)
    LaunchedEffect(key1 = true) {
        onComposingTopBar(
            TopBarState(
                topBarTitle = title,
                navigationIcon = null
            )
        )
    }

    BackHandler {
        navHostController.navigateUp()
    }
    val pagerState = rememberPagerState(pageCount = 2)
    Column(modifier = Modifier.fillMaxSize()) {
        TabLayout(pagerState)
        TabContent(pagerState)
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(pagerState: PagerState) {
    val tabData = mutableListOf<String>("One", "Two")
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {
            Spacer(modifier = Modifier.height(5.dp))
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 5.dp,
                color = Color.White
            )
        },


        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        tabData.forEachIndexed { index, s ->
            Log.i("asdwqedwq", "tabdata " + s)
            Tab(selected = pagerState.currentPage == index, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },

                text = {
                    Text(text = s, color = Color.Black)
                })
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState
    ) { pageIndex ->
        when (pageIndex) {
            0 -> LastAnswered()

            1 -> LastNonAnswered()
        }
    }
}


@Composable
fun LastAnswered() {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(Color.LightGray)
            .fillMaxSize()
    )
}


@Composable
fun LastNonAnswered() {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(Color.Blue)
            .fillMaxSize()
    )
}