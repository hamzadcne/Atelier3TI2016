package com.example.lvsc.tp3ti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Drawer2Activity extends AppCompatActivity {
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer2);

        ListView drawerList=(ListView)findViewById(R.id.drawer_listview);

        items=new String[]{
                "Menu 1",
                "Menu 2",
                "Menu 3"
        };
        drawerList.setAdapter(new ArrayAdapter<String>(Drawer2Activity.this,R.layout.drawer_list_item,items));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String element=items[position];
                Toast.makeText(Drawer2Activity.this,element,Toast.LENGTH_LONG).show();
            }
        });
    }
}
