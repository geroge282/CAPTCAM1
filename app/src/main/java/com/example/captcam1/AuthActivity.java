package com.example.captcam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
    final private int REQUEST_CODE_ASK_PERMISSION=111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitarpermisos();

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
                    //no esta logueado
                    Toast.makeText(getApplicationContext(),"Revise usuario o password",Toast.LENGTH_LONG).show();

                }
                else{

                    Toast.makeText(getApplicationContext(),"CORRECTO Y LOGUEADO",Toast.LENGTH_LONG).show();

                }
            }
        };



    }

    private void solicitarpermisos() {
        int permisoMemoria= ActivityCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoEscrituraMemoria= ActivityCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoCamara= ActivityCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.CAMERA);

        if(permisoEscrituraMemoria!= PackageManager.PERMISSION_GRANTED || permisoMemoria!=PackageManager.PERMISSION_GRANTED || permisoCamara!=PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSION);
            }
        }
    }

    private void abrirCuenta() {
        Intent i=new Intent(this, homeUsuario.class);
        startActivity(i);
        finish();
    }

    public void btnIngresar(View v){
    ingresar();
    }

    private void ingresar() {
        String email =correoAuth.getText().toString();
        String password= passwordAuth.getText().toString();
        if (!email.isEmpty()&& !password.isEmpty()){
            pbCarga.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"CORRECTO",Toast.LENGTH_LONG).show();
                        abrirCuenta();
                    }else{
                        Toast.makeText(getApplicationContext(),"INCORRECTO",Toast.LENGTH_LONG).show();

                    }
                    pbCarga.setVisibility(View.INVISIBLE);
                }
            });
        }
    }




    public void BotonRegistro(View v){


       Intent intent=new Intent(v.getContext(), Registro.class);
        startActivityForResult(intent,0);

    }



    public void BotonSalir(View v){

        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}