package com.example.lvsc.tp3ti;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Drawer2Activity extends AppCompatActivity {
    String[] items;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer2);

        ListView drawerList=(ListView)findViewById(R.id.drawer_listview);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_closed){
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle("Navigation Drawer");
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                //Toast.makeText(getBaseContext(),"Drawer Closed",Toast.LENGTH_LONG).show();
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation Drawer");
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                //Toast.makeText(getBaseContext(),"Drawer Opened",Toast.LENGTH_LONG).show();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        items=new String[]{
                "Menu 1",
                "Menu 2",
                "Menu 3"
        };
        drawerList.setAdapter(new ArrayAdapter<String>(Drawer2Activity.this,R.layout.drawer_list_item,items));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String element = items[position];
                Toast.makeText(Drawer2Activity.this, element, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
}
