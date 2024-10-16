package dev.vaibhav.vrid.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import dev.vaibhav.vrid.AppNavigation
import dev.vaibhav.vrid.LocalNavigationProvider
import dev.vaibhav.vrid.models.Blog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogListScreen(
    lazyItems: LazyPagingItems<Blog>,
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Blog List") })
        }
    ) { paddingValues ->

        BlogList(
            modifier = Modifier.padding(paddingValues),
            blogs = lazyItems
        )

    }

}


@Composable
fun BlogItem(blog: Blog) {
    val navController = LocalNavigationProvider.current

    Surface (
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).clickable {
                navController.navigate(AppNavigation.WebView(blog.url ?: ""))
            }
        ) {
            blog.title.rendered?.let { it ->
                Text(text = it, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                blog.date?.let { date->
                    Text(text = "published on: ${convertDateString(date)}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }

}


@Composable
fun BlogList(
    modifier: Modifier = Modifier,
    blogs: LazyPagingItems<Blog>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(blogs.itemCount) { index ->
            val item = blogs[index]
            item?.let { BlogItem(it) }
        }

        blogs.apply {
            when {
                loadState.refresh is LoadStateLoading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.refresh is LoadStateNotLoading && blogs.itemCount < 1 -> {
                    item { Text("No blogs for now...") }
                }

                loadState.refresh is LoadStateError -> {
                    item {
                        Text("Error: ${(loadState.refresh as LoadStateError).error.message}")
                    }
                }

                loadState.append is LoadStateLoading -> {
                    item {
                        CircularProgressIndicator(
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(1f)
                                .padding(20.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                loadState.append is LoadStateError -> {
                    item {
                        Text("Error: ${(loadState.append as LoadStateError).error.message}")
                    }
                }
            }
        }
    }
}
