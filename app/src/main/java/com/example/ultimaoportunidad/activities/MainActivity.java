package com.example.ultimaoportunidad.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar el SessionManager
        session = new SessionManager(this);

        // Comprobar si el usuario tiene una sesión activa
        if (session.isLoggedIn()) {
            // Si hay una sesión activa, redirigir a GroupsActivity
            Intent intent = new Intent(MainActivity.this, com.example.ultimaoportunidad.activities.GroupsActivity.class);
            startActivity(intent);
        } else {
            // Si no hay sesión activa, redirigir a LoginActivity
            Intent intent = new Intent(MainActivity.this, com.example.ultimaoportunidad.activities.LoginActivity.class);
            startActivity(intent);
        }

        // Cerrar MainActivity para que no quede en la pila de actividades
        finish();
    }
}
