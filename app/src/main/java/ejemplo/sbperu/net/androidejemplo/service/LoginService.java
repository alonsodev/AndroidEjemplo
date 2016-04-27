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
 * Created by alonso on 15/04/2016.
 */
public class LoginService {
    GlobalClass globalVariable;

    public LoginService(GlobalClass globalVariable){
        this.globalVariable = globalVariable;
    }

    public Usuario loginUsuario(String usuario, String clave) {
        /*JSONObject serviceResult = WebServiceUtil.requestWebService(globalVariable.getRootURLRestful() +
                "login/" + usuario + "/" + clave);

        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        Usuario oUsuario = null;
        try {
            JSONObject jsonUsuario = serviceResult.getJSONObject("jsonObject");
            oUsuario = gson.fromJson(jsonUsuario.toString(), Usuario.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        Usuario oUsuario = null;
        if(usuario.equals("alonsodev") && clave.equals("123456")){
            oUsuario = new Usuario();
            oUsuario.setId(1);
            oUsuario.setNombre("alonso palacios");
            oUsuario.setEmail("alonso@host.com");
            oUsuario.setAlias("alonsodev");
            oUsuario.setClave("123456");
        }

        return oUsuario;
    }
}
