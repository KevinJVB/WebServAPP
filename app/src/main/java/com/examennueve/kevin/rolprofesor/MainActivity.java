package com.examennueve.kevin.rolprofesor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText edit_usuario;
    EditText edit_password;
    String usuario,password;
    String idProfe = null;
    private String url = "http://192.168.1.8:8084/WebServiceExamNueve/webapi/usuarios/";


    //[{"id_usuario":1,"nombre":"Kevin","password":"123","rol":"est","username":"kevin"}]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edit_usuario = (EditText)findViewById(R.id.txt_nombre_user);
        edit_password = (EditText)findViewById(R.id.txt_nombre_pass);

    }

    public void iniciarSesion(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        if(!edit_usuario.getText().toString().isEmpty() || !edit_password.getText().toString().isEmpty()){

            usuario = edit_usuario.getText().toString();
            password = edit_password.getText().toString();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            deserializarJSONArray(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,
                                    "Error al realizar la petición\n"+error.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
            queue.add(jsonArrayRequest);

        }else{
            Toast.makeText(this,"Debe llenar todos los campos",Toast.LENGTH_SHORT).show();
        }

    }


    public void deserializarJSONArray(JSONArray jsonArray){
        String rol = "profe";
        boolean encontrado = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);



                if (usuario.equals(item.getString("username"))&& password.equals(item.getString("password"))
                        && rol.equals(item.getString("rol"))){
                    idProfe = String.valueOf(item.getString("id_usuario"));
                    encontrado = true;
                }


            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Error al procesar la respuesta de la petición",
                        Toast.LENGTH_SHORT).show();
            }
        }

            if(encontrado){
                Toast.makeText(this,"Correcto",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,Main2Activity.class);
                i.putExtra("llaveIdProfe",idProfe);
                startActivity(i);
            }
            if(encontrado==false){
                Toast.makeText(this,"Verifique su datos",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,idProfe,Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
