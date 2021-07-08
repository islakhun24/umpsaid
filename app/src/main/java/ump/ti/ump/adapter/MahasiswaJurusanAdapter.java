package ump.ti.ump.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ump.ti.ump.DetailMahasiswaAct;
import ump.ti.ump.EditMahasiswaEditAct;
import ump.ti.ump.MahasiswaAct;
import ump.ti.ump.R;
import ump.ti.ump.model.Mahasiswa;

public class MahasiswaJurusanAdapter extends RecyclerView.Adapter<MahasiswaJurusanAdapter.ViewHolder> {

    private ArrayList<Mahasiswa> mahasiswas;
    private Context context;

    public MahasiswaJurusanAdapter(ArrayList<Mahasiswa> mahasiswaArrayList, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        mahasiswas = mahasiswaArrayList;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvJurusan, tvKewarganegaraan, jenkel, nim, txtNama;
        ImageView img;
        RelativeLayout rl;
        ViewHolder(View v) {
            super(v);
            tvJurusan = (TextView) v.findViewById(R.id.tvJurusan);
            tvKewarganegaraan = (TextView) v.findViewById(R.id.tvKewarganegaraan);
            jenkel = (TextView) v.findViewById(R.id.jenkel);
            nim = (TextView) v.findViewById(R.id.nim);
            txtNama = (TextView) v.findViewById(R.id.txtNama);
            img = (ImageView) v.findViewById(R.id.img);
            rl = (RelativeLayout) v.findViewById(R.id.rl);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final Mahasiswa mahasiswa = mahasiswas.get(position);

        holder.txtNama.setText(mahasiswa.getNama_mahasiswa());
        holder.jenkel.setText(mahasiswa.getJenis_kelamin());
        holder.tvJurusan.setText(mahasiswa.getJurusan());
        holder.tvKewarganegaraan.setText(mahasiswa.getKewarganegaraan());
        holder.nim.setText(mahasiswa.getNim());
        Picasso.get()
                .load(mahasiswa.getPhoto())
                .error( R.drawable.img )
                .placeholder( R.drawable.img )
                .into(holder.img);

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailMahasiswaAct.class);
                i.putExtra("key",mahasiswa.getKey());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return mahasiswas.size();
    }

}
