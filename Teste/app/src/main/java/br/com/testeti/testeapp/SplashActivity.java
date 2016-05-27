package br.com.testeti.testeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import org.androidannotations.annotations.ViewById;

import br.com.testeti.testeapp.R;


/**
 * Created by rodrigo.cason on 27/05/2016.
 */

public class SplashActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Context mContext;

    @ViewById
    Button btnQrCode;
    Button btnVerLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        setContentView(R.layout.splash_activity);

        mContext = getApplicationContext();

        btnQrCode = (Button) findViewById(R.id.btnQrCode);
        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        btnVerLista = (Button) findViewById(R.id.btnVerLista);
        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListaActivity.class);
                startActivity(intent);
            }
        });
    }
}
