package com.example.acdajsonclase.ui.E3ContactosGson;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.acdajsonclase.R;
import com.example.acdajsonclase.network.RestClient;
import com.example.acdajsonclase.ui.E3ContactosGson.model.Contact;
import com.example.acdajsonclase.ui.E3ContactosGson.model.Person;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class ContactosGsonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String WEB = "https://portadaalta.mobi/acceso/contacts.json";
    private Button btnObtenerContactos;
    private ListView lwContactos;
    ArrayAdapter<Contact> adapter;
    Person person;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_gson);

        btnObtenerContactos = findViewById(R.id.btnObtenerContactos);
        btnObtenerContactos.setOnClickListener(this);
        lwContactos = findViewById(R.id.lwContactos);
        lwContactos.setOnItemClickListener(this);
        person = new Person();
    }

    @Override
    public void onClick(View view) {
        if (view == btnObtenerContactos) {
            descarga(WEB);
        }
    }

    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(WEB, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(true);
                progreso.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progreso.dismiss();
                showError("ERROR: "+statusCode+ "\n" + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Gson gson = new Gson();

                    person = gson.fromJson(response.toString(), Person.class);
                    mostrar();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR: "+e.getMessage(),Toast.LENGTH_LONG).show();

                }
                progreso.dismiss();

            }

        });
    }
    private void mostrar() {
        if (person != null) {

            if (adapter == null) {
                adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, person.getContacts());
                lwContactos.setAdapter(adapter);
            }
            else {
                adapter.clear();
                adapter.addAll(person.getContacts());
            }

        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Nombre: " + person.getContacts().get(position).getName(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Para mostrar errores hara un Toast y aparte mostrara el error en pantalla
     * @param message
     */
    public void showError(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
