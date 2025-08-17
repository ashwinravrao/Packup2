package com.ashwinrao.packup.ui.composables

import com.ashwinrao.packup.R
import com.ashwinrao.packup.ui.theme.PackupTheme
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersistentSearchBar(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    onHamburgerMenuClicked: () -> Unit,
    onMicClicked: () -> Unit,
    searchResults: List<String>, // todo: change type?
    hint: String = stringResource(R.string.default_search_bar_hint),
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var accessibilityAnnouncement by remember { mutableStateOf("") }

    val expandedAnnouncement = stringResource(R.string.search_expanded_announcement)
    val collapsedAnnouncement = stringResource(R.string.search_collapsed_announcement)

    // Update accessibility announcement when isExpanded changes
    LaunchedEffect(isExpanded) {
        accessibilityAnnouncement = if (isExpanded) expandedAnnouncement else collapsedAnnouncement
    }

    fun updateExpanded(expanded: Boolean) {
        isExpanded = expanded
        if (expanded) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    // Animate horizontal padding so the bar doesn't jankily widen before expanding
    val animatedPadding by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else 16.dp,
        label = "searchBarHorizontalPadding"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = animatedPadding)
    ) {
        // Accessibility announcement for screen readers (when the search bar is expanded or collapsed)
        if (accessibilityAnnouncement.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(0.dp)
                    .semantics { liveRegion = LiveRegionMode.Polite }
            ) {
                Text(text = accessibilityAnnouncement)
            }
        }
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    modifier = Modifier.focusRequester(focusRequester),
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        updateExpanded(false)
                    },
                    leadingIcon = {
                        if (isExpanded) {
                            IconButton(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .semantics { traversalIndex = 0f },
                                onClick = {
                                    textFieldState.clearText()
                                    updateExpanded(false)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_back_arrow),
                                    contentDescription = stringResource(R.string.search_close_and_go_back_description)
                                )
                            }
                        } else {
                            IconButton(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .semantics { traversalIndex = 0f },
                                onClick = onHamburgerMenuClicked,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_hamburger_menu),
                                    contentDescription = stringResource(R.string.navigation_menu_open_description)
                                )
                            }
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .semantics { traversalIndex = 2f },
                            onClick = onMicClicked,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_mic),
                                contentDescription = stringResource(R.string.voice_search_start_description)
                            )
                        }
                    },
                    expanded = isExpanded,
                    onExpandedChange = { updateExpanded(it) },
                    placeholder = { Text(text = hint) }
                )
            },
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            expanded = isExpanded,
            onExpandedChange = { updateExpanded(it) },
            modifier = modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
            ) {
                // todo: show search results
            }
        }
    }
}

@Preview(device = PIXEL_7_PRO, showSystemUi = true)
@Composable
fun PersistentSearchBarPreview() {
    val state = remember { TextFieldState() }
    PackupTheme {
        PersistentSearchBar(
            textFieldState = state,
            onSearch = {},
            onHamburgerMenuClicked = {},
            onMicClicked = {},
            searchResults = emptyList(),
            hint = "Describe an item to search..."
        )
    }
}