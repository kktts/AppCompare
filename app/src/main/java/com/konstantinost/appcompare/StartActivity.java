package com.konstantinost.appcompare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    SharedPreferences settings;
    private String postData = "";
    private PackageManager packageManager = null;
    private static ArrayList<Application> rawApplicationsList = new ArrayList<>();
    private static String resultText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);

        ImageButton start_button = (ImageButton) findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                settings = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
                checkTimeRestriction();

                packageManager = getPackageManager();
                checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
                if (settings.getBoolean("timeRestriction", false) == false) {
                    postData = postString(rawApplicationsList);
                    isNetworkAvailable();
                    if (isNetworkAvailable()) {
                        buildPostData post = new buildPostData();
                        post.execute();
                        Toast.makeText(getApplicationContext(), "Data submitted", Toast.LENGTH_SHORT).show();

                        Calendar date = Calendar.getInstance();
                        Long dateInMilliseconds = date.getTimeInMillis();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putLong("lastPost", dateInMilliseconds);
                        Log.d("hmerominia lastpost:", String.valueOf(settings.getLong("lastPost", 0)));
                        editor.putBoolean("timeRestriction", true);
                        editor.apply();

                    } else {
                        Toast.makeText(getApplicationContext(), "No network available,internet access required", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "You already submitted your info!", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(StartActivity.this, MenuActivity.class));

            }
        });

    }

    private void checkTimeRestriction() {
        Calendar dateNow = Calendar.getInstance();
        Long dateNowInMilliseconds = dateNow.getTimeInMillis();

        if (dateNowInMilliseconds - (settings.getLong("lastPost", dateNowInMilliseconds - 300000)) >= 300000) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("timeRestriction", false);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("timeRestriction", true);
            editor.apply();
        }
    }

    private String postString(ArrayList<Application> LISTA) {
        String data = "";
        data += Security.codeGenerator() + "#";
        for (int i = 0; i < LISTA.size(); i++) {
            data += LISTA.get(i).getPhoneID() + "#" + LISTA.get(i).getAppID() + "#" + LISTA.get(i).getName() + "#" + LISTA.get(i).getPack() + "#" + LISTA.get(i).getVersion();
            if (i != (LISTA.size() - 1)) {
                data += "#";
            }
        }
        return data;
    }

    private String getApplicationVersion(ApplicationInfo info) {
        try {
            PackageInfo pkgInfo = getPackageManager().getPackageInfo(info.packageName, 0);
            int versionCode = pkgInfo.versionCode;
            return String.valueOf(versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "version code error";
        }
    }

    private ArrayList<Application> checkForLaunchIntent(List<ApplicationInfo> list) {
        int i = 1;
        if (rawApplicationsList.size() > 0) {
            rawApplicationsList.clear();
        }
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    String deviceID = getPhoneId();
                    String appID = String.valueOf(i);
                    String name = String.valueOf(info.loadLabel(packageManager));
                    String pack = info.packageName;
                    String version = getApplicationVersion(info);
                    Drawable icon = info.loadIcon(packageManager);
                    rawApplicationsList.add(new Application(deviceID, appID, name, pack, version, icon));
                    i += 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return rawApplicationsList;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private String getPhoneId() {
        Context context = getApplicationContext();
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    private class buildPostData extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            String data = null;
            try {
                data = URLEncoder.encode("post_data", "UTF-8")
                        + "=" + URLEncoder.encode(postData, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(Security.getSiteUrl());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String response = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                con.disconnect();

                resultText = response;

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("result", resultText);
                editor.apply();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static ArrayList<Application> getRawApplicationsList() {
        return rawApplicationsList;
    }

}
