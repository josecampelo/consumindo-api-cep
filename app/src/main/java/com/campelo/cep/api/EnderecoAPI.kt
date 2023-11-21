package com.campelo.cep.api

import com.campelo.cep.model.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EnderecoAPI {

    @GET("ws/{cep}/json/")
    suspend fun recuperarCep(@Path("cep") cep: String) : Response<Endereco>
}