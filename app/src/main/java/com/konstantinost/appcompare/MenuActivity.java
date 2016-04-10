package com.konstantinost.appcompare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_layout);


        final ListView menuOptionsList = (ListView) findViewById(R.id.listview);
        String[] values = new String[]{
                "Applications",
                "Results",
                "About"
        };

        MenuListAdapter adapter = new MenuListAdapter(this, values);
        menuOptionsList.setAdapter(adapter);

        menuOptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String option = String.valueOf(menuOptionsList.getAdapter().getItem(position));

                switch (option) {
                    case "Applications":
                        startActivity(new Intent(MenuActivity.this, InstalledApplicationsActivity.class));
                        break;
                    case "Results":
                        startActivity(new Intent(MenuActivity.this, ResultActivity.class));
                        break;
                    case "About":
                        startActivity(new Intent(MenuActivity.this, AboutActivity.class));
                        break;
                }

            }
        });


    }

}