package ump.ti.ump.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ump.ti.ump.EditJurusanAct;
import ump.ti.ump.EditMahasiswaEditAct;
import ump.ti.ump.JurusanAct;
import ump.ti.ump.MahasiswaAct;
import ump.ti.ump.MahasiswaJurusanAct;
import ump.ti.ump.R;
import ump.ti.ump.model.Jurusan;
import ump.ti.ump.model.Mahasiswa;

public class JurusanAdapter extends RecyclerView.Adapter<JurusanAdapter.ViewHolder> {

    private ArrayList<Jurusan> jurusanss;
    private Context context;
    FirebaseDataListener listener;

    public JurusanAdapter(ArrayList<Jurusan> jurusans, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        jurusanss = jurusans;
        context = ctx;
        listener = (JurusanAct)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle, no;
        LinearLayout rl;
        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvJurusan);
            rl = (LinearLayout) v.findViewById(R.id.ln);
            no = (TextView) v.findViewById(R.id.no);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jurusan, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String name = jurusanss.get(position).getJurusan();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MahasiswaJurusanAct.class);
                intent.putExtra("title",name);
                context.startActivity(intent);
            }
        });

        holder.rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.rl);
                //inflating menu from xml resource
                popup.inflate(R.menu.informasi_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //handle menu1 click
                                Intent intent = new Intent(context, EditJurusanAct.class);
                                intent.putExtra("key",jurusanss.get(position).getKey());
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
//                                Toast.makeText(context,mahasiswa.getKey(),Toast.LENGTH_LONG).show();
//                                Log.d("stg", "onMenuItemClick: "+jurusanss.get(position).getKey());
                                listener.onDeleteData(jurusanss.get(position), position);
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


        holder.no.setText(String.valueOf(position+1));
        holder.tvTitle.setText(name);
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return jurusanss.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Jurusan jurusan, int position);
    }
}
