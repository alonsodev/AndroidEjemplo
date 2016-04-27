package ejemplo.sbperu.net.androidejemplo.util;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import ejemplo.sbperu.net.androidejemplo.model.Usuario;

/**
 * Created by alonso on 14/04/2016.
 */
public class WebServiceUtil {

    public static JSONObject requestWebService(String serviceUrl) {
        disableConnectionReuseIfNecessary();

        HttpURLConnection urlConnection = null;
        JSONObject oJSONObject = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();

            urlConnection.setConnectTimeout(150000);
            urlConnection.setReadTimeout(300000);
            //urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());

            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, "UTF-8");
            String theString = writer.toString();
            theString = "{\"jsonObject\" : " + theString + "}";
            oJSONObject = (JSONObject) new JSONTokener(theString).nextValue();

        } catch (MalformedURLException e) {
            // URL is invalid
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
        } catch (JSONException e) {
            e.printStackTrace();
            // response body is no valid JSON string
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return oJSONObject;
    }

    public static JSONObject requestWebServiceBeneficiarioPost(String serviceUrl, Usuario pUsuario) {
        disableConnectionReuseIfNecessary();

        HttpURLConnection urlConnection = null;
        JSONObject oJSONObject = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();

            urlConnection.setConnectTimeout(150000);
            urlConnection.setReadTimeout(300000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String parametro = gson.toJson(pUsuario);

            DataOutputStream printout;
            ByteArrayOutputStream baos = null;
            baos = new ByteArrayOutputStream();
            printout = new DataOutputStream(urlConnection.getOutputStream ());
            printout.writeBytes(URLEncoder.encode(parametro, "UTF-8"));
            printout.flush ();
            printout.close ();

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());

            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, "UTF-8");
            String theString = writer.toString();
            theString = "{\"jsonObject\" : " + theString + "}";
            oJSONObject = (JSONObject) new JSONTokener(theString).nextValue();

        } catch (MalformedURLException e) {
            // URL is invalid
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
        } catch (JSONException e) {
            e.printStackTrace();
            // response body is no valid JSON string
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return oJSONObject;
    }

    /**
     * required in order to prevent issues in earlier Android version.
     */
    private static void disableConnectionReuseIfNecessary() {
        // see HttpURLConnection API doc
        if (Integer.parseInt(Build.VERSION.SDK)
                < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

}
