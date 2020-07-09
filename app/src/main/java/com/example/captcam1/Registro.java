package com.example.captcam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    private Button btnGuardar;
    private Button btnRegresar;
    private TextView txtNombre;
    private TextView txtcorreo;
    private TextView txtPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pbProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNombre= findViewById(R.id.edNombre);
        txtcorreo=findViewById(R.id.edCorreo);
        txtPassword=findViewById(R.id.edPassword);
        btnGuardar=findViewById(R.id.btnGuardar);
        btnRegresar=findViewById(R.id.btnRegresar);
        mAuth=FirebaseAuth.getInstance();
        pbProgreso=new ProgressDialog(this);
        pbProgreso.setIndeterminate(true);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();

            }


        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin();

            }


        });
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=mAuth.getCurrentUser();
                if(user==null){
                    abrirHomeUsuario();
                }
            }
        };
    }

    private void abrirHomeUsuario() {
        Intent i =new Intent(this, homeUsuario.class);
        startActivity(i);
        finish();
    }

    private void abrirLogin() {
        Intent i =new Intent(this, AuthActivity.class);
        startActivity(i);
        finish();
    }

    private void registrarUsuario() {
        final String email= txtcorreo.getText().toString();
        final String password= txtPassword.getText().toString();
        String usuario= txtNombre.getText().toString();
        if(!email.isEmpty() && !password.isEmpty() && !usuario.isEmpty()){
            pbProgreso.setMessage("Registrando Usuario");
            pbProgreso.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this,"Se ha registrado el usuario",Toast.LENGTH_LONG).show();
                      //  abrirHomeUsuario();
                    }else
                    {
                        Toast.makeText(Registro.this,"No se ha registrado el usuario",Toast.LENGTH_LONG).show();
                        onStop();
                    }
                    pbProgreso.dismiss();
                }
            });
        }//else
    }


}




