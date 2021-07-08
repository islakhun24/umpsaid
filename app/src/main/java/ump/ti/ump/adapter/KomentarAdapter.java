package ump.ti.ump.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ump.ti.ump.DetailMahasiswaAct;
import ump.ti.ump.R;
import ump.ti.ump.model.Komentar;
import ump.ti.ump.model.Mahasiswa;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> {

    private ArrayList<Komentar> komentars;
    private Context context;

    public KomentarAdapter(ArrayList<Komentar> komentarArrayList, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        komentars = komentarArrayList;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvNama, tvKomentar;
        ViewHolder(View v) {
            super(v);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvKomentar = (TextView) v.findViewById(R.id.tvKomentar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_komentar, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final Komentar komentar = komentars.get(position);

        holder.tvNama.setText(komentar.getPengirim());
        holder.tvKomentar.setText(komentar.getKomentar());

    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return komentars.size();
    }

}
