package com.sipro.mysipro.dewan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sipro.mysipro.R;

import java.util.ArrayList;

public class DewanAdapter extends RecyclerView.Adapter<DewanAdapter.DewanViewHolder> {


    private ArrayList<Dewan> dataList;

    public DewanAdapter(ArrayList<Dewan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public DewanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dewan, parent, false);
        return new DewanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DewanViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNamaDewan());
        holder.txtJabatan.setText(dataList.get(position).getJabatanDewan());
//        holder.txtNoHp.setText(dataList.get(position).getNohp());

        Glide.with(this).load(dataList.get(position).getImageUrl).into(holder.imgDewan);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DewanViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtJabatan;
        private ImageView imgDewan;

        public DewanViewHolder(View itemView) {
            super(itemView);
            txtNama     = (TextView) itemView.findViewById(R.id.txt_name_dewan_list);
            txtJabatan  = (TextView) itemView.findViewById(R.id.txt_jabatan_dewan_list);
            imgDewan    = (ImageView) itemView.findViewById(R.id.img_dewan_list);
        }
    }
}
