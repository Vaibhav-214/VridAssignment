package dev.vaibhav.vrid.data

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import dev.vaibhav.vrid.models.Blog
import io.ktor.utils.io.errors.IOException

class BlogsPagingSource(
    private val repository: BlogsRepository
) : PagingSource<Int, Blog>() {

    override fun getRefreshKey(state: PagingState<Int, Blog>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Blog> {
        val currentPage = params.key ?: 1
        val perPage = params.loadSize

        // Fetch data using the repository and ResponseState
        return when (val response = repository.getBlogs(currentPage, perPage)) {
            is ResponseState.Success -> {
                val blogs = response.data
                LoadResult.Page(
                    data = blogs,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (blogs.isEmpty()) null else currentPage + 1
                )
            }
            is ResponseState.NoInternet -> {
                LoadResult.Error(IOException("No internet connection"))
            }
            is ResponseState.ErrorMessage -> {
                LoadResult.Error(Exception(response.message))
            }
        }
        }
}