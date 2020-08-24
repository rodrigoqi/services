package com.example.apptesteservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnAtivar, btnDesativar;
    static TextView txtValores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtValores = findViewById(R.id.txtValores);
        btnAtivar = findViewById(R.id.btnAtivar);
        btnDesativar = findViewById(R.id.btnDesativar);

        btnAtivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ativarServicoAcelerometro();
            }
        });

        btnDesativar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desativarServicoAcelerometro();
            }
        });
    }

    private void ativarServicoAcelerometro(){
        Intent iServico = new Intent(this, ServicoTeste.class);
        startService(iServico);
    }

    private void desativarServicoAcelerometro(){
        Intent iServico = new Intent(this, ServicoTeste.class);
        stopService(iServico);
    }

    public static Handler correio = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Bundle pacoteRecebido = msg.getData();
            double x = pacoteRecebido.getDouble("valorX");
            double y = pacoteRecebido.getDouble("valorY");

            txtValores.setText("X: " + String.format("%.1f", x) + "\nY: " + String.format("%.1f", y));
        }
    };

}