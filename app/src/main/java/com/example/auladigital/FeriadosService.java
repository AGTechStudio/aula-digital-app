package com.example.auladigital;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface FeriadosService {
    @GET("feriados/{anio}")
    Call<List<Feriado>> getFeriados(@Path("anio") int anio);
}

