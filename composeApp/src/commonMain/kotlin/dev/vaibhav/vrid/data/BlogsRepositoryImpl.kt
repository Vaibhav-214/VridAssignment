package dev.vaibhav.vrid.data

import dev.vaibhav.vrid.models.Blog

class BlogsRepositoryImpl(
    private val networkService: BlogsNetworkService
): BlogsRepository {

    override suspend fun getBlogs(page: Int, perPage: Int): ResponseState<List<Blog>> {
        return getApiResponseState {
            networkService.getBlogs(page = page, perPage = perPage)
        }
    }

}