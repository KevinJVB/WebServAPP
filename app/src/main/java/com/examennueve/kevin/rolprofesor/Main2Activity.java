package com.examennueve.kevin.rolprofesor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.examennueve.kevin.rolprofesor.Adaptadores.Asignatura;
import com.examennueve.kevin.rolprofesor.Adaptadores.AsignaturaAdapter;
import com.examennueve.kevin.rolprofesor.Fragmentos.estudiantes;
import com.examennueve.kevin.rolprofesor.Fragmentos.tareas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Asignatura> asignaturas = new ArrayList<>();
    private String url = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/asignaturas/";
    //[{"id_asignatura":1,"nombre_asignatura":"Matematica"},{"id_asignatura":2,"nombre_asignatura":"Ciencias quimicas"}]
    Bundle args;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.asignaturas);

        args = new Bundle();
        args.putString("llaveIdProfe", getIntent().getExtras().getString("llaveIdProfe"));

        //Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Recycler View
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false));

        final AsignaturaAdapter adapter = new AsignaturaAdapter(this, asignaturas);
        recyclerView.setAdapter(adapter);

        //Dialogo y JSON para llamar a desrealizarlo y mostrarlo
        RequestQueue queue = Volley.newRequestQueue(this);
        final ProgressDialog dialog = new ProgressDialog(this);
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
                        Toast.makeText(getApplicationContext(),
                                "Error al realizar la petición\n"+error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });
        queue.add(jsonArrayRequest);

    }

    //Metodo desrealizar

    public void deserializarJSONArray(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);
                Asignatura asignatura = new Asignatura();
                asignatura.setId_asignatura(item.getString("id_asignatura"));
                asignatura.setNombre_asignatura(item.getString("nombre_asignatura"));

                asignaturas.add(asignatura);
            } catch (JSONException e) {
                Toast.makeText(this, "Error al procesar la respuesta de la petición",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        int id = item.getItemId();

        if (id == R.id.asignaturas) {
            Intent i = new Intent(this,Main2Activity.class);
            startActivity(i);
        } else if (id == R.id.estudiantes) {
            this.setTitle("Estudiantes");
            fragmentoGenerico = new estudiantes();

        } else if (id == R.id.tareas) {

            this.setTitle("Tareas");
            fragmentoGenerico = new tareas();
            fragmentoGenerico.setArguments(args);
        }

        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main2, fragmentoGenerico)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
