package com.example.captcam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AuthActivity extends AppCompatActivity {
    TextView correoAuth;
    TextView passwordAuth;
    Button btnIngresar;
    Button btnSalir;
    Button btnRegistrar;
    FirebaseAuth mAuth;
    ProgressBar pbCarga;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
       correoAuth=findViewById(R.id.ptUsuario);
       passwordAuth=findViewById(R.id.ptPassword);
       btnIngresar=findViewById(R.id.btnIngresar);
        btnRegistrar=findViewById(R.id.btnRegistrar);
       btnSalir=findViewById(R.id.btnSalir);
       pbCarga=findViewById(R.id.pbCarga);
       pbCarga.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=mAuth.getCurrentUser();
                if(user==null){
                    Toast.makeText(getApplicationContext(),"CORRECTO",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"CORRECTO Y LOGUEADO",Toast.LENGTH_LONG).show();

                }
            }
        };



    }



    public void btnIngresar(View v){

ingresar();
        /*Intent intent=new Intent(v.getContext(), homeUsuario.class);
        startActivityForResult(intent,0);*/

    }

    private void ingresar() {
        String email =correoAuth.getText().toString();
        String password= passwordAuth.getText().toString();
        if (!email.isEmpty()&& !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"CORRECTO",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"INCORRECTO",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public void BotonRegistro(View v){

     /*   Intent intent=new Intent(v.getContext(), Registro.class);
        startActivityForResult(intent,0);*/
 //comentario
    }
    public void BotonSalir(View v){

        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
        }
    }
}