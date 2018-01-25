package com.example.acdajsonclase.utils;

import com.example.acdajsonclase.ui.contactos.Contacto;
import com.example.acdajsonclase.ui.contactos.Telefono;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by usuario on 23/01/18.
 */

public class Analisis {

    public static String analizarPrimitiva(JSONObject texto) throws JSONException {
        JSONArray jsonContenido;
        String tipo;
        JSONObject item;
        StringBuilder cadena = new StringBuilder();

        tipo = texto.getString("info");
        jsonContenido = new JSONArray(texto.getString("sorteo"));
        cadena.append("Sorteos de la Primitiva:" + "\n");

        for (int i = 0; i < jsonContenido.length(); i++) {
            item = jsonContenido.getJSONObject(i);
            cadena.append(tipo + ":" + item.getString("fecha") + "\n");
            cadena.append(
                    item.getInt("numero1") + "," +
                            item.getInt("numero2") + "," +
                            item.getInt("numero3") + "," +
                            item.getInt("numero4") + "," +
                            item.getInt("numero5") + "," +
                            item.getInt("numero6") + "\n"
            );
            cadena.append("Complementario: "+item.getInt("complementario")+", Reintegro: "+item.getInt("reintegro")+"\n");
        }
        return cadena.toString();
    }

    public static ArrayList<Contacto> analizarContactos(JSONObject respuesta) throws JSONException {
        JSONArray jAcontactos;
        JSONObject jOcontacto, jOtelefono;
        Contacto contacto;
        Telefono telefono;
        ArrayList<Contacto> personas = new ArrayList<>();
        // añadir contactos (en JSON) a personas



        return personas;
    }

}
