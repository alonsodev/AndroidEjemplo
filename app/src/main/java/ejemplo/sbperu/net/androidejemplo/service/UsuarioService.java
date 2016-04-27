package ejemplo.sbperu.net.androidejemplo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

import ejemplo.sbperu.net.androidejemplo.model.GlobalClass;
import ejemplo.sbperu.net.androidejemplo.model.Usuario;
import ejemplo.sbperu.net.androidejemplo.util.WebServiceUtil;

/**
 * Created by alonso on 18/04/2016.
 */
public class UsuarioService {
    GlobalClass globalVariable;

    public UsuarioService(GlobalClass globalVariable){
        this.globalVariable = globalVariable;
    }

    public void actualizarUsuario(Usuario pUsuario) {
        JSONObject serviceResult = WebServiceUtil.requestWebServiceBeneficiarioPost(globalVariable.getRootURLRestful() +
                "usuario/actualizar", pUsuario);

     /*   try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            Gson gson = builder.create();
            JSONObject jsonUsuario = serviceResult.getJSONObject("jsonObject");
            resultadoFinal = gson.fromJson(jsonUsuario.toString(), ResultadoFinal.class);
        } catch (JSONException e) {
            // handle exception
        }*/


        return;
    }
}
