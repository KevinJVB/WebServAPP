package com.examennueve.kevin.rolprofesor.Adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examennueve.kevin.rolprofesor.R;

import java.util.List;

/**
 * Created by kevin on 12/05/2017.
 */

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuarioHolder> {

    private Activity activity;
    private List<Usuarios> usuariosList;

    public UsuariosAdapter(Activity activity, List<Usuarios> usuariosList) {
        this.activity = activity;
        this.usuariosList = usuariosList;
    }

    @Override
    public UsuarioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.plantilla_usuarios, parent, false);
        UsuarioHolder holder = new UsuarioHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UsuarioHolder holder, int position) {

        holder.idAlum.setText("ID: "+usuariosList.get(position).getId_usuario());
        holder.nomAlum.setText("ID: "+usuariosList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class UsuarioHolder extends RecyclerView.ViewHolder{

        TextView idAlum, nomAlum;

        public UsuarioHolder(View itemView) {
            super(itemView);
            idAlum = (TextView)itemView.findViewById(R.id.txtView_NombreTarea);
            nomAlum = (TextView)itemView.findViewById(R.id.txtView_nameAlumno);
        }
    }
}
