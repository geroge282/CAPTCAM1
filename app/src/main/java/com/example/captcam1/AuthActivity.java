package com.example.captcam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();


    }



    public void btnIngresar(View v){


        Intent intent=new Intent(v.getContext(), homeUsuario.class);
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