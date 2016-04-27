package ejemplo.sbperu.net.androidejemplo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import ejemplo.sbperu.net.androidejemplo.util.Constants;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class EjemploService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String TAG = IntentService.class.getSimpleName();
    public EjemploService() {
        super("EjemploService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.ACTION_RUN_ISERVICE.equals(action)) {
                handleActionRun();
            }
        }
    }

    private void handleActionRun() {
        try {
            // Se construye la notificación
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setContentTitle("Nuevo producto ingresado")
                    .setContentText("Procesando...");
            //int i = 0;

            //while(true){
                startForeground(1, builder.build());

                Intent localIntent = new Intent(Constants.ACTION_RUN_ISERVICE)
                        .putExtra(Constants.EXTRA_EJEMPLO, "Producto Nuevo 0001");

                // Emisión de {@code localIntent}
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

                // Retardo de 1 segundo en la iteración
                Thread.sleep(10000);
           // }
            // Bucle de simulación
            /*
            for (int i = 1; i <= 10; i++) {
                // Poner en primer plano
                builder.setProgress(10, i, false);
                startForeground(1, builder.build());

                Intent localIntent = new Intent(Constants.ACTION_RUN_ISERVICE)
                        .putExtra(Constants.EXTRA_EJEMPLO, i);

                // Emisión de {@code localIntent}
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

                // Retardo de 1 segundo en la iteración
                Thread.sleep(1000);
            }*/
            // Quitar de primer plano
            stopForeground(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio destruido...", Toast.LENGTH_SHORT).show();

        // Emisión para avisar que se terminó el servicio
        Intent localIntent = new Intent(Constants.ACTION_EJEMPLO_EXIT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
