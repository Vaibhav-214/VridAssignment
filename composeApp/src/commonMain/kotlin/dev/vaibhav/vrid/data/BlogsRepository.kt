package dev.vaibhav.vrid.data

import dev.vaibhav.vrid.models.Blog

interface BlogsRepository {
    suspend fun getBlogs(page: Int, perPage: Int): ResponseState<List<Blog>>
}