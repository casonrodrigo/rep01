package br.com.testeti.testeapp.sqlite;

import android.content.Context;

import java.util.List;

/**
 * Created by rodrigo.cason on 25/05/2016.
 */
public class TesteObject {

    private int TesteId;
    private String TesteQrCode;
    private double TesteLat;
    private double TesteLng;

    public int getTesteId() {
        return TesteId;
    }

    public void setTesteId(int testeId) {
        TesteId = testeId;
    }

    public String getTesteQrCode() {
        return TesteQrCode;
    }

    public void setTesteQrCode(String testeQrCode) {
        TesteQrCode = testeQrCode;
    }

    public double getTesteLat() {
        return TesteLat;
    }

    public void setTesteLat(double testeLat) {
        TesteLat = testeLat;
    }

    public double getTesteLng() {
        return TesteLng;
    }

    public void setTesteLng(double testeLng) {
        TesteLng = testeLng;
    }

}


