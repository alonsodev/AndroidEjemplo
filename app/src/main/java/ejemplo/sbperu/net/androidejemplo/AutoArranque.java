package ejemplo.sbperu.net.androidejemplo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by alonso on 25/04/2016.
 */
public class AutoArranque extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context,  EjemploService.class);
        context.startService(service);
    }

}