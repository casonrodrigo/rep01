package br.com.testeti.testeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import br.com.testeti.testeapp.sqlite.Helper;
import br.com.testeti.testeapp.sqlite.TesteManager;
import br.com.testeti.testeapp.adapters.TesteAdapter;
import br.com.testeti.testeapp.sqlite.TesteObject;

public class ListaActivity extends AppCompatActivity {

    TesteAdapter adapter;

    private Helper db;
    private TesteManager objManager;

    ListView lstTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista);

        lstTeste = (ListView) findViewById(R.id.lstTeste);

        db = new Helper(this);
        objManager = new TesteManager(db, this);

        List<TesteObject> list = objManager.ConsultarTodos();

        adapter = new TesteAdapter(this, R.layout.item_teste, list);
        lstTeste.setAdapter(adapter);
    }
}
