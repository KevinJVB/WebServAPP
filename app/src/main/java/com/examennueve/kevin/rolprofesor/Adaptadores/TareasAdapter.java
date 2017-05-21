package com.examennueve.kevin.rolprofesor.Adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examennueve.kevin.rolprofesor.R;

import java.util.ArrayList;

/**
 * Created by kevin on 12/05/2017.
 */

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasHolder> {

    private Activity activity;
    private ArrayList<Tareas> tareasList;

    public TareasAdapter(Activity activity, ArrayList<Tareas> tareasList) {
        this.activity = activity;
        this.tareasList = tareasList;
    }

    @Override
    public TareasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.plantilla_tareas, parent, false);
        TareasHolder holder = new TareasHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(TareasHolder holder, int position) {

        holder.nombre.setText(tareasList.get(position).getNombre());
        holder.nota.setText(tareasList.get(position).getNota());
        holder.materia.setText("ID asignatura: "+tareasList.get(position).getIdasignatura());
        holder.alumno.setText("Id Alumno: "+tareasList.get(position).getIdusuario_alumn());
    }

    @Override
    public int getItemCount() {
        return tareasList.size();
    }

    public class TareasHolder extends RecyclerView.ViewHolder{

        TextView alumno,nombre,materia,nota;

        public TareasHolder(View itemView) {
            super(itemView);
            alumno = (TextView)itemView.findViewById(R.id.txtView_nameAlumnoTarea);
            nombre = (TextView)itemView.findViewById(R.id.txtView_NombreTarea);
            materia = (TextView)itemView.findViewById(R.id.textView_materia);
            nota = (TextView)itemView.findViewById(R.id.textView_nota);
        }
    }
}
