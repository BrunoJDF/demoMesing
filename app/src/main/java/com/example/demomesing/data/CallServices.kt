package com.example.demomesing.data

import com.example.demomesing.model.*
import com.example.demomesing.model.Collection
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.HashMap

interface CallServices {
    @POST("authenticate/token")
    @FormUrlEncoded
    suspend fun loggin(@FieldMap param: Map<String, String>): ResponseData

    @POST("menu/lstMenu")
    @FormUrlEncoded
    suspend fun lstMain(@FieldMap param: Map<String, String>): Collection

    @POST("tipoServicio/servicioXUsuario")
    @FormUrlEncoded
    suspend fun getAllServices(@FieldMap param: Map<String, String>): ResponseService

    @POST("usuario/mntUsuario")
    @FormUrlEncoded
    suspend fun addUser(@FieldMap param: Map<String, String>): ResponseUsuario

    @POST("tipoServicio/asignarServicioAUsuario")
    @FormUrlEncoded
    suspend fun getCategorias(@FieldMap parameter: Map<String, String>): ResponseCategoria

    @POST("pregunta/lstPregunta")
    @FormUrlEncoded
    suspend fun getListQuestion(@FieldMap param: Map<String, String>): ResponsePregunta

    @POST("solicitud/mntSolicitud")
    @FormUrlEncoded
    suspend fun createSolicitude(@FieldMap param: Map<String, String>): ResponsePregunta

    @POST("solicitud/mntSolicitud")
    @FormUrlEncoded
    suspend fun getSolicitude(parameter: Map<String, String>): ResponseSolicitude
}
