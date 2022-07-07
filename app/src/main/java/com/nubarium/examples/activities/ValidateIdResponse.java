package com.nubarium.examples.activities;

import android.util.Log;
import org.json.JSONObject;

public class ValidateIdResponse {

    private String estatus;
    private String mensaje;
    private String tipo;
    private String subTipo;
    private String sexo;
    private String registro;
    private String claveElector;
    private String curp;
    private String vigencia;
    private String fechaNacimiento;
    private String primerApellido;
    private String segundoApellido;
    private String nombres;
    private String calle;
    private String colonia;
    private String ciudad;
    private String codigoValidacion;
    private String mrz;
    private String cic;
    private String ocr;
    private String identificadorCiudadano;
    private ValidacionMRZ validacionMRZ;
    private ValidacionesRenapo validacionesRenapo;
    private ValidacionesListaNominal validacionesListaNominal;
    private ComparacionFacial comparacionFacial;
    private DatosListaNominal datosListaNominal;


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

    public void fromJson(JSONObject objeto){
        try {
            this.claveElector  = getString(objeto, "claveElector");
            this.estatus  = getString(objeto, "estatus");
            this.mensaje  = getString(objeto, "mensaje");
            this.tipo  = getString(objeto, "tipo");
            this.subTipo  = getString(objeto, "subTipo");
            this.sexo  = getString(objeto, "sexo");
            this.registro  = getString(objeto, "registro");
            this.curp  = getString(objeto, "curp");
            this.vigencia  = getString(objeto, "vigencia");
            this.fechaNacimiento  = getString(objeto, "fechaNacimiento");
            this.primerApellido  = getString(objeto, "primerApellido");
            this.segundoApellido  = getString(objeto, "segundoApellido");
            this.nombres  = getString(objeto, "nombres");
            this.calle  = getString(objeto, "calle");
            this.colonia  = getString(objeto, "colonia");
            this.ciudad  = getString(objeto, "ciudad");
            this.codigoValidacion  = getString(objeto, "codigoValidacion");
            this.mrz  = getString(objeto, "mrz");
            this.cic  = getString(objeto, "cic");
            this.ocr  = getString(objeto, "ocr");
            this.identificadorCiudadano  = getString(objeto, "identificadorCiudadano");

            this.validacionMRZ = new ValidacionMRZ();
            if(objeto.has("validacionesRenapo")) {
                JSONObject validacionMRZJson = getJSONObject(objeto, "validacionMRZ");
                this.validacionMRZ.fromJson(validacionMRZJson);
            }
            this.validacionesRenapo = new ValidacionesRenapo();
            if(objeto.has("validacionMRZ")) {
                JSONObject validacionesRenapoJson = getJSONObject(objeto, "validacionesRenapo");
                this.validacionesRenapo.fromJson(validacionesRenapoJson);
            }
            this.validacionesListaNominal =  new ValidacionesListaNominal();
            
            if(objeto.has("validacionesListaNominal")) {
                JSONObject validacionesListaNominalJson = getJSONObject(objeto, "validacionesListaNominal");
                this.validacionesListaNominal.fromJson(validacionesListaNominalJson);
            }

            this.comparacionFacial =  new ComparacionFacial();
            if(objeto.has("comparacionFacial")) {
                JSONObject comparacionFacialJson = getJSONObject(objeto, "comparacionFacial");
                this.comparacionFacial.fromJson(comparacionFacialJson);
            }

            this.datosListaNominal =  new DatosListaNominal();
            if(objeto.has("datosListaNominal")) {
                JSONObject datosListaNominalJson = getJSONObject(objeto, "datosListaNominal");
                this.datosListaNominal.fromJson(datosListaNominalJson);
            }

            this.datosRenapo =  new DatosRenapo();
            if(objeto.has("datosListaNominal")) {
                JSONObject datosRenapoJson = getJSONObject(objeto, "datosRenapo");
                this.datosRenapo.fromJson(datosRenapoJson);
            }


        }catch(Exception e){
            com.nubarium.components.tools.Utilities.log("upload","error en converisond e objeto");
            com.nubarium.components.tools.Utilities.log("upload",e.toString());
        }
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public DatosListaNominal getDatosListaNominal() {
        return datosListaNominal;
    }

    public void setDatosListaNominal(DatosListaNominal datosListaNominal) {
        this.datosListaNominal = datosListaNominal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getClaveElector() {
        return claveElector;
    }

    public void setClaveElector(String claveElector) {
        this.claveElector = claveElector;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoValidacion() {
        return codigoValidacion;
    }

    public void setCodigoValidacion(String codigoValidacion) {
        this.codigoValidacion = codigoValidacion;
    }

    public String getMrz() {
        return mrz;
    }

    public void setMrz(String mrz) {
        this.mrz = mrz;
    }

    public String getCic() {
        return cic;
    }

    public void setCic(String cic) {
        this.cic = cic;
    }

    public String getOcr() {
        return ocr;
    }

    public void setOcr(String ocr) {
        this.ocr = ocr;
    }

    public String getIdentificadorCiudadano() {
        return identificadorCiudadano;
    }

    public void setIdentificadorCiudadano(String identificadorCiudadano) {
        this.identificadorCiudadano = identificadorCiudadano;
    }

    public ValidacionMRZ getValidacionMRZ() {
        return validacionMRZ;
    }

    public void setValidacionMRZ(ValidacionMRZ validacionMRZ) {
        this.validacionMRZ = validacionMRZ;
    }

    public ValidacionesRenapo getValidacionesRenapo() {
        return validacionesRenapo;
    }

    public void setValidacionesRenapo(ValidacionesRenapo validacionesRenapo) {
        this.validacionesRenapo = validacionesRenapo;
    }

    public ValidacionesListaNominal getValidacionesListaNominal() {
        return validacionesListaNominal;
    }

    public void setValidacionesListaNominal(ValidacionesListaNominal validacionesListaNominal) {
        this.validacionesListaNominal = validacionesListaNominal;
    }



    public ComparacionFacial getComparacionFacial() {
        return comparacionFacial;
    }

    public void setComparacionFacial(ComparacionFacial comparacionFacial) {
        this.comparacionFacial = comparacionFacial;
    }

    public class ValidacionMRZ {
        public double fechaNacimiento;
        private double sexo;
        private double vigencia;
        private double emision;
        private double nombre;

        public void fromJson(JSONObject object){

            fechaNacimiento = getDouble(object, "fechaNacimiento");
            sexo = getDouble(object, "sexo");
            vigencia = getDouble(object, "vigencia");
            emision = getDouble(object, "emision");
            nombre = getDouble(object, "nombre");

        }

        public double getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(double fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public double getSexo() {
            return sexo;
        }

        public void setSexo(double sexo) {
            this.sexo = sexo;
        }

        public double getVigencia() {
            return vigencia;
        }

        public void setVigencia(double vigencia) {
            this.vigencia = vigencia;
        }

        public double getEmision() {
            return emision;
        }

        public void setEmision(double emision) {
            this.emision = emision;
        }

        public double getNombre() {
            return nombre;
        }

        public void setNombre(double nombre) {
            this.nombre = nombre;
        }
    }

    public DatosRenapo getDatosRenapo() {
        return datosRenapo;
    }

    public void setDatosRenapo(DatosRenapo datosRenapo) {
        this.datosRenapo = datosRenapo;
    }

    DatosRenapo datosRenapo;

    public class DatosRenapo{
        private String estatus;
        private String mensaje;
        private String codigoMensaje;

        public void fromJson(JSONObject object){

            estatus = getString(object, "estatus");
            mensaje = getString(object, "mensaje");
            codigoMensaje = getString(object, "codigoMensaje");

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

        public String getCodigoMensaje() {
            return codigoMensaje;
        }

        public void setCodigoMensaje(String codigoMensaje) {
            this.codigoMensaje = codigoMensaje;
        }
    }


    public class ValidacionesRenapo {
        private double primerApellido;
        private double segundoApellido;
        private double nombre;
        private double sexo;
        private double fechaNacimiento;

        public void fromJson(JSONObject object){

            fechaNacimiento = getDouble(object, "fechaNacimiento");
            sexo = getDouble(object, "sexo");
            segundoApellido = getDouble(object, "segundoApellido");
            primerApellido = getDouble(object, "primerApellido");
            nombre = getDouble(object, "nombre");

        }

        public double getSegundoApellido() {
            return segundoApellido;
        }

        public void setSegundoApellido(double segundoApellido) {
            this.segundoApellido = segundoApellido;
        }

        public double getPrimerApellido() {
            return primerApellido;
        }

        public void setPrimerApellido(double primerApellido) {
            this.primerApellido = primerApellido;
        }

        public double getNombre() {
            return nombre;
        }

        public void setNombre(double nombre) {
            this.nombre = nombre;
        }

        public double getSexo() {
            return sexo;
        }

        public void setSexo(double sexo) {
            this.sexo = sexo;
        }

        public double getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(double fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }
    }


    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }



}
