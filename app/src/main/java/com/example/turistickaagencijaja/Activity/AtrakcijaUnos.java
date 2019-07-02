package com.example.turistickaagencijaja.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.turistickaagencijaja.DB.Atrakcija;
import com.example.turistickaagencijaja.DB.DataBaseHelper;
import com.example.turistickaagencijaja.MainActivity;
import com.example.turistickaagencijaja.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtrakcijaUnos extends AppCompatActivity {


    Toolbar toolbar;
    List<String> drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;


    EditText nazivU;
    EditText opisU;
    EditText adresaU;
    EditText brojU;
    EditText webU;
    EditText radnoVrU;
    EditText cenaU;
    EditText komU;
    ImageView slikaU;
    Button btnDodajSlikuU;

    DataBaseHelper dataBaseHelper;

    public static String picturePath;
    private static final int SELECT_PICTURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atrakcija_unos);

        nazivU = findViewById(R.id.etNazivU);
        opisU = findViewById(R.id.etOpusU);
        adresaU = findViewById(R.id.etAdresa);
        brojU = findViewById(R.id.etBrojU);
        webU = findViewById(R.id.etWebU);
        radnoVrU = findViewById(R.id.etRadnoVremeU);
        cenaU = findViewById(R.id.etCenaU);
        komU = findViewById(R.id.etKomU);
        slikaU = findViewById(R.id.ivSlikaU);

        btnDodajSlikuU = findViewById(R.id.btnDodajSlikuU);
        btnDodajSlikuU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSlika();
            }
        });


        setupToolbar();
        fillDataDrawer();
        setupDrawer();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_save):
                addAtrakcija();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarUnos);
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

    public DataBaseHelper getDataBaseHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            showSlika();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            if (selectedImage != null) {
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    slikaU.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
            }
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    private void showSlika() {
        if (isStoragePermissionGranted()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_PICTURE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }


    public void addAtrakcija() {

        Atrakcija a = new Atrakcija();

        a.setNaziv(nazivU.getText().toString());
        a.setOpis(opisU.getText().toString());
        a.setAdresa(adresaU.getText().toString());
        a.setBrojTelefona(Integer.parseInt(brojU.getText().toString()));
        a.setWebAdresa(webU.getText().toString());
        a.setRadnoVreme(radnoVrU.getText().toString());
        a.setCenaUlaznice(Integer.parseInt(cenaU.getText().toString()));
        a.setKomentari(komU.getText().toString());
        a.setFoto(picturePath);

        try {
            getDataBaseHelper().getmAtrakcijaDao().create(a);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}
