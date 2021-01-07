package br.com.rentakelly

import br.com.rentakelly.models.Pull
import br.com.rentakelly.models.Repos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryService {
    @GET("search/repositories?q=language:Java&sort=stars")
    fun reposList(@Query("page") page: Int): Call<Repos>

    @GET("repos/{login}/{name}/pulls")
    fun pullsList(@Path("login") login: String,
                  @Path("name") name: String): Call<List<Pull>>



}