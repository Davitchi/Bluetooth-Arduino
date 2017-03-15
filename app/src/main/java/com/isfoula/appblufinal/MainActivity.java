package com.isfoula.appblufinal;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
{
Button btn_Connect;
    private static final int  Activo_Bluetooth = 1 ; //var 1
    private static final int  connexio_Bluetooth = 2 ; // var 2
    private static String Ordinateur = null;

    BluetoothAdapter meuBluetoothAdapter = null ;
    BluetoothDevice meuDevice = null ;
    BluetoothSocket meuSocket = null ;

    UUID Meu_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");// faire appel à l'ensemble des regles protocole de communication

    boolean connexion=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Connect=(Button) findViewById(R.id.btn_connect);


        meuBluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
if(meuBluetoothAdapter==null)
  {  Toast.makeText(getApplicationContext(),"pas de bluetooth",Toast.LENGTH_SHORT).show();  }

else if (!meuBluetoothAdapter.isEnabled())
        {
    Intent ActiverBluetooth=  new Intent(meuBluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(ActiverBluetooth,Activo_Bluetooth);
        }


btn_Connect.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
       if(connexion){

     try {    // pas de connexion
        meuSocket.close();
        connexion= false;
        btn_Connect.setText("se connecter");
        Toast.makeText(getApplicationContext(),"bluetooth deconnecté",Toast.LENGTH_SHORT).show();
         }
           catch (IOException errore)
               {Toast.makeText(getApplicationContext(),"une erreur est survenu wa9ila 2" + errore,Toast.LENGTH_SHORT).show();    }

                    }  else {
   // connecté
    Intent Ouverture_liste= new Intent(MainActivity.this, ListeDeDispositif.class);
    startActivityForResult(Ouverture_liste,connexio_Bluetooth);
             }
                                 }
                                     });
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
     //   super.onActivityResult(requestCode, resultCode, data);

switch(requestCode){
    case Activo_Bluetooth:
    if(resultCode == Activity.RESULT_OK){
        Toast.makeText(getApplicationContext(),"bluetooth activé",Toast.LENGTH_SHORT).show();}
        else { Toast.makeText(getApplicationContext(),"connexion echoué, Traitement annullé",Toast.LENGTH_SHORT).show();
        finish(); }
        break;


    case connexio_Bluetooth:
        if(resultCode == Activity.RESULT_OK) {
            Ordinateur = data.getExtras().getString(ListeDeDispositif.Abdelilah_PC);
            //   Toast.makeText(getApplicationContext(),"ordinateut Final" + Ordinateur ,Toast.LENGTH_SHORT).show();
            meuDevice = meuBluetoothAdapter.getRemoteDevice(Ordinateur);

            try {
                meuSocket = meuDevice.createRfcommSocketToServiceRecord(Meu_UUID);
                meuSocket.connect();
                connexion =true;
                btn_Connect.setText("se deconnecter");
                Toast.makeText(getApplicationContext(),"connécté au peripherique d'adresse:  " + Ordinateur ,Toast.LENGTH_SHORT).show();

            }catch(IOException error){
                connexion = false ;
                Toast.makeText(getApplicationContext(),"une erreur est survenue: \n" + error ,Toast.LENGTH_SHORT).show();
        }
        }
        else
            Toast.makeText(getApplicationContext(),"hadi mafhemthach",Toast.LENGTH_SHORT).show();
                   }
      }
}

