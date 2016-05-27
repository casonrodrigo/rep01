package br.com.testeti.testeapp.sqlite;

import android.content.Context;

import java.util.List;

/**
 * Created by rodrigo.cason on 27/05/2016.
 */
public class TesteManager {

    private final Context _contexto;
    private Helper _helper;

    public TesteManager(Helper helper, Context contexto) {
        _helper = helper;
        _contexto = contexto;
    }

    public Helper getHelper() {
        if (_helper == null) {
            _helper = new Helper(_contexto);
        }
        return _helper;
    }

    public long Salvar(TesteObject objTeste) {
        long retorno = 0;
        boolean iniciouTransacao = false;

        if (_helper == null)
            _helper = getHelper();

        try {
            if (!_helper.ExisteTransacaoAtiva()) {
                _helper.IniciarTransacao();
                iniciouTransacao = true;
            }

            TesteDAL objTesteDAL = new TesteDAL(getHelper(), _contexto);

            int maxID = objTesteDAL.ConsultarUltimoId();
            objTeste.setTesteId(maxID + 1);

            retorno = objTesteDAL.Inserir(objTeste);

            if (iniciouTransacao) {
                _helper.FinalizarTransacao();
            }

        } catch (Exception e) { //NAO DAR THROW DE EXCEPTION PADRAO
            if (iniciouTransacao) {
                _helper.LiberarTransacao();
            }
            e.printStackTrace();
        } finally {
            if (iniciouTransacao) {
                _helper.LiberarTransacao();
            }
        }
        return retorno;
    }

    public List<TesteObject> ConsultarTodos() {
        List<TesteObject> lstTesteObject = null;

        try {

            TesteDAL objTesteDAL = new TesteDAL(getHelper(), _contexto);
            lstTesteObject = objTesteDAL.ConsultarTodos();

        } catch (Exception e) { //NAO DAR THROW DE EXCEPTION PADRAO
            throw e;
        }

        return lstTesteObject;
    }
}
