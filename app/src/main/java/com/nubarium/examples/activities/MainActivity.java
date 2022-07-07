package com.nubarium.examples.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nubarium.app.demo.R;
import com.nubarium.components.enums.CaptureMode;
import com.nubarium.components.sdk.FacialCapture;
import com.nubarium.components.sdk.FacialResult;
import com.nubarium.components.sdk.IdCapture;
import com.nubarium.components.sdk.IdResult;
import com.nubarium.components.tools.RestHelper;
import com.nubarium.components.tools.Utilities;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    boolean underReview = false;
    private ProgressBar pgsBar;
    private ComparacionFacial comparacionFacial;
    private DatosListaNominal datosListaNominal;
    private ValidateIdResponse validateIdResponse;
    private JSONObject searchFaceResults;
    private int serviceStatusOcr = 0;
    private int serviceStatusIne = 0;
    private int serviceStatusFacial = 0;
    private int serviceStatusSearch = 0;
    private Handler handler = new Handler();
    private FacialCapture facialCapture;
    private IdCapture idCapture;
    private FacialResult facialResult;
    private IdResult idResult;
    private String base64Front;
    private String base64Back;
    private String base64Face;
    private String uuid;
    private String username;
    private String password;

    @Override
    protected void onResume() {


        super.onResume();
        boolean isConnected = Utilities.isConnected(this);
        if (!isConnected) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Captura de ID")
                    .setMessage("Este servicio requiere de una conexión a Internet.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked

                        }
                    }).show();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        username = getString(R.string.username);
        password = getString(R.string.password);

        uuid = UUID.randomUUID().toString();

        facialCapture = new FacialCapture(this);

        facialCapture.setLivenessRequired(true);
        facialCapture.setShowHelp(false);
        facialCapture.setShowVideo(false);
        facialCapture.setShowPreview(false); // Pendiente por un bug encontrado
        facialCapture.setCredentials(username, password);
        // optional to handle the credentials failure
        facialCapture.addOnInitListener(new FacialCapture.OnInitListener() {
            @Override
            public void onInit(String token) {
                Log.d("track", "token generated: " + token);
            }

            @Override
            public void onFail(String reason) {
                Log.d("track", "fail, reason: " + reason);
            }
        });

        // Manejo de eventos
        facialCapture.addOnResultListener(new FacialCapture.OnResultListener() {

            @Override
            public void onSuccess(FacialResult faceResultRet, Bitmap faceImage, Bitmap areaImage) {
                base64Face = toBase64(faceImage);
                facialResult = faceResultRet;
                // Open ID Capture component
                startIdCaptureComponent();
            }

            // En caso de que falle , este caso no es para captura de excpciones sino cuando no pase las prueba sbiometricas
            @Override
            public void onFail(FacialCapture.ReasonFail reasonFail, String reason) {

            }

            // Cuando suceda un error que no se maneja con excepciones
            @Override
            public void onError(FacialCapture.Error error, String message) {

            }

            // Cuando suceda un error que no se maneja con excepciones
            @Override
            public void onCancel() {
                Log.d("Track", "Cancelado");

            }
        });
        facialCapture.initialize("ANY_TRANSACTION_TAG");

        idCapture = new IdCapture(this);
        idCapture.setAntispoofingRequired(true);
        idCapture.setCaptureMode(CaptureMode.AUTO);
        idCapture.setAllowCaptureOnFail(true);
        idCapture.setMaxValidations(2);
        idCapture.setCredentials(username, password);
        idCapture.addOnInitListener(new IdCapture.OnInitListener() {
            @Override
            public void onInit(String token) {
                Log.d("track", "token generado: " + token);
            }

            @Override
            public void onFail(String reason) {
                Log.d("track", "fallo, motivo: " + reason);
            }
        });//.initialize("tag");
        // Manejo de eventos
        idCapture.addOnResultListener(new IdCapture.OnResultListener() {

                                          // En caso de que se pueda obtener la imagen del rostro.
                                          @Override
                                          public void onSuccess(IdResult validateResultRet, Bitmap frontImage, Bitmap backImage, CaptureMode captureMode) {
                                              base64Front = toBase64(frontImage);
                                              base64Back = toBase64(backImage);
                                              idResult = validateResultRet;
                                              Log.d("track", "Overall result" + idResult.getOverallResult());
                                              Log.d("track", "resultado capture mode: " + captureMode.getValue());
                                              //start(base64Front, base64Back, base64Face, "123456");
                                              extractOcr(base64Front, base64Back);
                                              facialCompare(base64Front, base64Face);
                                              searchFace(base64Face);

                                          }

                                          // En caso de que falle , este caso no es para captura de excpciones sino cuando no pase las prueba sbiometricas
                                          @Override
                                          public void onFail(IdCapture.ReasonFail reasonFail, String reason) {
                                              Log.d("track", "reasonFail " + reasonFail.getValue() + "  reason: " + reason);
                                          }

                                          // Cuando suceda un error que no se maneja con excepciones
                                          @Override
                                          public void onError(IdCapture.Error error, String message) {

                                          }

                                          // Cuando suceda un error que no se maneja con excepciones
                                          @Override
                                          public void onCancel() {
                                              Log.d("track", "Cancelado");

                                          }
                                      }
        );
        idCapture.initialize("ANY_TRANSACTION_TAG");
    }

    public void testLiveness(View view) {
        try {
            // Lanzamiento del componente
            facialCapture.start();
            //switchSheet(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Solo Agregar esta linea para procesar la respuesta del componente
        facialCapture.process(requestCode, resultCode, data);
        idCapture.process(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startIdCaptureComponent() {
        try {
            // 
            idCapture.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickAccept(View view) {

        switchSheet(false);
    }

    public void clickAdd(View view) {

        addFace(base64Face);

    }

    public void switchSheet(boolean underReview) {
        this.underReview = underReview;

        if (underReview) {
            LinearLayout reviewSheet = findViewById(R.id.reviewSheet);
            reviewSheet.setVisibility(View.VISIBLE);
            LinearLayout scanSheet = findViewById(R.id.scanSheet);
            scanSheet.setVisibility(View.GONE);

            Button btnFinish = findViewById(R.id.btn_start);
            btnFinish.setVisibility(View.GONE);
            Button btnExit = findViewById(R.id.btn_exit);
            btnExit.setVisibility(View.GONE);
            Button btnAccept = findViewById(R.id.btn_accept);
            btnAccept.setVisibility(View.VISIBLE);

        } else {
            LinearLayout reviewSheet = findViewById(com.nubarium.components.R.id.reviewSheet);
            reviewSheet.setVisibility(View.GONE);
            LinearLayout scanSheet = findViewById(com.nubarium.components.R.id.scanSheet);
            scanSheet.setVisibility(View.VISIBLE);

            Button btnFinish = findViewById(R.id.btn_start);
            btnFinish.setVisibility(View.VISIBLE);
            Button btnExit = findViewById(R.id.btn_exit);
            btnExit.setVisibility(View.VISIBLE);
            Button btnAccept = findViewById(R.id.btn_accept);
            btnAccept.setVisibility(View.GONE);
            Button btnAddFace = findViewById(R.id.btn_add);
            btnAddFace.setVisibility(View.GONE);
            
        }

    }


    private void scheduleCheck() {

        if (serviceStatusFacial != 0 && serviceStatusIne != 0 && serviceStatusOcr != 0 && serviceStatusSearch != 0) {
            turnOffProgressbar();
            if (serviceStatusFacial == -1 || serviceStatusIne == -1 || serviceStatusOcr == -1) {
                // existieron problemas obteniendo informacion vuelva a capturara
                if (validateIdResponse.getClaveElector() != null && !validateIdResponse.getClaveElector().toString().equals("")) {
                    switchSheet(true);
                    processValidateId(validateIdResponse);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Captura de ID")
                            .setMessage("No se pudo procesar la petición, intente capturar de nuevo el ID")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked

                                }
                            }).show();
                }

            } else {

                if (validateIdResponse.getClaveElector() != null && !validateIdResponse.getClaveElector().toString().equals("")) {
                    switchSheet(true);
                    processValidateId(validateIdResponse);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Captura de ID")
                            .setMessage("No se pudo procesar la petición, intente capturar de nuevo el ID")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked

                                }
                            }).show();

                }


            }
        } else {
            handler.postDelayed(new Runnable() {
                public void run() {
                    scheduleCheck();
                }
            }, 2500);

        }
    }


    public void validateIne(String cic, String identificadorCiudadano) {
        try {
            Log.d("track", "start");
            turnOnProgressbar();
            String URL = "https://ine.nubarium.com/ine/v2/valida_ine";
            JSONObject body = new JSONObject();
            body.put("cic", cic);
            body.put("identificadorCiudadano", identificadorCiudadano);

            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


            JSONObject headers = new JSONObject();
            headers.put("Authorization", auth);

            RestHelper restHelper = new RestHelper(this.getApplicationContext());
            JSONObject parameters = new JSONObject();
            parameters.put("host", URL);
            parameters.put("method", "POST");
            parameters.put("header", headers);
            restHelper.start(parameters, body);
            restHelper.addOnResponseListener(new RestHelper.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    String respuesta = response.toString();

                    datosListaNominal = new DatosListaNominal();
                    datosListaNominal.fromJson(response);
                    validateIdResponse.setDatosListaNominal(datosListaNominal);

                    serviceStatusIne = 1;

                    Log.d("upload ine", respuesta);
                    //turnOffProgressbar();

                }

                @Override
                public void onFail(String statusCode, String response) {
                    serviceStatusIne = -1;
                }

                @Override
                public void onException(String exception) {
                    serviceStatusIne = -1;
                }
            });

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }


    public void facialCompare(String credencial, String captura) {
        try {
            Log.d("track", "start");
            turnOnProgressbar();
            String URL = "https://biometrics.nubarium.com/antifraude/reconocimiento_facial";
            JSONObject body = new JSONObject();
            body.put("credencial", credencial);
            body.put("captura", captura);
            body.put("tipo", "imagen");
            body.put("limiteInferior", "10");


            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


            JSONObject headers = new JSONObject();
            headers.put("Authorization", auth);

            RestHelper restHelper = new RestHelper(this.getApplicationContext());
            JSONObject parameters = new JSONObject();
            parameters.put("host", URL);
            parameters.put("method", "POST");
            parameters.put("header", headers);
            restHelper.start(parameters, body);
            restHelper.addOnResponseListener(new RestHelper.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {

                    String respuesta = response.toString();
                    Log.d("upload facial", respuesta);
                    comparacionFacial = new ComparacionFacial();
                    comparacionFacial.fromJson(response);
                    validateIdResponse.setComparacionFacial(comparacionFacial);

                    serviceStatusFacial = 1;

                }

                @Override
                public void onFail(String statusCode, String response) {
                    serviceStatusFacial = -1;
                }

                @Override
                public void onException(String exception) {
                    serviceStatusFacial = -1;
                }
            });

        } catch (Exception e) {
            serviceStatusFacial = -1;
            Log.d("Exception", e.toString());
        }
    }

    /* Add the given face to the database */
    public void addFace(String faceImage) {
        try {
            Log.d("track", "start");
            turnOnProgressbar();
            String URL = "https://facedb.nubarium.com/facedb/v1/add-face";
            JSONObject body = new JSONObject();
            body.put("faceImage", faceImage);

            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


            JSONObject headers = new JSONObject();
            headers.put("Authorization", auth);

            RestHelper restHelper = new RestHelper(this.getApplicationContext());
            JSONObject parameters = new JSONObject();
            parameters.put("host", URL);
            parameters.put("method", "POST");
            parameters.put("header", headers);
            restHelper.start(parameters, body);
            restHelper.addOnResponseListener(new RestHelper.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        String respuesta = response.toString();
                        turnOffProgressbar();
                        Log.d("upload face add", respuesta);
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Motor facial")
                                .setMessage("Se agrego el rostro al motor facial")
                                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //set what would happen when positive button is clicked
                                        switchSheet(false);
                                    }
                                }).show();


                    } catch (Exception e) {
                        Log.d("track", "error: " + e.toString());
                    }

                }

                @Override
                public void onFail(String statusCode, String response) {
                    turnOffProgressbar();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Motor facial")
                            .setMessage("Existió un problema al agregar el rostro, intente de nuevo.")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                    switchSheet(false);
                                }
                            }).show();
                }

                @Override
                public void onException(String exception) {
                    turnOffProgressbar();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Motor facial")
                            .setMessage("Existió un problema al agregar el rostro, intente de nuevo.")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                    switchSheet(false);
                                }
                            }).show();
                }
            });

        } catch (Exception e) {
            Log.d("Exception", e.toString());

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Motor facial")
                    .setMessage("Existió un problema al agregar el rostro, intente de nuevo.")
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            switchSheet(false);
                        }
                    }).show();

        }
    }

    /* Search the given face on the database */
    public void searchFace(String faceImage) {
        try {
            Log.d("track", "start");
            turnOnProgressbar();
            String URL = "https://facedb.nubarium.com/facedb/v1/search-face";
            JSONObject body = new JSONObject();
            body.put("faceImage", faceImage);

            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


            JSONObject headers = new JSONObject();
            headers.put("Authorization", auth);

            RestHelper restHelper = new RestHelper(this.getApplicationContext());
            JSONObject parameters = new JSONObject();
            parameters.put("host", URL);
            parameters.put("method", "POST");
            parameters.put("header", headers);
            restHelper.start(parameters, body);
            restHelper.addOnResponseListener(new RestHelper.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        String respuesta = response.toString();
                        serviceStatusSearch = 1;
                        searchFaceResults = new JSONObject(respuesta);

                        Log.d("upload search face", respuesta);

                    } catch (Exception e) {
                        Log.d("track", "error: " + e.toString());
                    }

                }

                @Override
                public void onFail(String statusCode, String response) {
                    serviceStatusSearch = -1;
                }

                @Override
                public void onException(String exception) {
                    serviceStatusSearch = -1;
                }
            });

        } catch (Exception e) {
            serviceStatusSearch = -1;
            Log.d("Exception", e.toString());
        }
    }

    /* Delete the given face id */
    public void deleteFace(String id) {
        try {
            Log.d("track", "start deleteFace");
            turnOnProgressbar();
            String URL = "https://facedb.nubarium.com/facedb/v1/delete-face";
            JSONObject body = new JSONObject();
            body.put("id", id);

            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


            JSONObject headers = new JSONObject();
            headers.put("Authorization", auth);

            RestHelper restHelper = new RestHelper(this.getApplicationContext());
            JSONObject parameters = new JSONObject();
            parameters.put("host", URL);
            parameters.put("method", "POST");
            parameters.put("header", headers);
            restHelper.start(parameters, body);
            restHelper.addOnResponseListener(new RestHelper.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        String respuesta = response.toString();

                        Log.d("upload search face", respuesta);

                    } catch (Exception e) {
                        Log.d("track", "error: " + e.toString());
                    }

                }

                @Override
                public void onFail(String statusCode, String response) {

                }

                @Override
                public void onException(String exception) {

                }
            });

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }


    public void extractOcr(String front, String back) {
        try {
            Log.d("track", "start");
            turnOnProgressbar();
            String URL = "https://ine.nubarium.com/ocr/v1/obtener_datos_id";
            JSONObject body = new JSONObject();
            body.put("id", front);
            body.put("idReverso", back);

            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


            JSONObject headers = new JSONObject();
            headers.put("Authorization", auth);

            RestHelper restHelper = new RestHelper(this.getApplicationContext());
            JSONObject parameters = new JSONObject();
            parameters.put("host", URL);
            parameters.put("method", "POST");
            parameters.put("header", headers);
            restHelper.start(parameters, body);
            restHelper.addOnResponseListener(new RestHelper.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        serviceStatusOcr = 1;
                        String respuesta = response.toString();
                        validateIdResponse = new ValidateIdResponse();
                        validateIdResponse.fromJson(response);

                        Log.d("upload ocr", respuesta);
                        String cic = response.getString("cic");
                        String identificadorCiudadano = response.getString("identificadorCiudadano");
                        validateIne(cic, identificadorCiudadano);
                        scheduleCheck();
                        ;
                    } catch (Exception e) {
                        Log.d("track", "error: " + e.toString());
                    }

                }

                @Override
                public void onFail(String statusCode, String response) {
                    turnOffProgressbar();
                }

                @Override
                public void onException(String exception) {
                    turnOffProgressbar();

                }
            });

        } catch (Exception e) {
            Log.d("Exception", e.toString());
            turnOffProgressbar();

        }
    }


    private void turnOnProgressbar() {
        pgsBar = findViewById(com.nubarium.components.R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);
    }

    private void turnOffProgressbar() {
        pgsBar = findViewById(com.nubarium.components.R.id.pBar);
        pgsBar.setVisibility(View.INVISIBLE);
    }

    private String getColorFacial(double val) {
        if (val >= 75) {
            return "<font color='#89D099'><b>" + val + " %</b></font>";
        } else if (val >= 50) {
            return "<font color='#ffcc00'><b>" + val + " %</b></font>";
        } else
            return "<font color='red'><b>" + val + " %</b></font>";
    }

    private String getColorText(double val) {
        if (val == 100) {
            return "<font color='#89D099'><b>" + val + " %</b></font>";
        } else if (val > 90) {
            return "<font color='#ffcc00'><b>" + val + " %</b></font>";
        } else
            return "<font color='red'><b>" + val + " %</b></font>";
    }

    private String getColorAttack(double val, String label) {
        if (val >= 70) {
            return "<font color='#89D099'><b>" + label + " </b></font>";
        } else if (val > 60) {
            return "<font color='#ffcc00'><b>" + label + " </b></font>";
        } else
            return "<font color='red'><b>" + label + " </b></font>";
    }

    public String getStringIfNull(String val) {
        if (val == null)
            return "";
        else
            return val;
    }

    private void processValidateId(ValidateIdResponse data) {
        Log.d("track", "processValidateId: ");

        ArrayList<String> lista = new ArrayList<String>();
        try {
            if (searchFaceResults != null && searchFaceResults.getString("status").equals("OK")) {
                lista.add("+<b>Busqueda de Cara en Motor Facial</b>");
                String messageCode = searchFaceResults.getString("messageCode");
                String message = searchFaceResults.getString("message");
                if (messageCode.equals("1")) {
                    lista.add(getColorAttack(0, "No se encontró ningún rostro"));
                    Button btnAddFace = findViewById(R.id.btn_add);
                    btnAddFace.setVisibility(View.VISIBLE);
                } else {
                    if (messageCode.equals("0")) {
                        double match = searchFaceResults.getDouble("similarity");
                        //{"id":"e70c1e21-09f5-413a-991b-4513e4df6a0b","status":"OK","messageCode":0,"message":"Face succesfully added","validationCode":"fa1657199519.9030855"}
                        lista.add("<b>Folio de registro: </b> " + getColorAttack(100, searchFaceResults.getString("id")));
                        lista.add("<b>Similitud: </b> " + getColorAttack(match, match + " %"));
                        Button btnAddFace = findViewById(R.id.btn_add);
                        btnAddFace.setVisibility(View.GONE);
                    } else {

                    }

                }


            }
        } catch (Exception e) {

        }

        if (idResult.getOverallResult() != null && !idResult.getOverallResult().equals("")) {
            lista.add("+<b>Confianza en ID</b>");
            if (idResult.getOverallResult().equals("PASS")) {
                lista.add("<b>Resultado General</b> : " + getColorAttack(100, idResult.getOverallResult()));
            } else {
                if (idResult.getOverallResult().equals("WARNING")) {
                    lista.add("<b>Resultado General</b> : " + getColorAttack(69, idResult.getOverallResult()));
                } else {
                    lista.add("<b>Resultado General</b> : " + getColorAttack(30, idResult.getOverallResult()));
                }
            }
            lista.add("<b>Indice de posible ataque :</b> : " + idResult.getOverallResult());

        }

        lista.add("+<b>OCR</b>");
        if (!data.getComparacionFacial().getEstatus().equals("ERROR")) {
            lista.add("<b>Similitud facial</b> : " + getColorFacial(data.getComparacionFacial().getSimilitud()));
        } else {
            lista.add("<b>Similitud facial</b> : " + getColorFacial(10));
        }
        lista.add("<b>Clave de elector</b> : " + data.getClaveElector());
        if (!data.getSubTipo().equals("C")) {
            lista.add("<b>CIC</b> : " + data.getCic());
        }
        if (data.getOcr() != null) {
            lista.add("<b>OCR</b> : " + data.getOcr());
        }
        lista.add("<b>Nombre</b> : " + data.getNombres());
        lista.add("<b>Primer apellido</b> : " + data.getPrimerApellido());
        lista.add("<b>Segundo apellido</b> : " + data.getSegundoApellido());

        lista.add("<b>Tipo</b> : " + data.getTipo());
        lista.add("<b>Subtipo</b> : " + data.getSubTipo());
        lista.add("<b>Curp</b> : " + getStringIfNull(data.getCurp()));
        lista.add("<b>Vigencia</b> : " + data.getVigencia());
        if (data.getRegistro() != null) {
            lista.add("<b>Registro</b> : " + getStringIfNull(data.getRegistro()));
        }
        if (data.getDatosListaNominal() != null) {
            lista.add("+<b>Datos de Lista Nominal</b> : ");
            if (data.getDatosListaNominal().getEstatus() != null && !data.getDatosListaNominal().getEstatus().equals("ERROR")) {
                lista.add("<b>CIC</b> : " + data.getDatosListaNominal().getCic());
                lista.add("<b>Clave de elector</b> : " + data.getDatosListaNominal().getClaveElector());
                lista.add("<b>OCR</b> : " + data.getDatosListaNominal().getOcr());
                lista.add("<b>Emisión</b> : " + data.getDatosListaNominal().getAnioEmision());
                lista.add("<b>Registro</b> : " + data.getDatosListaNominal().getAnioRegistro());
                lista.add("<b>Número de Emisión</b> : " + data.getDatosListaNominal().getNumeroEmision());
                lista.add("<b>Vigencia</b> : " + data.getDatosListaNominal().getVigencia());

            } else {
                lista.add("<b>Estatus</b> : " + data.getDatosListaNominal().getEstatus());
                lista.add("<b>Mensaje</b> : " + data.getDatosListaNominal().getMensaje());
            }
        }

        Object[] facialInstructionsList = lista.toArray();
        LinearLayout layout = findViewById(com.nubarium.components.R.id.bullet_list);
        layout.removeAllViews();
        if (facialInstructionsList.length > 0) {
            for (int i = 0; i < facialInstructionsList.length; i++) {
                String txt = (facialInstructionsList[i]).toString();
                if (!txt.startsWith("+")) {
                    View c = LayoutInflater.from(this).inflate(com.nubarium.components.R.layout.text_view_bullet, null);
                    TextView t = (TextView) c.findViewWithTag("bullet");
                    t.setText(Html.fromHtml(txt));
                    layout.addView(c);
                } else {
                    txt = txt.substring(1);
                    View c = LayoutInflater.from(this).inflate(com.nubarium.components.R.layout.text_view_subtitle, null);
                    TextView t = (TextView) c.findViewWithTag("subtitle");
                    t.setText(Html.fromHtml(txt));
                    layout.addView(c);
                }
            }
        }
        layout.invalidate();
    }


    private byte[] toByte(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageResultArray = stream.toByteArray();
        return imageResultArray;
    }

    private String toBase64(Bitmap image) {
        byte[] array = toByte(image);
        String base64 = Base64.encodeToString(array, Base64.NO_WRAP);
        return base64;
    }

}