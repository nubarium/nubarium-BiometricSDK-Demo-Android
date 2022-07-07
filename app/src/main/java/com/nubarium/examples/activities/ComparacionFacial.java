package com.nubarium.examples.activities;

import org.json.JSONObject;

public class ComparacionFacial {


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


    private double similitud;
    private String estatus;
    private String mensaje;

    public void fromJson(JSONObject object){

        estatus = getString(object, "estatus");
        mensaje = getString(object, "mensaje");
        similitud = getDouble(object, "similitud");

    }


    public double getSimilitud() {
        return similitud;
    }

    public void setSimilitud(double similitud) {
        this.similitud = similitud;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}