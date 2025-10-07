/*
 * Copyright (c) 2025 Ashwin Rao (github:ashwinravrao).
 *
 * Licensed under the GNU General Public License v3.0 or later (GPLv3+).
 * See the LICENSE file or <https://www.gnu.org/licenses/> for details.
 */

package com.ashwinrao.packup.feature.main.ui.composable

import android.text.TextUtils.replace
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.feature.common.R
import com.ashwinrao.packup.feature.common.theme.PackupTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBar(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    isVoiceSearchEnabled: Boolean = true,
    onVoiceSearch: () -> Unit = {},
    onTextSearch: (String) -> Unit = {},
    isExpanded: Boolean = false,
    onExpanded: (Boolean) -> Unit,
    results: List<Item>,
    hint: String = stringResource(R.string.default_search_bar_hint),
) {
    // Animate horizontal padding so the bar doesn't jankily widen before expanding
    val animatedPadding by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else 16.dp,
        label = "searchBarHorizontalPadding",
    )

    SearchBar(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(all = animatedPadding),
        inputField = {
            SearchBarDefaults.InputField(
                query = state.text.toString(),
                onQueryChange = { state.edit { replace(0, length, it) } },
                onSearch = {
                    onTextSearch(state.text.toString())
                    onExpanded(false)
                },
                leadingIcon = {
                    if (isExpanded) {
                        IconButton(
                            onClick = {
                                state.clearText()
                                onExpanded(false)
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back_arrow),
                                contentDescription = stringResource(R.string.search_close_and_go_back_description),
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search_bar_icon_description),
                        )
                    }
                },
                trailingIcon = {
                    if (isVoiceSearchEnabled) {
                        IconButton(
                            onClick = onVoiceSearch,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_mic),
                                contentDescription = stringResource(R.string.voice_search_start_description),
                            )
                        }
                    }
                },
                expanded = isExpanded,
                onExpandedChange = onExpanded,
                placeholder = { Text(text = hint) },
            )
        },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        expanded = isExpanded,
        onExpandedChange = onExpanded,
    ) {
        LazyColumn(modifier = modifier) {
            items(results.size) { index ->
                val item = results[index]
                item.name?.let {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = it,
                    )
                }
            }
        }
    }
}

@Preview(device = PIXEL_7_PRO, showSystemUi = true)
@Composable
private fun TopSearchBarPreview() {
    val state = remember { TextFieldState() }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    PackupTheme {
        SearchBar(
            state = state,
            onTextSearch = {},
            onVoiceSearch = {},
            isExpanded = isExpanded,
            onExpanded = { isExpanded = it },
            results = emptyList(),
            hint = "Describe an item to search...",
        )
    }
}
