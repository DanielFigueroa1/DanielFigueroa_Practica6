package com.example.danielfigueroa_practica6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.danielfigueroa_practica6.model.Usuario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText contrasena;
    private Button ingresar;
    private BufferedWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        ingresar = findViewById(R.id.ingresar);


        ingresar.setOnClickListener( //metodo onclick con parametro

                (v) -> {

                    Gson gson = new Gson(); //crea json


                    String nombreUsuario = usuario.getText().toString();//datos ingresados
                    String contrasenaUsuario = contrasena.getText().toString();

                    Usuario obj= new Usuario(nombreUsuario, contrasenaUsuario); //creo un objeto cordenada que serializo

                    String json = gson.toJson(obj);
                    //Log.e(">>>","funciono"+json);

                    new Thread(
                            ()->{ //metodo run del runable sin parametro
                                try { //cualquier elemento de network tiene que ir en un hilo
                                    /*Usar json que es un modelo de usuario, hacer una clase de usuario desde cliente y servidor que sean iguales*/
                                    //String registro = usuario.getText().toString() + "  " + contrasena.getText().toString(); //atrapar elementos

                                    writer.write(json + "\n"); //enviar el mensaje igual
                                    writer.flush(); //para que envie la info
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                    ).start();
                    Intent i = new Intent(this, ExitoActivity.class);
                    startActivity(i);
                }
        );

        new Thread( //generacion de hilo
                () -> {
                    try {
                        System.out.println("Enviando solicitud...");//todavia muy prematuro para pedir los datos

                        Socket socket = new Socket("10.0.2.2", 5000); //solicitud al server donde necesito que el server busque la ip y puerto
                        System.out.println("Conexion establecida");
                        //socket es como una puerta hacia el servidor
                        InputStream is = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();

                        /*BufferedWriter gracias a que la declare anteriormente de forma global*/
                        //globalizarlo para que funcione, necesto declarar el writer
                        writer = new BufferedWriter(new OutputStreamWriter(out));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is)); //declarando el lector para que lea mensajes del servidor

                        while (true) { //bucle infinito de lectura
                            System.out.println("Esperando datos...");
                            String line = reader.readLine(); //llegan los mensajes del servidor
                            System.out.println("Datos recibidos" + line);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        ).start();


    }

}