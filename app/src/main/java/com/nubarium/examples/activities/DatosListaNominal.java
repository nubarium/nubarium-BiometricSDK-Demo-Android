package com.nubarium.examples.activities;

import org.json.JSONObject;

public class DatosListaNominal{


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



    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getClaveMensaje() {
        return claveMensaje;
    }

    public void setClaveMensaje(String claveMensaje) {
        this.claveMensaje = claveMensaje;
    }

    public String getCodigoValidacion() {
        return codigoValidacion;
    }

    public void setCodigoValidacion(String codigoValidacion) {
        this.codigoValidacion = codigoValidacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCic() {
        return cic;
    }

    public void setCic(String cic) {
        this.cic = cic;
    }

    public String getClaveElector() {
        return claveElector;
    }

    public void setClaveElector(String claveElector) {
        this.claveElector = claveElector;
    }

    public String getNumeroEmision() {
        return numeroEmision;
    }

    public void setNumeroEmision(String numeroEmision) {
        this.numeroEmision = numeroEmision;
    }

    public String getDistritoFederal() {
        return distritoFederal;
    }

    public void setDistritoFederal(String distritoFederal) {
        this.distritoFederal = distritoFederal;
    }

    public String getOcr() {
        return ocr;
    }

    public void setOcr(String ocr) {
        this.ocr = ocr;
    }

    public String getAnioRegistro() {
        return anioRegistro;
    }

    public void setAnioRegistro(String anioRegistro) {
        this.anioRegistro = anioRegistro;
    }

    public String getAnioEmision() {
        return anioEmision;
    }

    public void setAnioEmision(String anioEmision) {
        this.anioEmision = anioEmision;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    private String mensaje;
    private String claveMensaje;
    private String codigoValidacion;
    private String estatus;
    private String cic;
    private String claveElector;
    private String numeroEmision;
    private String distritoFederal;
    private String ocr;
    private String anioRegistro;
    private String anioEmision;
    private String vigencia;

    public void fromJson(JSONObject object){

        mensaje = getString(object, "mensaje");
        claveMensaje = getString(object, "claveMensaje");
        codigoValidacion = getString(object, "codigoValidacion");
        estatus = getString(object, "estatus");
        cic = getString(object, "cic");
        claveElector = getString(object, "claveElector");
        numeroEmision = getString(object, "numeroEmision");
        distritoFederal = getString(object, "distritoFederal");
        ocr = getString(object, "ocr");
        anioRegistro = getString(object, "anioRegistro");
        anioEmision = getString(object, "anioEmision");
        vigencia = getString(object, "vigencia");

    }

}
