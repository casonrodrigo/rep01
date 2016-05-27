package br.com.testeti.testeapp.sqlite;

/**
 * Created by rodrigo.cason on 09/02/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TesteDAL {

    //Tabela
    public static final String NOME_TABELA = "teste";

    //Colunas
    public static final String QRCODE = "tiv_qrcode";
    public static final String LAT = "tiv_lat";
    public static final String LNG = "tiv_lng";

    public static final String ESTRUTURA_BANCO =
            "CREATE TABLE " + NOME_TABELA
                    + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QRCODE + " VARCHAR NOT NULL, "
                    + LAT + " DECIMAL(10, 8) NOT NULL, "
                    + LNG + " DECIMAL(10, 8) NOT NULL )";

    private static final String[] COLUNAS = { Helper.CHAVE_TABELA, QRCODE, LAT, LNG};
    private final Helper DataHelper;
    private final Context Contexto;

    public TesteDAL(Helper helper, Context contexto)
    {
        DataHelper = helper;
        Contexto = contexto;
    }

    public long Inserir(TesteObject objTesteManager) {
        ContentValues valores = TesteDAL.ToContentValues(objTesteManager);
        return DataHelper.Inserir(NOME_TABELA, valores);
    }

    public TesteObject Consultar()
    {
        Cursor cursor = DataHelper.Consultar(NOME_TABELA, COLUNAS);
        TesteObject objTeste = null;

        try {

            if (cursor != null) {
                boolean temResultado = cursor.moveToFirst();

                if (temResultado)
                {
                    objTeste = new TesteObject();
                    objTeste.setTesteId(cursor.getInt(cursor.getColumnIndexOrThrow(Helper.CHAVE_TABELA)));
                    objTeste.setTesteQrCode(cursor.getString(cursor.getColumnIndexOrThrow(TesteDAL.QRCODE)));
                    objTeste.setTesteLat(cursor.getDouble(cursor.getColumnIndexOrThrow(TesteDAL.LAT)));
                    objTeste.setTesteLng(cursor.getDouble(cursor.getColumnIndexOrThrow(TesteDAL.LNG)));
                }
            }

        } catch (IllegalArgumentException e) {
            Log.e("", e.getMessage());
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

        cursor.close();
        DataHelper.LiberarTransacao();
        return objTeste;
    }


    public TesteObject ConsultarPorId(long id)
    {
        String[] whereArgs = {String.valueOf(id)};

        Cursor cursor = DataHelper.ConsultarPorId(NOME_TABELA, COLUNAS, Helper.CHAVE_TABELA + "=?", whereArgs);
        TesteObject objLocalRetorno = null;

        try {

            if (cursor != null) {
                boolean temResultado = cursor.moveToFirst();

                if (temResultado)
                {
                    objLocalRetorno = new TesteObject();
                    objLocalRetorno.setTesteId(cursor.getInt(cursor.getColumnIndexOrThrow(Helper.CHAVE_TABELA)));
                    objLocalRetorno.setTesteQrCode(cursor.getString(cursor.getColumnIndexOrThrow(TesteDAL.QRCODE)));
                    objLocalRetorno.setTesteLat(cursor.getDouble(cursor.getColumnIndexOrThrow(TesteDAL.LAT)));
                    objLocalRetorno.setTesteLng(cursor.getDouble(cursor.getColumnIndexOrThrow(TesteDAL.LNG)));
                }
            }

        } catch (IllegalArgumentException e) {
            Log.e("", e.getMessage());
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

        cursor.close();
        DataHelper.LiberarTransacao();
        return objLocalRetorno;
    }

    public List<TesteObject> ConsultarTodos()
    {
        Cursor cursor = DataHelper.Consultar(NOME_TABELA, COLUNAS);
        List<TesteObject> lstLocalRetorno = new ArrayList<>();

        try {

            if (cursor != null) {

                while (cursor.moveToNext()){

                    TesteObject objLocalRetorno = new TesteObject();
                    objLocalRetorno.setTesteId(cursor.getInt(cursor.getColumnIndexOrThrow(Helper.CHAVE_TABELA)));
                    objLocalRetorno.setTesteQrCode(cursor.getString(cursor.getColumnIndexOrThrow(TesteDAL.QRCODE)));
                    objLocalRetorno.setTesteLat(cursor.getDouble(cursor.getColumnIndexOrThrow(TesteDAL.LAT)));
                    objLocalRetorno.setTesteLng(cursor.getDouble(cursor.getColumnIndexOrThrow(TesteDAL.LNG)));
                    lstLocalRetorno.add(objLocalRetorno);
                }
            }

        } catch (IllegalArgumentException e) {
            Log.e("", e.getMessage());
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

        cursor.close();
        DataHelper.LiberarTransacao();
        return lstLocalRetorno;
    }

    public int ConsultarUltimoId()
    {
        Cursor cursor = DataHelper.ConsultarUltimoId();
        int retorno = 0;

        cursor.moveToFirst();
        retorno = cursor.getInt(0);

        cursor.close();
        //DataHelper.LiberarTransacao();
        return retorno;
    }


    private static ContentValues ToContentValues(TesteObject objLocal)
    {
        ContentValues valores = new ContentValues();
        valores.put(Helper.CHAVE_TABELA, objLocal.getTesteId());
        valores.put(QRCODE, objLocal.getTesteQrCode());
        valores.put(LAT, objLocal.getTesteLat());
        valores.put(LNG, objLocal.getTesteLng());

        return valores;
    }
}

