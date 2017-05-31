package com.examennueve.kevin.rolprofesor.Fragmentos;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.examennueve.kevin.rolprofesor.Adaptadores.Asignatura;
import com.examennueve.kevin.rolprofesor.Adaptadores.Tareas;
import com.examennueve.kevin.rolprofesor.Adaptadores.TareasAdapter;
import com.examennueve.kevin.rolprofesor.Adaptadores.UsuariosAdapter;
import com.examennueve.kevin.rolprofesor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class tareas extends Fragment {


    private ArrayList<Tareas> tareasList = new ArrayList<>();
    private String url = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/tareas/";
    private ArrayList<String> nombreAsigna;
    ArrayList<String> codAsigna;
    ArrayList<String> nombreEstu;
    ArrayList<String> codEstu;
    String materiaSeleccionada, estudianteSeleccionado, idmateriaSeleccionada, idestudianteSeleccionado;


    EditText TnombreTarea;
    EditText TnotaTarea;
    TareasAdapter adapterRecycler;
    String idprofesor;
    public tareas() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        Bundle b = getArguments();
        idprofesor = b.getString("llaveIdProfe");

        //RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewTareas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        adapterRecycler = new TareasAdapter(getActivity(), tareasList,idprofesor);
        recyclerView.setAdapter(adapterRecycler);



        //Dialogo y JSON para llamar a desrealizarlo y mostrarlo
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Por favor espere...");
        dialog.show();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        deserializarJSONArray(response);
                        adapterRecycler.notifyDataSetChanged();
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getContext(), "Error al realizar la petición\n" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });
        queue.add(jsonArrayRequest);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo();
            }
        });

        obtenerMaterias(); //Obtengo las materias para el dialogo de agregar tarea
        obtenerEstudiantes(); //Obtengo los estudiantes para el dialogo de agregar tarea
        return view;

    }

    public void deserializarJSONArray(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);
                Tareas tarea = new Tareas();
                tarea.setIdtarea(item.getString("idtarea"));
                tarea.setNombre(item.getString("nombre"));
                tarea.setNota(item.getString("nota"));
                tarea.setIdasignatura(item.getString("idasignatura"));
                tarea.setIdusuario_alumn(item.getString("idusuario_alum"));

                tareasList.add(tarea);
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error al procesar la respuesta de la petición TAREA",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dialogo(){

        //Creo la vista para mostrar el dialogo
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialogo_add_tarea, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        //Casteo del Sppiner Asignaturas
        final Spinner spinnerAsignatura = (Spinner)dialogView.findViewById(R.id.spinner_asignatura);
        //Adaptador del Spinner para mostrar las asignaturas
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), R.layout.support_simple_spinner_dropdown_item, nombreAsigna);

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

        //Casteo del Sppiner Estudiantes
        final Spinner spinnerEstudiantes = (Spinner)dialogView.findViewById(R.id.spinner_alumno);

        //Adaptador del Spinner para mostrar las asignaturas
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (getActivity(), R.layout.support_simple_spinner_dropdown_item, nombreEstu);

        //Setear el adapter creado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        //Casteo de EditText
         TnombreTarea = (EditText)dialogView.findViewById(R.id.editText_nombreTarea);
         TnotaTarea = (EditText)dialogView.findViewById(R.id.editText_nota);

        //Botones del dialogo:
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final JSONObject jsonObject = new JSONObject();

                final String  nombreTarea= TnombreTarea.getText().toString();
                final String notaTarea = TnotaTarea.getText().toString();

                try{
                    jsonObject.put("idasignatura",idmateriaSeleccionada).put("idusuario_prof",idprofesor)
                            .put("idusuario_alum",idestudianteSeleccionado).put("nombre",nombreTarea).put("nota",notaTarea);
                }catch (JSONException e){e.printStackTrace();}

                RequestQueue queue = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getContext(),"Ok agregado correcamente",Toast.LENGTH_SHORT).show();
                                String idtarea=null;
                                try {
                                    idtarea = response.getString("idtarea");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Tareas tareas = new Tareas();
                                tareas.setIdtarea(idtarea);
                                tareas.setIdusuario_alumn(idestudianteSeleccionado);
                                tareas.setIdasignatura(idmateriaSeleccionada);
                                tareas.setNombre(nombreTarea);
                                tareas.setNota(notaTarea);
                                tareasList.add(tareas);
                                adapterRecycler.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error",String.valueOf(error));
                    }
                });
                queue.add(jsonObjectRequest);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void obtenerMaterias(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                        //Toast.makeText(getContext(), "Error al realizar la petición\n" + error.getMessage(), Toast.LENGTH_SHORT).show();

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
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                        Toast.makeText(getContext(),
                                "Error al realizar la petición OBTENER ESTUDIANTES\n" + error.getMessage(),
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

}
