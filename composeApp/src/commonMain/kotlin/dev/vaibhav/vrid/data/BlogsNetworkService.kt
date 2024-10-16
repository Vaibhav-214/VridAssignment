package dev.vaibhav.vrid.data

import dev.vaibhav.vrid.models.Blog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BlogsNetworkService(
    private val client: HttpClient
) {
    companion object {
        const val BASE_URL = "https://blog.vrid.in"
    }

    suspend fun getBlogs(page: Int, perPage: Int): List<Blog> {
        return client.get("wp-json/wp/v2/posts") {
           url {
               parameters.append("page", page.toString())
               parameters.append("per_page", perPage.toString())
           }
        }.body()
    }

}