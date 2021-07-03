package ump.ti.ump.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ump.ti.ump.DetailInfoAct;
import ump.ti.ump.EditInformasiAct;
import ump.ti.ump.EditJurusanAct;
import ump.ti.ump.InformasiAct;
import ump.ti.ump.MahasiswaAct;
import ump.ti.ump.R;
import ump.ti.ump.model.Informasi;
import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Mahasiswa;

public class InformasiAdapter extends RecyclerView.Adapter<InformasiAdapter.ViewHolder> {

    private ArrayList<Informasi> informasis;
    private Context context;
    FirebaseDataListener listener;

    public InformasiAdapter(ArrayList<Informasi> informasi, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        informasis = informasi;
        context = ctx;
        listener = (InformasiAct)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView judul, tanggal, content, no;
        ImageView image;
        LinearLayout ln;

        ViewHolder(View v) {
            super(v);
            judul = (TextView) v.findViewById(R.id.judul);
            tanggal = (TextView) v.findViewById(R.id.tanggal);
            content = (TextView) v.findViewById(R.id.content);
            image =  v.findViewById(R.id.gambar);
            ln = v.findViewById(R.id.ln);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_informasi, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final Informasi informasi = informasis.get(position);

        holder.judul.setText(informasi.getJudul());
        holder.content.setText(informasi.getJudul());
        holder.tanggal.setText(informasi.getTanggal());
        Picasso.get()
                .load(informasi.getImage())
                .placeholder(R.drawable.empty)
                .fit()
                .centerCrop()
                .into(holder.image);
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailInfoAct.class);
                i.putExtra("key",informasi.getKey());
                context.startActivity(i);
            }
        });
        holder.ln.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.ln);
                //inflating menu from xml resource
                popup.inflate(R.menu.informasi_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle menu1 click
                                Intent intent = new Intent(context, EditInformasiAct.class);
                                intent.putExtra("key",informasi.getKey());
//                                intent.putExtra("txtNama",mahasiswa.getKey());
//                                intent.putExtra("jenkel",mahasiswa.getKey());
//                                intent.putExtra("tvJurusan",mahasiswa.getKey());
//                                intent.putExtra("tvKewarganegaraan",mahasiswa.getKey());
//                                intent.putExtra("nim",mahasiswa.getKey());
//                                intent.putExtra("nim",mahasiswa.getKey());
                                context.startActivity(intent);
                                return true;
                            case R.id.delete:
                                //handle menu2 click
                                listener.onDeleteData(informasi, position);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return informasis.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Informasi informasi, int position);
    }
}
