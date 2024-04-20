package com.example.words.presentation.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.words.R
import com.example.words.domain.entity.TopBarState
import com.example.words.domain.entity.Word
import com.example.words.utils.extentions.clickableOnce

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    onComposingTopBar: (TopBarState) -> Unit,
    onSearchResultClick: (Word) -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchList = viewModel.searchData.observeAsState()
    val title = stringResource(R.string.search_screen_title)

    var queryString by rememberSaveable { mutableStateOf("") }
    var keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        onComposingTopBar(
            TopBarState(
                topBarTitle = title,
                navigationIcon = null
            )
        )
        focusRequester.requestFocus()
    }

    SearchBar(
        query = queryString,
        onQueryChange = { newQueryString ->
            queryString = newQueryString
            viewModel.searchWords(queryString)
        },
        onSearch = {
            // It is triggered when the user taps on the Search icon on the keyboard.
            // The current query comes as a parameter of this callback.
            val word = Word(word = it)
            onSearchResultClick(word)
        },
        modifier = Modifier.focusRequester(focusRequester),
        placeholder = {
            Text(text = "Search words")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (queryString.isNotEmpty()) {
                IconButton(onClick = {
                    queryString = ""
                    viewModel.searchWords("")


                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                searchList.value?.let {
                    items(
                        count = it.size,
                        key = { index -> it[index].id ?: 0 },
                        itemContent = { index ->
                            val word = it[index]
                            WordListItem(word = word,
                                onSearchResultClick = {
                                    onSearchResultClick(it)
                                })
                        }
                    )
                }

            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp

    )

    BackHandler {
        navHostController.navigateUp()
    }
}

@Composable
fun WordListItem(
    word: Word,
    modifier: Modifier = Modifier,
    onSearchResultClick: (Word) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickableOnce {
                onSearchResultClick(word)
            }
    ) {
        Text(text = word.word)
    }

}