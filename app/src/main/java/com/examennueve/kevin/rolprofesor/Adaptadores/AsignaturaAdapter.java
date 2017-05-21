package com.examennueve.kevin.rolprofesor.Adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examennueve.kevin.rolprofesor.R;

import java.util.ArrayList;

/**
 * Created by kevin on 2/05/2017.
 */

public class AsignaturaAdapter extends RecyclerView.Adapter<AsignaturaAdapter.AsginaturaHolder> {


    private Activity activity;
    private ArrayList<Asignatura> asignaturas;

    public AsignaturaAdapter(Activity activity, ArrayList<Asignatura> asignaturas) {
        this.activity = activity;
        this.asignaturas = asignaturas;
    }

    @Override
    public AsginaturaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.plantilla_asignaturas, parent, false);
        AsginaturaHolder holder = new AsginaturaHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AsginaturaHolder holder, int position) {

        holder.idasig.setText("ID: "+ asignaturas.get(position).getId_asignatura());
        holder.nomasig.setText("Asignatura: "+ asignaturas.get(position).getNombre_asignatura());

    }

    @Override
    public int getItemCount() {
        return asignaturas.size();
    }

    protected class AsginaturaHolder extends RecyclerView.ViewHolder{

        TextView idasig,nomasig;

        public AsginaturaHolder(View itemView) {
            super(itemView);
            idasig = (TextView)itemView.findViewById(R.id.txtView_Idasignatura);
            nomasig = (TextView)itemView.findViewById(R.id.txtView_asignatura);
        }
    }

}
