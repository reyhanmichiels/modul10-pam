package com.example.modul10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modul10.api.ApiConfig;
import com.example.modul10.model.AddMahasiswaResponse;
import com.example.modul10.model.UpdateMahasiswaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMahasiswaActivity extends AppCompatActivity {
    String mahasiswaId;
    EditText updateNrp, updateName, updateEmail, updateJurusan;
    Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mahasiswa);

        updateNrp = findViewById(R.id.updateNrp);
        updateName = findViewById(R.id.updateName);
        updateEmail = findViewById(R.id.updateEmail);
        updateJurusan = findViewById(R.id.updateJurusan);
        btnUpdate = findViewById(R.id.btnUpdate);
        mahasiswaId = getIntent().getStringExtra("idMahasiswa");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void update() {
        if (updateNrp.getText().toString().isEmpty() || updateName.getText().toString().isEmpty() || updateEmail.getText().toString().isEmpty() || updateJurusan.getText().toString().isEmpty()){
            Toast.makeText(UpdateMahasiswaActivity.this, "Silahkan lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
        }else {
            Call<UpdateMahasiswaResponse> client = ApiConfig.getApiService().updateMahasiswa(mahasiswaId ,updateNrp.getText().toString(), updateName.getText().toString(), updateEmail.getText().toString(), updateJurusan.getText().toString());
            client.enqueue(new Callback<UpdateMahasiswaResponse>() {
                @Override
                public void onResponse(Call<UpdateMahasiswaResponse> call, Response<UpdateMahasiswaResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            Toast.makeText(UpdateMahasiswaActivity.this, "Berhasil mengupdate data silahakan cek data pada halaman list!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (response.body() != null) {
                            Log.e("", "onFailure: " + response.body().getMessage());
                        }
                    }
                }
                @Override
                public void
                onFailure(Call<UpdateMahasiswaResponse> call, Throwable t) {
                    Log.e("Error Retrofit", "onFailure: " + t.getMessage());
                }
            });
            Intent it = new Intent(UpdateMahasiswaActivity.this, MainActivity.class);
            startActivity(it);
        }
    }
}
