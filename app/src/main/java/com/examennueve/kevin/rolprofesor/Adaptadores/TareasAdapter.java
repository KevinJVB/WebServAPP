package com.examennueve.kevin.rolprofesor.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.examennueve.kevin.rolprofesor.Fragmentos.tareas;
import com.examennueve.kevin.rolprofesor.Main2Activity;
import com.examennueve.kevin.rolprofesor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevin on 12/05/2017.
 */

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasHolder> {

    private Activity activity;
    private ArrayList<Tareas> tareasList = new ArrayList<>();;

    private ArrayList<String> nombreAsigna;
    private ArrayList<String> codAsigna;
    ArrayList<String> nombreEstu;
    ArrayList<String> codEstu;
    String materiaSeleccionada, estudianteSeleccionado, idmateriaSeleccionada, idestudianteSeleccionado;

    public TareasAdapter(Activity activity, ArrayList<Tareas> tareasList) {
        this.activity = activity;
        this.tareasList = tareasList;
    }

    @Override
    public TareasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.plantilla_tareas, parent, false);
        TareasHolder holder = new TareasHolder(view);
        obtenerMaterias();
        obtenerEstudiantes();
        return holder;
    }

    @Override
    public void onBindViewHolder(TareasHolder holder, int position) {

        holder.nombre.setText(tareasList.get(position).getNombre());
        holder.nota.setText(tareasList.get(position).getNota());
        holder.materia.setText("ID asignatura: "+tareasList.get(position).getIdasignatura());
        holder.alumno.setText("Id Alumno: "+tareasList.get(position).getIdusuario_alumn());
        holder.idtarea.setText(tareasList.get(position).getIdtarea());

        final Tareas mTareas = tareasList.get(position);


        holder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodo_editar(mTareas.getIdtarea(),mTareas.getNombre(), mTareas.getNota());
            }
        });

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tareasList.size();
    }

    public class TareasHolder extends RecyclerView.ViewHolder{

        TextView alumno,nombre,materia,nota,idtarea;
        ImageButton btn_editar, btn_eliminar;
        public TareasHolder(View itemView) {
            super(itemView);
            alumno = (TextView)itemView.findViewById(R.id.txtView_nameAlumnoTarea);
            nombre = (TextView)itemView.findViewById(R.id.txtView_NombreTarea);
            materia = (TextView)itemView.findViewById(R.id.textView_materia);
            nota = (TextView)itemView.findViewById(R.id.textView_nota);
            btn_editar = (ImageButton)itemView.findViewById(R.id.imageButton_edit);
            btn_eliminar = (ImageButton)itemView.findViewById(R.id.imageButton_delete);
            idtarea = (TextView)itemView.findViewById(R.id.txtidTarea);
        }
    }

    //********************************************************************************************//
    //-----------------------------METODOS DE LOS BOTOONES DEL ITEM**********************************
    //********************************************************************************************//

    private void metodo_editar(final String id, String nombre, String nota){

        //Construye el Dialogo
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialogo_add_tarea, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        //Casteo de los EditTExt
        final EditText nombreTareaEdit = (EditText)dialogView.findViewById(R.id.editText_nombreTarea);
        final EditText notaEdit  = (EditText)dialogView.findViewById(R.id.editText_nota);

        //Setear EditText
        nombreTareaEdit.setText(nombre);
        notaEdit.setText(nota);

        //////////////SPINER ASIGNATURAS//////////////////////

        final Spinner spinnerAsignatura = (Spinner)dialogView.findViewById(R.id.spinner_asignatura);

        //Creando el adaptador
         ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (activity, R.layout.support_simple_spinner_dropdown_item, nombreAsigna);

        //Setear el adapter creado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAsignatura.setAdapter(adapter);
        spinnerAsignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                materiaSeleccionada = spinnerAsignatura.getSelectedItem().toString();
                idmateriaSeleccionada = codAsigna.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////SPINER ALUMNOS//////////////////////
        final Spinner spinnerEstudiantes = (Spinner)dialogView.findViewById(R.id.spinner_alumno);

        //Adaptador del Spinner para mostrar las asignaturas
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (activity, R.layout.support_simple_spinner_dropdown_item, nombreEstu);

        Toast.makeText(activity,
                adapter2.toString(),
                Toast.LENGTH_SHORT).show();

        //Setear el adapter creado
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstudiantes.setAdapter(adapter2);
        spinnerEstudiantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estudianteSeleccionado = spinnerEstudiantes.getSelectedItem().toString();
                idestudianteSeleccionado = codEstu.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////BOTONES DEL DIALOGO//////////////////////
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actualizarTarea(id,nombreTareaEdit.getText().toString(),notaEdit.getText().toString(),idmateriaSeleccionada,idestudianteSeleccionado);
            }
        });

        builder.create().show();
    }



    private void obtenerMaterias(){
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url2 = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/asignaturas/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url2,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        deserializarJSONArray2(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,
                                "Error al realizar la petición\n" + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void deserializarJSONArray2(JSONArray jsonArray){

        nombreAsigna = new ArrayList<>();
        codAsigna = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                nombreAsigna.add(jsonArray.getJSONObject(i).getString("nombre_asignatura"));
                codAsigna.add(jsonArray.getJSONObject(i).getString("id_asignatura"));
            }
        }catch (JSONException e){}
    }

    private void obtenerEstudiantes(){
        RequestQueue queue = Volley.newRequestQueue(activity);
        String urlE = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/usuarios/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlE,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        deserializarJSONArray3(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,
                                "Error al realizar la petición\n" + error.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(jsonArrayRequest);

    }

    private void deserializarJSONArray3(JSONArray jsonArray){

        nombreEstu = new ArrayList<>();
        codEstu = new ArrayList<>();
        String rol = "est";

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);

                if (rol.equals(item.getString("rol"))){
                    nombreEstu.add(jsonArray.getJSONObject(i).getString("nombre"));
                    codEstu.add(jsonArray.getJSONObject(i).getString("id_usuario"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualizarTarea(String id, String nombreTarea, String nota, String materia, final String alumno){

        JSONObject jsonObject = new JSONObject();
        try{
           jsonObject.put("idusuario_prof",id).put("nombre",nombreTarea).put("nota",nota).put("idusuario_alum",alumno)
                   .put("idasignatura",materia);
        }catch (JSONException e){}


        String url = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/tareas/";
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT , url+id, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity,"Modificado correctamente",Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);
    }


}
