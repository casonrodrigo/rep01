package br.com.testeti.testeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.testeti.testeapp.R;
import br.com.testeti.testeapp.util.gps.LocationService;
import br.com.testeti.testeapp.sqlite.Helper;
import br.com.testeti.testeapp.sqlite.TesteManager;
import br.com.testeti.testeapp.sqlite.TesteObject;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 10;

    private static final int ACTIVITY_CODE_CAMERA = 20;

    @ViewById
    Toolbar toolbar;

    private Helper db;
    private TesteManager objManager;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private double lat = 0.0;
    private double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_main);

        db = new Helper(this);
        objManager = new TesteManager(db, this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationService(this);

        int hasGPSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasGPSPermission != PackageManager.PERMISSION_GRANTED || hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        if (locationManager != null) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, ACTIVITY_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) throws SecurityException {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                    if (locationManager != null) {
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                    }

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, ACTIVITY_CODE_CAMERA);
                }else{
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_CODE_CAMERA:
                if (resultCode == RESULT_OK) {
                    // Handle successful scan
                    String conteudo = data.getStringExtra("SCAN_RESULT");
                    String formato = data.getStringExtra("SCAN_RESULT_FORMAT");

                    TesteObject objTeste = new TesteObject();
                    objTeste.setTesteQrCode(conteudo);
                    objTeste.setTesteLat(lat);
                    objTeste.setTesteLng(lng);

                    objManager.Salvar(objTeste);

                    Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show();

                } else if (resultCode == RESULT_CANCELED) {
                    // Handle cancel
                }
                break;
        }
    }
}
