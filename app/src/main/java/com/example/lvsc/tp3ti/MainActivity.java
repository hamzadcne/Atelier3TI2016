package com.example.lvsc.tp3ti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Adapters.PharmacieArrayAdapter;
import Models.Pharmacie;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String langue = preferences.getString(getString(R.string.app_language_pref), "en");


        SharedPreferences SP = PreferenceManager.
                getDefaultSharedPreferences(getBaseContext());
        String searchDistanceStr = SP.getString(getString(R.string.search_distance_pref), "500");

        int searchDistanceValue = Integer.valueOf(searchDistanceStr);
        listView= (ListView)findViewById(R.id.listView);
        /*
        String[] myArray=new String[]{
                "Element 1",
                "Element 2",
                "Element 3",
                "Element 4",
                "Element 5",
                "Element 6",
                "Element 7",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,
                R.layout.liste_layout,
                R.id.txtViewLayout,
                myArray);
        listView.setAdapter(adapter);
        */
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

            startActivity(new Intent(this,ConfigActivity.class));

        }else if(id==R.id.action_refresh){

        }

        return super.onOptionsItemSelected(item);
    }

    public void onLoginClick(View view){

        EditText usernameTxt=(EditText)findViewById(R.id.emailTxt);
        EditText passwordTxt=(EditText)findViewById(R.id.passwordTxt);


        username=usernameTxt.getText().toString();
        password=passwordTxt.getText().toString();
        if(username.isEmpty())
            Toast.makeText(getBaseContext(),"Entrez votre username",Toast.LENGTH_SHORT).show();
        else if(password.isEmpty())
            Toast.makeText(getBaseContext(),"Entrez votre mot de passe",Toast.LENGTH_SHORT).show();
        else {
            String urlString = "http://192.168.137.1:8090/pharma/login.php";
            LoginTask loginTask = new LoginTask();
            loginTask.execute(urlString);
        }
    }


    public void onLoadClick(View view) {
        String urlString="http://192.168.137.1:8090/pharma/pharmacies.php";
        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute(urlString);
    }


    private class BackgroundTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection c=null;
            try {
                String urlString=params[0];
                URL url=new URL(urlString);
                c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setConnectTimeout(15000 /* milliseconds */);
                c.setDoInput(true);

                c.connect();
                int mStatusCode = c.getResponseCode();
                switch (mStatusCode) {
                    case 200:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        String result =  sb.toString();
                    return result;
                }
            return "";
            } catch (Exception ex) {
                 Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            return "Error connecting to server";
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
//            TextView textView = (TextView)findViewById(R.id.resultTxt);
//            textView.setText(s);
            ArrayList<Pharmacie> pharmacies= new ArrayList<Pharmacie>();

            try {
                Gson gson = new Gson();
                Pharmacie[] listePharma= gson.fromJson(s, Pharmacie[].class);
//                JSONArray listeJson=new JSONArray(s);
//                for (int i=0;i<listeJson.length();i++){
//                    JSONObject obj = listeJson.getJSONObject(i);
//                    String nom= obj.getString("Nom");
//                    String ville= obj.getString("Ville");
//                    Double latitude= obj.getDouble("Latitude");
//                    Double longitude= obj.getDouble("Longitude");
//                    Pharmacie pharma=new Pharmacie(nom,ville,
//                            latitude,longitude);
//                    pharmacies.add(pharma);
//                }
                Toast.makeText(MainActivity.this,String.valueOf(listePharma.length),Toast.LENGTH_SHORT).show();

                PharmacieArrayAdapter adapter = new PharmacieArrayAdapter(
                        MainActivity.this,
                        R.layout.liste_layout,
                        listePharma);
                listView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class LoginTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection c=null;
            try {
                String urlString=params[0];
                URL url=new URL(urlString);
                c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("POST");
                c.setConnectTimeout(15000 /* milliseconds */);
                c.setDoInput(true);
                c.setDoOutput(true);
                c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String s = "username="+username+"&password=" + password;

                c.setFixedLengthStreamingMode(s.getBytes().length);
                PrintWriter out = new PrintWriter(c.getOutputStream());
                out.print(s);
                out.close();

                c.connect();
                int mStatusCode = c.getResponseCode();
                switch (mStatusCode) {
                    case 200:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        String result =  sb.toString();
                        return result;
                }
                return "";
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                return "Error connecting to server";
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            TextView textView = (TextView)findViewById(R.id.resultTxt);
            textView.setText(s);
        }
    }
}
