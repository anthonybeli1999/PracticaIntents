package com.example.practicaintents;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int CAMERAPIC_REQUEST = 1;
    private int contador=0;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final EditText edt_nombre = (EditText)findViewById(R.id.edt_nombre);
        final EditText edt_apellido = (EditText)findViewById(R.id.edt_apellido);
        final EditText edt_numero = (EditText)findViewById(R.id.edt_numero);
        final EditText edt_direccion = (EditText)findViewById(R.id.edt_direccion);
        final EditText edt_email = (EditText)findViewById(R.id.edt_email);
        Button btn_enviar = (Button)findViewById(R.id.btn_enviar);
        final ImageView img_camera = (ImageView)findViewById(R.id.img_camera);
        Button btn_camera = (Button)findViewById(R.id.btn_camara);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camaraIntent, CAMERAPIC_REQUEST);
            }
        });

        /* Enviar datos*/
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EnviarIntent = new Intent();
                if(contador == 0){
                    Toast.makeText(getApplicationContext(),"Debe tomar una foto",Toast.LENGTH_SHORT).show();
                }
                else {
                    Bitmap bitmap = ((BitmapDrawable)img_camera.getDrawable()).getBitmap();
                    EnviarIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    EnviarIntent.putExtra("nombre",edt_nombre.getText().toString());
                    EnviarIntent.putExtra("apellido",edt_apellido.getText().toString());
                    EnviarIntent.putExtra("celular",edt_numero.getText().toString());
                    EnviarIntent.putExtra("direccion",edt_direccion.getText().toString());
                    EnviarIntent.putExtra("email",edt_email.getText().toString());
                    EnviarIntent.putExtra("imagen",bitmap);
                    EnviarIntent.setType("text/plain");
                    if(EnviarIntent.resolveActivity(getPackageManager()) != null){
                        startActivity(Intent.createChooser(EnviarIntent, getResources().getText(R.string.chooser)));
                    }
                }

            }
        });
    }

    /* Metodo de la camara*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERAPIC_REQUEST){
            if(resultCode == RESULT_OK){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                ImageView img_camera = (ImageView)findViewById(R.id.img_camera);
                img_camera.setImageBitmap(bitmap);
                contador++;
            }
        }
    }
}
