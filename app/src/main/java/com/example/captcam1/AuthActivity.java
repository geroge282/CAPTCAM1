package com.example.captcam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    public void btnIngresar(View v){

        Intent intent=new Intent(v.getContext(), usuario.class);
        startActivityForResult(intent,0);

    }
    public void BotonRegistro(View v){

        Intent intent=new Intent(v.getContext(), Registro.class);
        startActivityForResult(intent,0);
 //comentario
    }
    public void BotonSalir(View v){

        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}