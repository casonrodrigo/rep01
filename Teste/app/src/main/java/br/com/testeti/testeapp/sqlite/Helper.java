package br.com.testeti.testeapp.sqlite;

/**
 * Created by rodrigo.cason on 09/02/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper {

    public static final String CHAVE_TABELA = "_id";
    private static final String NOME_BANCO = "teste_sqliteDB";
    private static final int VERSAO_BANCO = 1;
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public Helper(Context ctx) {
        this.mCtx = ctx;

        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public boolean ExisteTransacaoAtiva()
    {
        if (mDb != null)
            if (mDb.isOpen())
                return mDb.inTransaction();
            else
                return false;
        else
            return false;
    }

    public void IniciarTransacao()
    {
        if (mDb.isOpen())
            mDb.beginTransaction();
    }

    public void FinalizarTransacao()
    {
        mDb.setTransactionSuccessful();
    }

    public void LiberarTransacao()
    {
        if (mDb != null && mDb.isOpen()) {
            if (mDb.inTransaction()) {
                mDb.endTransaction();
            }
            mDb.close();
            mDbHelper.close();
        }
    }

    public Cursor Consultar(String tabela, String[] colunasSelect) {
        return mDb.query(tabela, colunasSelect, null, null, null, null, null);
    }

    public Cursor ConsultarPorId(String tabela, String[] colunasSelect, String colunas, String[] valores) {
        return mDb.query(tabela, colunasSelect, colunas, valores, null, null, null);
    }

    public Cursor ConsultarUltimoId(){

        //return mDb.query(TesteDAL.NOME_TABELA, new String [] {"MAX(_id)"}, null, null, null, null, null);

        return mDb.rawQuery("SELECT MAX(_id) FROM " + TesteDAL.NOME_TABELA, null);
    }

    public long Inserir(String tabela, ContentValues valores) {
        return mDb.insert(tabela, null, valores);
    }

    public boolean Excluir(String tabela, long idLinha) {
        return mDb.delete(tabela, CHAVE_TABELA + "=?", new String[]{Integer.toString((int) idLinha)}) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, NOME_BANCO, null, VERSAO_BANCO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // para cada tabela refazer/inicializar a estrutura
            db.execSQL(TesteDAL.ESTRUTURA_BANCO);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // para cada tabela excluir no processo de atualização pois será refeita a seguir
            db.execSQL("DROP TABLE IF EXISTS " + TesteDAL.NOME_TABELA);
            onCreate(db);
        }
    }
}
