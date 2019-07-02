package com.example.turistickaagencijaja;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.turistickaagencijaja.Activity.AtrakcijaPrikaz;
import com.example.turistickaagencijaja.Activity.AtrakcijaUnos;
import com.example.turistickaagencijaja.Adapter.MyAdapter;
import com.example.turistickaagencijaja.DB.Atrakcija;
import com.example.turistickaagencijaja.DB.DataBaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    Toolbar toolbar;
    List<String> drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    List<Atrakcija> atrakcijas;

    DataBaseHelper dataBaseHelper;
    RecyclerView rv_lista;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        fillDataDrawer();
        setupDrawer();
        fillDataRV();
        setUpRV();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_add):
                Intent intent = new Intent(this, AtrakcijaUnos.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }


    public void fillDataDrawer (){
        drawerItems = new ArrayList<>();
        drawerItems.add("Sve atrakcije");
        drawerItems.add("About");
    }

    private void setupDrawer(){
        drawerList = findViewById(R.id.lvDrawer);
        drawerLayout = findViewById(R.id.drawerLy);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i){
                    case 0:
                       //rvLista
                        break;
                    case 1:
                        title = "About";
                      //dialog about
                        break;
                }
                setTitle(title);
                drawerLayout.closeDrawer(drawerList);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
    }

    public DataBaseHelper getDataBaseHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }

    public void fillDataRV (){
        try {
            atrakcijas = getDataBaseHelper().getmAtrakcijaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setUpRV (){
        rv_lista= findViewById(R.id.rv_listaMain);
        rv_lista.setLayoutManager(new LinearLayoutManager(this));
        if (myAdapter == null) {
            try {
                myAdapter = new MyAdapter(getDataBaseHelper().getmAtrakcijaDao().queryForAll(), this);
        }catch (SQLException e){
                e.printStackTrace();
            }
            rv_lista.setAdapter(myAdapter);
        }else {
            myAdapter.notifyDataSetChanged();
        }


    }


    @Override
    public void onItemClick(Atrakcija atrakcija) {
        Intent intent = new Intent(this, AtrakcijaPrikaz.class);
        intent.putExtra("atrakcija_id", atrakcija.getId());
        startActivity(intent);
    }
}
