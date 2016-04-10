package com.konstantinost.appcompare;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;


public class InstalledApplicationsActivity extends ListActivity {

    TextView androidVersion;
    private CustomAdapter listadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.installed_applications_activity_layout);

        androidVersion = (TextView) findViewById(R.id.androidVersionTextView);
        String androidVersionStr = Build.VERSION.RELEASE;
        androidVersion.setText(androidVersionStr);

        new LoadApplications().execute();

    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            listadapter = new CustomAdapter(InstalledApplicationsActivity.this,
                    R.layout.list_adapter, StartActivity.getRawApplicationsList());

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(InstalledApplicationsActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
