package dev.vaibhav.vrid

import androidx.lifecycle.viewmodel.compose.viewModel
import dev.vaibhav.vrid.data.BlogsNetworkService
import dev.vaibhav.vrid.data.BlogsRepository
import dev.vaibhav.vrid.data.BlogsRepositoryImpl
import dev.vaibhav.vrid.presentation.BlogListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val mainModule = module {
    single {
        HttpClient {
            defaultRequest {
                url(BlogsNetworkService.BASE_URL)
                contentType(ContentType.Application.Json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
    single<BlogsRepository> {
        BlogsRepositoryImpl(get())
    }

    single {
        BlogsNetworkService(get())
    }
    viewModel {
        BlogListViewModel(get())
    }

}

fun initKoin() = startKoin {
    modules(mainModule)
}