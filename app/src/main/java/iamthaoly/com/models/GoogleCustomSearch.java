package iamthaoly.com.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class GoogleCustomSearch {
    Context context;
    private static final String TAG = "search";
    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";
    final String APIKey = "AIzaSyCSl8bs1On23jmf_G15n96GhkIujl1rVCs";
    final String ID = "004604558402211073738:svq9mxynmza";

    public String search(String query) {
        //remove spaces
        String queryNoSpaces = query.replace(" ", "+");
        String urlString = "https://www.googleapis.com/customsearch/v1?q=" + queryNoSpaces
                + "&key=" + APIKey + "&cx=" + ID + "&searchType=image" + "&alt=json";

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }
        result = excute(url);
        writeResultToFile(result);
        return result;
//        GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
//        searchTask.execute(url);
    }
    private void writeResultToFile(String s)
    {
        try {
            FileOutputStream fileout = context.openFileOutput("search.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(s);
            outputWriter.close();
            //display file saved message
            Toast.makeText(context, "File saved successfully!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "File saved failed!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String excute(URL url)
    {
        HttpURLConnection conn = null;
        try
        {
            conn = (HttpURLConnection) url.openConnection();
        }
        catch (IOException e)
        {
            Log.e(TAG, "Http connection ERROR " + e.toString());
        }
        try
        {
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
        }
        catch (IOException e) {
            Log.e(TAG, "Http getting response code ERROR " + e.toString());
        }
        try {
            if (responseCode != null && responseCode == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line + "\n");
                }
                rd.close();
                conn.disconnect();
                result = sb.toString();
                return result;
            } else {
                String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                Log.e(TAG, errorMsg);
                result = errorMsg;
                return result;
            }
        } catch (IOException e) {
            Log.e(TAG, "Http Response ERROR " + e.toString());
        }
        return null;
    }

    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);
            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }

            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());

            }
            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);
            try {
                if (responseCode != null && responseCode == 200) {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    rd.close();
                    conn.disconnect();
                    result = sb.toString();
                    return result;
                } else {
                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;
                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + progress);
        }

        protected void onPostExecute(String result) {
            // make TextView scrollable
//            txtResult.setMovementMethod(new ScrollingMovementMethod());
            // show result
//            txtResult.setText(result);
        }


    }

}
