package com.isfoula.appblufinal;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ListeDeDispositif extends ListActivity{


    private BluetoothAdapter meuBluetoothAdapter2 = null;
    static String Abdelilah_PC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        meuBluetoothAdapter2= BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice>  dispositifAppariee = meuBluetoothAdapter2.getBondedDevices();
//ici l'icone qui va s'fficher il va nous donner les noms des dispo et leurs adresses .. ici j'ia remarqué qu'il affiche seulement qui sont deja connu par le tel
        if(dispositifAppariee.size() > 0 )
                {
                for(BluetoothDevice Dispositifs : dispositifAppariee){
                    String nomBtn = Dispositifs.getName();
                    String Macbtn = Dispositifs.getAddress();
                    ArrayBluetooth.add(nomBtn + "\n"  + Macbtn );

                }
        }
        setListAdapter(ArrayBluetooth);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//ici lorsqu'on clique sur un dispositif il nous donne ces info
        String InformationGeneral = ((TextView) v ).getText().toString();
      //  Toast.makeText(getApplicationContext(),"Info :" + InformationGeneral ,Toast.LENGTH_SHORT).show();

        String Adresse = InformationGeneral.substring(InformationGeneral.length() - 17 );
      //  Toast.makeText(getApplicationContext(),"Info: " + Adresse ,Toast.LENGTH_SHORT).show();

        Intent RetornaMac =new Intent();
        RetornaMac.putExtra(Abdelilah_PC, Adresse);
        setResult(RESULT_OK,RetornaMac);
        finish();
            }



    }
