package ejemplo.sbperu.net.androidejemplo.model;

import android.app.Application;

import java.util.List;

/**
 * Created by alonso on 14/04/2016.
 */
public class GlobalClass extends Application {
    final String rootURLRestful = "http://10.0.2.2:8082/ejemplo/api/v1/";
    Usuario oUsuario;

    public Usuario getoUsuario() {
        return oUsuario;
    }

    public void setoUsuario(Usuario oUsuario) {
        this.oUsuario = oUsuario;
    }

    public String getRootURLRestful() {
        return rootURLRestful;
    }

}
