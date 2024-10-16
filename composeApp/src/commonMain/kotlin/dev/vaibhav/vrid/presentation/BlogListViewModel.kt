package dev.vaibhav.vrid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.vaibhav.vrid.data.BlogsPagingSource
import dev.vaibhav.vrid.data.BlogsRepository
import dev.vaibhav.vrid.models.Blog
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class BlogListViewModel(
    private val repository: BlogsRepository
): ViewModel(), KoinComponent {

    val blogs: Flow<PagingData<Blog>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { BlogsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

}
