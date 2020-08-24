package com.example.apptesteservice;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicoTeste extends Service {
    SensorManager mSensores;
    Sensor sAcelerometro;
    int sacudidasX=0, sacudidasY=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        mSensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        sAcelerometro = mSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensores.registerListener(
                new Acelerometro(),
                sAcelerometro,
                SensorManager.SENSOR_DELAY_GAME
        );

        return START_STICKY;
    }

    class Acelerometro implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            double x = sensorEvent.values[0];
            double y = sensorEvent.values[1];

            Message mensagem = new Message();
            Bundle pacote = new Bundle();
            pacote.putDouble("valorX", x);
            pacote.putDouble("valorY", y);
            mensagem.setData(pacote);
            MainActivity.correio.sendMessage(mensagem);

            if(x<-15||x>15){
                sacudidasX++;
                Log.i("MEUTESTE", "Sacudidas:" + sacudidasX + "  [" + x + "]");
            }
            if(y<-5||y>25){
                sacudidasY++;
            }

            if(sacudidasX>10){
                sacudidasX = 0;
                Intent iAbrir = getPackageManager().
                        getLaunchIntentForPackage("com.android.chrome");
                iAbrir.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iAbrir);
            }

            if(sacudidasY>10){
                sacudidasY = 0;
                Intent iAbrir = getPackageManager().
                        getLaunchIntentForPackage("com.google.android.youtube");
                iAbrir.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iAbrir);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}
