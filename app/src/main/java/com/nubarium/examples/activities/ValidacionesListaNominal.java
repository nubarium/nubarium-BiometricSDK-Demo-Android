package com.nubarium.examples.activities;

import org.json.JSONObject;

public class ValidacionesListaNominal {


    private String getString(JSONObject objeto, String str){
        try {
            String ret = objeto.getString(str);
            if(ret==null) {
                return "";
            }
            return ret;
        }catch(Exception e){
            return "";
        }
    }

    private Double getDouble(JSONObject objeto, String str){
        try {
            Double ret = objeto.getDouble(str);
            if(ret==null) {
                return 0.0;
            }
            return ret;
        }catch(Exception e){
            return 0.0;
        }
    }

    private JSONObject getJSONObject(JSONObject objeto, String str){
        try {
            JSONObject ret = objeto.getJSONObject(str);
            if(ret==null) {
                return new JSONObject();
            }
            return ret;
        }catch(Exception e){
            return new JSONObject();
        }
    }



    private double claveElector;
    private double numeroEmision;
    private double ocr;
    private double cic;
    private double emision;
    private double registro;
    private double vigencia;

    public void fromJson(JSONObject object){

        claveElector = getDouble(object, "claveElector");
        numeroEmision = getDouble(object, "numeroEmision");
        ocr = getDouble(object, "ocr");
        cic = getDouble(object, "cic");
        emision = getDouble(object, "emision");
        registro = getDouble(object, "registro");
        vigencia = getDouble(object, "vigencia");

    }


    public double getClaveElector() {
        return claveElector;
    }

    public void setClaveElector(double claveElector) {
        this.claveElector = claveElector;
    }

    public double getNumeroEmision() {
        return numeroEmision;
    }

    public void setNumeroEmision(double numeroEmision) {
        this.numeroEmision = numeroEmision;
    }

    public double getOcr() {
        return ocr;
    }

    public void setOcr(double ocr) {
        this.ocr = ocr;
    }

    public double getCic() {
        return cic;
    }

    public void setCic(double cic) {
        this.cic = cic;
    }

    public double getEmision() {
        return emision;
    }

    public void setEmision(double emision) {
        this.emision = emision;
    }

    public double getRegistro() {
        return registro;
    }

    public void setRegistro(double registro) {
        this.registro = registro;
    }

    public double getVigencia() {
        return vigencia;
    }

    public void setVigencia(double vigencia) {
        this.vigencia = vigencia;
    }
}
