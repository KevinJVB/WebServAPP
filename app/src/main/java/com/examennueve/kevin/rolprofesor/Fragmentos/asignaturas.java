package com.examennueve.kevin.rolprofesor.Fragmentos;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.examennueve.kevin.rolprofesor.Adaptadores.Asignatura;
import com.examennueve.kevin.rolprofesor.Adaptadores.AsignaturaAdapter;
import com.examennueve.kevin.rolprofesor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class asignaturas extends Fragment {

    private ArrayList<Asignatura> asignaturas = new ArrayList<>();
    private String url = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/asignaturas/";
    RecyclerView recyclerView;

    public asignaturas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_asignaturas, container, false);


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));

        final AsignaturaAdapter adapter = new AsignaturaAdapter(getActivity(), asignaturas);
        recyclerView.setAdapter(adapter);

        //Dialogo y JSON para llamar a desrealizarlo y mostrarlo
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Por favor espere...");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        deserializarJSONArray(response);
                        adapter.notifyDataSetChanged();
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),
                                "Error al realizar la petición\n"+error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });
        queue.add(jsonArrayRequest);

        return view;
    }


    public void deserializarJSONArray(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);
                Asignatura asignatura = new Asignatura();
                asignatura.setId_asignatura(item.getString("id_asignatura"));
                asignatura.setNombre_asignatura(item.getString("nombre_asignatura"));

                asignaturas.add(asignatura);
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error al procesar la respuesta de la petición",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
