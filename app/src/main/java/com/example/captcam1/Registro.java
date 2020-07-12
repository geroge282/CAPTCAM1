package com.example.captcam1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    private TextView txtNombre;
    private TextView txtcorreo;
    private TextView txtPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pbProgreso;
    ImageView Rostro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);




        txtNombre= findViewById(R.id.edNombre);
        txtcorreo=findViewById(R.id.edCorreo);
        txtPassword=findViewById(R.id.edPassword);
        Button btnGuardar1 = findViewById(R.id.btnGuardar);
        Button btnRegresar1 = findViewById(R.id.btnRegresar);

        mAuth=FirebaseAuth.getInstance();
        pbProgreso=new ProgressDialog(this);
        pbProgreso.setIndeterminate(true);
        btnGuardar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                registrarUsuario();


            }


        });
        btnRegresar1.setOnClickListener(new View.OnClickListener() {
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
        Rostro= findViewById(R.id.imageViewId);
        Button capturaRostro = findViewById(R.id.tomarFoto);
        capturaRostro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        Rostro.setImageBitmap(bitmap);
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
                        abrirHomeUsuario();
                    }else
                    {
                        Toast.makeText(Registro.this,"No se ha registrado el usuario",Toast.LENGTH_LONG).show();

                    }
                    pbProgreso.dismiss();
                }
            });
        }//else
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener!=null){
            mAuth.removeAuthStateListener(listener);
        } }
}




