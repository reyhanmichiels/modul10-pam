package com.example.modul10.api;

import com.example.modul10.model.AddMahasiswaResponse;
import com.example.modul10.model.AllMahasiswaResponse;
import com.example.modul10.model.DeleteMahasiswaResponse;
import com.example.modul10.model.MahasiswaResponse;
import com.example.modul10.model.UpdateMahasiswaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswa(@Query("nrp") String nrp);

    @GET("mahasiswa")
    Call<AllMahasiswaResponse> getAllMahasiswa();

    @POST("mahasiswa")
    @FormUrlEncoded
    Call<AddMahasiswaResponse> addMahasiswa(
            @Field("nrp") String nrp,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jurusan") String jurusan
    );

    @PUT("mahasiswa")
    @FormUrlEncoded
    Call<UpdateMahasiswaResponse> updateMahasiswa(
            @Field("id") String id,
            @Field("nrp") String nrp,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jurusan") String jurusan
    );

    @HTTP(method = "DELETE", path = "mahasiswa", hasBody = true)
    @FormUrlEncoded
    Call<DeleteMahasiswaResponse> deleteMahasiswa(@Field("id") String id);
}
