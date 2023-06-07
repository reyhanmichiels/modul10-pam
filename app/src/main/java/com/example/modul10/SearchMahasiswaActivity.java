package com.example.modul10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modul10.api.ApiConfig;
import com.example.modul10.model.AddMahasiswaResponse;
import com.example.modul10.model.DeleteMahasiswaResponse;
import com.example.modul10.model.Mahasiswa;
import com.example.modul10.model.MahasiswaResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMahasiswaActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtChecNrp;
    private Button btnSearch;
    private Button btnUpdate;
    private Button btnDelete;
    private ProgressBar progressBar;
    private TextView tvId;
    private TextView tvNrp;
    private TextView tvNama;
    private TextView tvEmail;
    private TextView tvJurusan;
    private List<Mahasiswa> mahasiswaList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mahasiswa);
        edtChecNrp = findViewById(R.id.edtChckNrp);
        btnSearch = findViewById(R.id.btnSearch);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        progressBar = findViewById(R.id.progressBar);
        tvId = findViewById(R.id.tvValId);
        tvNrp = findViewById(R.id.tvValNrp);
        tvNama = findViewById(R.id.tvValNama);
        tvEmail = findViewById(R.id.tvValEmail);
        tvJurusan = findViewById(R.id.tvValJurusan);
        mahasiswaList = new ArrayList<>();

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                String nrp = edtChecNrp.getText().toString();
                if (nrp.isEmpty()){
                    edtChecNrp.setError("Silahakan Isi nrp terlebih dahulu");
                    showLoading(false);
                }else{
                    Call<MahasiswaResponse> client = ApiConfig.getApiService().getMahasiswa(nrp);
                    client.enqueue(new Callback<MahasiswaResponse>() {
                        @Override
                        public void
                        onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null){
                                    showLoading(false);
                                    mahasiswaList = response.body().getData();
                                    setData(mahasiswaList);
                                    btnUpdate.setVisibility(View.VISIBLE);
                                    btnDelete.setVisibility(View.VISIBLE);


                                }
                            } else {
                                if (response.body() != null)
                                {
                                    Log.e("", "onFailure: " + response.message());
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                            showLoading(false);
                            Log.e("Error Retrofit", "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }



    private void setData(List<Mahasiswa> mahasiswaList) {
        tvId.setText(mahasiswaList.get(0).getId());
        tvNrp.setText(mahasiswaList.get(0).getNrp());
        tvNama.setText(mahasiswaList.get(0).getNama());
        tvEmail.setText(mahasiswaList.get(0).getEmail());
        tvJurusan.setText(mahasiswaList.get(0).getJurusan());
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnUpdate.getId()) {
            update();
        } else if (v.getId() == btnDelete.getId()) {
            delete();
        }
    }

    private void update() {
        Intent it = new Intent(SearchMahasiswaActivity.this, UpdateMahasiswaActivity.class);
        it.putExtra("idMahasiswa", mahasiswaList.get(0).getId());
        startActivity(it);
    }

    private void delete() {
        Call<DeleteMahasiswaResponse> client = ApiConfig.getApiService().deleteMahasiswa(mahasiswaList.get(0).getId());
        client.enqueue(new Callback<DeleteMahasiswaResponse>() {
            @Override
            public void onResponse(Call<DeleteMahasiswaResponse> call, Response<DeleteMahasiswaResponse> response) {
                showLoading(false);
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(SearchMahasiswaActivity.this, "Berhasil Menghapus data silahakan cek data pada halaman list!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.body() != null) {
                        Log.e("", "onFailure: " + response.body().getMessage());
                    }
                }
            }
            @Override
            public void
            onFailure(Call<DeleteMahasiswaResponse> call, Throwable t) {
                showLoading(false);
                Log.e("Error Retrofit", "onFailure: " + t.getMessage());
            }
        });
        Intent it = new Intent(SearchMahasiswaActivity.this, MainActivity.class);
        startActivity(it);
    }
}
