package ejemplo.sbperu.net.androidejemplo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ejemplo.sbperu.net.androidejemplo.model.GlobalClass;
import ejemplo.sbperu.net.androidejemplo.model.Usuario;
import ejemplo.sbperu.net.androidejemplo.service.UsuarioService;

public class ActualizarUsuario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GlobalClass globalVariable;
    TextView tvAlias;
    EditText etNombre;
    EditText etCorreo;
    EditText etClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVariable = (GlobalClass) getApplicationContext();
        if(globalVariable.getoUsuario() == null){
            finish();
            Intent i = new Intent(ActualizarUsuario.this, LoginActivity.class);
            startActivity(i);
        }
        setContentView(R.layout.activity_actualizar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvAlias = (TextView) findViewById(R.id.tvAlias);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etClave = (EditText) findViewById(R.id.etClave);

        tvAlias.setText(globalVariable.getoUsuario().getAlias());
        etNombre.setText(globalVariable.getoUsuario().getNombre());
        etCorreo.setText(globalVariable.getoUsuario().getEmail());
        etClave.setText(globalVariable.getoUsuario().getClave());

        Button btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    Usuario oUsuario = new Usuario();
                    oUsuario.setId(globalVariable.getoUsuario().getId());
                    oUsuario.setAlias(globalVariable.getoUsuario().getAlias());
                    oUsuario.setNombre(etNombre.getText().toString());
                    oUsuario.setEmail(etCorreo.getText().toString());
                    oUsuario.setClave(etClave.getText().toString());

                    new ActualizarUsuarioTask(oUsuario).execute();
                } else {
                    Toast.makeText(ActualizarUsuario.this, "Error de conexion", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actualizar_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reporte) {
            Intent i = new Intent(ActualizarUsuario.this, PieChartActivity.class);
            startActivity(i);
        }

        if (id == R.id.nav_cerrar_sesion) {
            globalVariable.setoUsuario(null);
            Intent i = new Intent(ActualizarUsuario.this, LoginActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ActualizarUsuarioTask
            extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog =
                new ProgressDialog(ActualizarUsuario.this);
        private Usuario oUsuario;

        ActualizarUsuarioTask(Usuario pUsuario){
            oUsuario = pUsuario;
        }

        @Override
        protected void onPreExecute() {


            // TODO i18n
            dialog.setMessage("Por favor esperar..");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            // The ItemService would contain the method showed
            // in the previous paragraph
            UsuarioService itemService = new UsuarioService(globalVariable);
            try {
                itemService.actualizarUsuario(oUsuario);
            } catch (Throwable e) {
                // handle exceptions
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            // setListAdapter must not be called at doInBackground()
            // since it would be executed in separate Thread

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            Toast.makeText(ActualizarUsuario.this, "Usuario actualizado satisfactoriamente.", Toast.LENGTH_LONG).show();
        }
    }
}
