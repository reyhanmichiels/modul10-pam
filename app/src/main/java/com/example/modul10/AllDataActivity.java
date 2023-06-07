package com.example.modul10;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modul10.api.ApiConfig;
import com.example.modul10.model.AllMahasiswaResponse;
import com.example.modul10.model.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDataActivity extends AppCompatActivity {
    List<Mahasiswa> listMahasiswa = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);

        recyclerView = findViewById(R.id.rv_data);

        Call<AllMahasiswaResponse> client = ApiConfig.getApiService().getAllMahasiswa();
        client.enqueue(new Callback<AllMahasiswaResponse>() {
            @Override
            public void
            onResponse(Call<AllMahasiswaResponse> call, Response<AllMahasiswaResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        listMahasiswa = response.body().getData();

                        MahasiswaAdapter adapter = new MahasiswaAdapter(listMahasiswa);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AllDataActivity.this));

                        Toast.makeText(AllDataActivity.this, "Succeed",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.body() != null)
                    {
                        Log.e("", "onFailure: " + response.message());
                        Toast.makeText(AllDataActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<AllMahasiswaResponse> call, Throwable t) {
                Log.e("Error Retrofit", "onFailure: " + t.getMessage());
            }
        });


    }
}
