package com.example.turistickaagencijaja.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.turistickaagencijaja.DB.Atrakcija;
import com.example.turistickaagencijaja.DB.DataBaseHelper;
import com.example.turistickaagencijaja.MainActivity;
import com.example.turistickaagencijaja.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtrakcijaPrikaz extends AppCompatActivity {


    Toolbar toolbar;
    List<String> drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    DataBaseHelper dataBaseHelper;
    Atrakcija atrakcija;
    AlertDialog dijalog;


    TextView nazivU;
    TextView opisU;
    TextView adresaU;
    TextView brojU;
    TextView webU;
    TextView radnoVrU;
    TextView cenaU;
    TextView komU;
    ImageView slikaU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atrakcija_prikaz);

        nazivU = findViewById(R.id.tvNazivP);
        opisU = findViewById(R.id.tvOpisP);
        adresaU = findViewById(R.id.tvAdresaP);
        brojU = findViewById(R.id.tvBrojP);
        webU = findViewById(R.id.tvWebP);
        radnoVrU = findViewById(R.id.tvRadnoVrP);
        cenaU = findViewById(R.id.cenaP);
        komU = findViewById(R.id.komP);
        slikaU = findViewById(R.id.ivSlikaP);


        setupToolbar();
        fillDataDrawer();
        setupDrawer();
        getAtrakcijaInfo(getIntent());
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prikaz, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_delete):
                showDialog();
                break;

            case (R.id.action_edi):
                //edit
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarPrikaz);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }

    public void fillDataDrawer() {
        drawerItems = new ArrayList<>();
        drawerItems.add("Sve atrakcije");
        drawerItems.add("About");
    }

    private void setupDrawer() {
        drawerList = findViewById(R.id.lvDrawer);
        drawerLayout = findViewById(R.id.drawerLy);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i) {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }

    public DataBaseHelper getDataBaseHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    public void delete (){
        try {
            getDataBaseHelper().getmAtrakcijaDao().delete(atrakcija);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void getAtrakcijaInfo (Intent intent){
        int atrakcija_id = intent.getIntExtra("atrakcija_id", 1);

        try {
            atrakcija = getDataBaseHelper().getmAtrakcijaDao().queryForId(atrakcija_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nazivU.setText(atrakcija.getNaziv());
        opisU.setText(atrakcija.getOpis());
        adresaU.setText(atrakcija.getAdresa());
        brojU.setText("" + atrakcija.getBrojTelefona());
        webU.setText(atrakcija.getWebAdresa());
        radnoVrU.setText(atrakcija.getRadnoVreme());
        cenaU.setText("" + atrakcija.getCenaUlaznice());
        komU.setText(atrakcija.getKomentari());
        if (atrakcija.getFoto() != null){
            slikaU.setImageBitmap(BitmapFactory.decodeFile(atrakcija.getFoto()));
        }

    }

    private void showDialog(){
        if (dijalog == null){
            dijalog = new Dijalog(this).prepereDialog();
        } else {
            if (dijalog.isShowing()){
                dijalog.dismiss();
            }
        }
        dijalog.show();
    }


    public class Dijalog extends AlertDialog.Builder {
        public Dijalog(Context context) {
            super(context);
            setTitle("Moj Dialog");
            setMessage("Da li ste sigurni da zelite da obrisete ?");
            setPositiveButton("Potrvrdi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete();
                }
            });
            setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        public AlertDialog prepereDialog (){
            AlertDialog dialog = create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }

}
