package com.example.modul10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modul10.model.Mahasiswa;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private List<Mahasiswa> listMahasiswa;

    public MahasiswaAdapter(List<Mahasiswa> listMahasiswa) {
        this.listMahasiswa = listMahasiswa;
    }

    @NonNull
    @Override
    public MahasiswaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View mahasiswaView = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(mahasiswaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.ViewHolder holder, int position) {
        Mahasiswa mahasiswa = listMahasiswa.get(position);

        holder.id.setText(mahasiswa.getId());
        holder.nrp.setText(mahasiswa.getNrp());
        holder.nama.setText(mahasiswa.getNama());
        holder.email.setText(mahasiswa.getEmail());
        holder.jurusan.setText(mahasiswa.getJurusan());
    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, nrp, nama, email, jurusan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.allData_tvValId);
            nrp = itemView.findViewById(R.id.allData_tvValNrp);
            nama = itemView.findViewById(R.id.allData_tvValNama);
            email = itemView.findViewById(R.id.allData_tvValEmail);
            jurusan = itemView.findViewById(R.id.allData_tvValJurusan);
        }
    }
}
