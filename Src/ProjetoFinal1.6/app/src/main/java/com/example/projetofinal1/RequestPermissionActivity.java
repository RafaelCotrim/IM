package com.example.projetofinal1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class RequestPermissionActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST = 99;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Boolean temTodasPermissoes = Boolean.TRUE;
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            for (int j = 0; j < grantResults.length; j++) {
                if (grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("LOCATION_DEBUG", "O utilizador deu a permissão: " + permissions[j]);
                } else {
                    temTodasPermissoes = Boolean.FALSE;
                    Log.d("LOCATION_DEBUG", "Ooopsss. O utilizador não deu a permissão: " + permissions[j]);
                }
            }
        }
    }

    protected boolean pedirPermissoesSeNecessario(String[] permissions) {
        Log.d("LOCATION_DEBUG", "pedirPermissoesSeNecessario");
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //A permissão ainda não tinha sido dada anteriormente
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), MY_PERMISSIONS_REQUEST);
            return Boolean.FALSE;
            //devolve FALSE se a app ainda não tiver todas as permissões
        } else
            return true; //devolve TRUE se a app não precisar de pedir permissões (porque já as tem)}
    }

    protected boolean requestStandard(){
        return pedirPermissoesSeNecessario(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
    }

}
