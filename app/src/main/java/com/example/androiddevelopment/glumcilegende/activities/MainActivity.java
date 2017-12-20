package com.example.androiddevelopment.glumcilegende.activities;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.adapters.DrawerListAdapter;
import com.example.androiddevelopment.glumcilegende.db.DatabaseHelper;
import com.example.androiddevelopment.glumcilegende.db.model.Film;
import com.example.androiddevelopment.glumcilegende.db.model.Glumac;
import com.example.androiddevelopment.glumcilegende.dialogs.AboutDialog;
import com.example.androiddevelopment.glumcilegende.fragments.DetailFragment;
import com.example.androiddevelopment.glumcilegende.fragments.ListFragment;
import com.example.androiddevelopment.glumcilegende.fragments.ListFragment.OnGlumacSelectedListener;
import com.example.androiddevelopment.glumcilegende.model.NavigationItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;

// Each activity extends Activity class or AppCompatActivity class
public class MainActivity extends AppCompatActivity implements OnGlumacSelectedListener {

   private static final int SELECT_PICTURE = 1;

    // The click listner for ListView in the navigation drawer
     /*
    *Ova klasa predstavlja reakciju na klik neke od stavki iz navigation drawer-a
    *Kljucni element je 'int position' argument koji nam kaze koji tacno element
    *je izabran. To nam je dovoljno da odredimo koju akciju zelimo da pozovemo.
    */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);

        }

    }

    // Attributes used by NavigationDrawer
    //Drawer potrebni elementi
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;
    private CharSequence drawerTitle;
    private CharSequence title;

    private ArrayList<NavigationItem> navigationItems = new ArrayList<NavigationItem>();

    // Attributes used by Dialog
    private AlertDialog dialog;

    // Attributes representing the activity's state
    private boolean landscapeMode = false; // Is the device in the landscape mode?
    private boolean listShown = false; // Is the ListFragment fragment shown?
    private boolean detailShown = false; // Is the DetailFragment fragment shown?

    private int glumacId = 0; // selected item id

    private DatabaseHelper databaseHelper;
    private String imagePath = null;
    private ImageView preview;

    // onCreate method is a lifecycle method called when he activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Each lifecycle method should call the method it overrides
        super.onCreate(savedInstanceState);
        // setContentView method draws UI
        //draws activity`s layout
        setContentView(R.layout.main);

       //Manages NavigationDrawer

        //Populates a list of NavigationDrawer items
        //U navigation drawer postavimo koje to elemente zelimo da imamo. Ikonicu, naziv i kratak opis
        navigationItems.add(new NavigationItem(getString(R.string.drawer_home), getString(R.string.drawer_home_long), R.drawable.ic_action_product));
        navigationItems.add(new NavigationItem(getString(R.string.drawer_settings), getString(R.string.drawer_settings_long), R.drawable.ic_action_settings));
        navigationItems.add(new NavigationItem(getString(R.string.drawer_about), getString(R.string.drawer_about_long), R.drawable.ic_action_about));

        title = drawerTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.navList);

        // Populates NavigtionDrawer with options
        drawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        //Prethodno definisanu listu koja sadrzi ikone, naslov i opis svake stavke postavimo u navigation drawer
        DrawerListAdapter adapter = new DrawerListAdapter(this, navigationItems);

        // Sets a custom shadow that overlays the main content when NavigationDrawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        /* Zelimo da reagujemo na izbog stavki unutar navigation drawer-a.
           Prethodno smo definisali klasu koja ce na osnovu pozicije reagovati
           Ovde povezujemo izbor stavke i kako reagovati */
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerList.setAdapter(adapter);

        // Enable ActionBar app icon to behave as action to toggle nav drawer
        //Prva stvar koja je potrebna za rad sa toolbar-om jeste da ga pronadjemo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Nakon toga potrebno je aplikaciji dopustiti da koristi toolbar
        setSupportActionBar(toolbar);

        //Zbog mera predostroznosti, potrebno je proveriti da li je rad sa toolbar-om moguc.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            //Ako jeste dopustimo klik na tu ikonu da bi mogli da otvaramo/zatvaramo navigation drawer
            actionBar.setDisplayHomeAsUpEnabled(true);
            //postavljamo ikonicu untar toolbar-a
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
            //i prikazujemo ikonu
            actionBar.show();
        }

        /*
        *Zelimo da postignemo da se navigation drawer otvara/zatvara uspesno.
        *Potrebno je da damo kontekst(aktivnost) u kome se prikazuje 'this'
        *toolbar na kojem ce se prikazivati ikona kao i menjati naslov 'toolbar'
        *i dva teksta sta prikazivati kada je navigation drawer otvoren/zatvoren.
        */
        drawerToggle = new ActionBarDrawerToggle(
                this,          /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,              /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            //kada se navigation drawer ne prikazuje zelimo da reagujemo na taj dogadjaj
            public void onDrawerClosed(View view) {
                //postavimo naslov u toolbar
                getSupportActionBar().setTitle(title);
                //i obrisemo sadrzaj toolbar-a.
                //Ako svaka nova aktivnost ili fragment ima drugaciji sadrzaj toolbar-a
                invalidateOptionsMenu(); // Creates call to onPrepareOptionsMenu()
            }
            //reagujemo kada se navigation drawer otvori
            public void onDrawerOpened(View drawerView) {
                //postavimo naslov u toolbar
                getSupportActionBar().setTitle(drawerTitle);
                //i obrisemo sadrzaj toolbar-a.
                //Ako svaka nova aktivnost ili fragment ima drugaciji sadrzaj toolbar-a
                invalidateOptionsMenu(); // Creates call to onPrepareOptionsMenu()
            }

        };

        // Manages fragments

        // If the activity is started for the first time create master fragment
        if(savedInstanceState == null) {
            // FragmentTransaction is a set of changes (e.g. adding, removing and replacing fragments) that you want to perform at the same time.
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ListFragment listFragment = new ListFragment();
            ft.add(R.id.displayList, listFragment, "List_Fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            selectItemFromDrawer(0);
        }

        // If the device is in the landscape mode and the detail fragment is null create detail fragment
        if(findViewById(R.id.displayDetail) != null){
            landscapeMode = true;
            getFragmentManager().popBackStack();

            DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.displayDetail);
            if(detailFragment == null){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                detailFragment = new DetailFragment();
                ft.replace(R.id.displayDetail, detailFragment, "Detail_Fragment1");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                detailShown = true;
            }
        }

        listShown = true;
        detailShown = false;
        glumacId = 0;
    }

    private void reset(){
        imagePath = "";
        preview = null;
    }

    private void addFilm(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_film_layout);

        Button choosebtn = (Button) dialog.findViewById(R.id.choose);
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview = (ImageView) dialog.findViewById(R.id.preview_image);
                selectPicture();
            }
        });

        final EditText glumacName = (EditText) dialog.findViewById(R.id.glumac_name);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = glumacName.getText().toString();

                if(preview == null || imagePath == null){
                    Toast.makeText(MainActivity.this, "Morate izabrati sliku", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Morate uneti naziv filma", Toast.LENGTH_SHORT).show();
                    return;
                }

                Film film = new Film();
                film.setName(name);
                film.setImage(imagePath);

                try{
                    getDatabaseHelper().getFilmDao().create(film);
                    refresh();
                    Toast.makeText(MainActivity.this, "Film dodat", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    reset();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void selectPicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Izaberite fotograiju"), SELECT_PICTURE);
    }

    /**
     * Sistematska metoda koja se automatski poziva ako se
     * aktivnost startuje u startActivityForResult rezimu
     *
     * Ako je to slucaj i ako je sve proslo ok, mozemo da izvucemo
     * sadrzaj i isti prikazemo. Rezultat NIJE slika nego URI do te slike.
     * Na osnovu toga mozemo dobiti tacnu putanju do slike ali i samu sliku
     * */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    if(selectedImageUri != null){
                        imagePath = selectedImageUri.toString();
                    }
                    if (preview != null){
                        preview.setImageBitmap(bitmap);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //da bi dodali podatak u bazu, potrebno je da napravimo objekat klase
    //koji reprezentuje tabelu i popunimo podacima
    private void addItem() throws SQLException {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);

       Button choosebtn = (Button) dialog.findViewById(R.id.choose);
       choosebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               preview = (ImageView) dialog.findViewById(R.id.preview_image);
               selectPicture();
           }
       });

        final Spinner glumaciSpinner = (Spinner) dialog.findViewById(R.id.glumac_film);
        List<Film> list = getDatabaseHelper().getFilmDao().queryForAll();
        ArrayAdapter<Film> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        glumaciSpinner.setAdapter(dataAdapter);
        glumaciSpinner.setSelection(0);

        final EditText glumacName = (EditText) dialog.findViewById(R.id.glumac_name);
        final EditText glumacBiog = (EditText) dialog.findViewById(R.id.glumac_biografija);
        final EditText glumacRating = (EditText) dialog.findViewById(R.id.glumac_rating);

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = glumacName.getText().toString();
                    String biog = glumacBiog.getText().toString();
                    //Voditi racuna kada radimo sa brojevima. Ono sto unesemo mora biti broj
                    //da bi formater uspeo ispravno da formatira broj. Dakle ono sto unesemo
                    //bice u teksutalnom obliku, i mora biti moguce pretrovirit u broj.
                    //Ako nije moguce pretvoriti u broj dobicemo NumberFormatException
                    //Zato je dobro za input gde ocekujemo da stavimo broj, stavimo u xml-u
                    //da ce tu biti samo unet broj npr android:inputType="number|numberDecimal"
                    float zvezdice = Float.parseFloat(glumacRating.getText().toString());

                    Film film = (Film) glumaciSpinner.getSelectedItem();

                    if (name.isEmpty()){
                        Toast.makeText(MainActivity.this, "Morate uneti ime i prezime glumca", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (biog.isEmpty()){
                        Toast.makeText(MainActivity.this, "Moarte uneti osnovnu biografiju glumca", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (film == null){
                        Toast.makeText(MainActivity.this, "Morate izabrati zanr filma", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (preview == null || imagePath == null){
                        Toast.makeText(MainActivity.this, "Morate izabrati fotografiju", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Glumac glumac = new Glumac();
                    glumac.setmName(name);
                    glumac.setBiografija(biog);
                    glumac.setRating(zvezdice);
                    glumac.setImage(imagePath);
                    glumac.setFilm(film);

                    getDatabaseHelper().getGlumacDao().create(glumac);
                    refresh();
                    Toast.makeText(MainActivity.this, "Glumac dodat", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    reset();

                }catch (SQLException e){
                    e.printStackTrace();

                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Rating mora biti broj", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (dataAdapter.isEmpty()){
            Toast.makeText(MainActivity.this, "Ne postoji ni jedan unet film, molim Vas da prvo unesete film.", Toast.LENGTH_SHORT).show();

        }

        dialog.show();
    }

    /**Toolbar**/
    /**
     *Ova metoda 'ubacuje' sadrzaj 'R.menu.activity_item_detail' fajla unutar toolbar-a
     *Svaki pojedinacan element 'R.menu.activity_item_detail' fajla ce biti jedno dugme
     *na toolbar-u
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_item_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // onOptionsItemSelected method is called whenever an item in the Toolbar is selected.
    /**
     *Metoda koja je zaduzena za rekaciju tj dogadjaj kada se klikne na neki
     *od dugmica u toolbar-u. Pozivom 'item.getItemId()' dobijamo jedinstveni identifikator
     *kliknutog dugmeta na osnovu cega mozemo da odredimo kako da reagujemo.
     *Svaki element unutar 'R.menu.activity_item_detail' fajla ima polje id na osnovu koga
     *lako mozemo odrediti sta smo tacno kliknuli.
     */

    /**
     *
     * Metoda koja je izmenjena da reflektuje rad sa Asinhronim zadacima
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refresh();
                break;
            case R.id.action_add:
                try {
                    addItem();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
           case R.id.action_add_film:
                addFilm();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // refresh() prikazuje novi sadrzaj.Povucemo nov sadrzaj iz baze i popunimo listu
    private void refresh(){
        ListView listview = (ListView) findViewById(R.id.glumci);

        if (listview != null){
            ArrayAdapter<Glumac> adapter = (ArrayAdapter<Glumac>) listview.getAdapter();

            if (adapter != null)
            {
                try {
                    adapter.clear();
                    List<Glumac> list = getDatabaseHelper().getGlumacDao().queryForAll();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }

    // Overrides setTitle method to support older api levels
    @Override
    public void setTitle(CharSequence title){
        //Namena ove metode jeste da promeni naslov koji se prikazuje unutar
        //toolbar-a
        getSupportActionBar().setTitle(title);
    }

    // Method(s) that manage NavigationDrawer.
    // onPostCreate method is called ofthen onRestoreInstanceState to synchronize toggle state
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    // onConfigurationChanged method is called when the device configuration changes to pass configuration change to the drawer toggle
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // selectItemFromDrawer method is called when NavigationDrawer item is selected to update UI accordingly
    /*
    *Ova metoda reaguje na izbor neke od stavki iz navigation drawer-a
    *Na osnovu pozicije iz liste navigation drawer-a odredimo sta tacno
    *zelimo da odradimo.
    */
    private void selectItemFromDrawer(int position) {
        if (position == 0) {
            // MainActivity is already shown
        } else if (position == 1) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
        } else if (position == 2) {
            if (dialog == null) {
                dialog = new AboutDialog(MainActivity.this).prepareDialog();
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            // To not create dialog class
            // call it from here.
            dialog.show();
        }
        //ovom linijom oznacavamo element iz liste da je selektovano.
        //Pozadinska boja elementa ce biti promenjena.
        drawerList.setItemChecked(position, true);
        //menjamo naslov
        setTitle(navigationItems.get(position).getTitle());
        //kada odradimo neku akciju zatvorimo navigation drawer
        drawerLayout.closeDrawer(drawerPane);

    }

    @Override
    public void onGlumacSelected(int id) {

        glumacId = id;

        try {

        Glumac glumac = getDatabaseHelper().getGlumacDao().queryForId(id);

        if (landscapeMode) {
            DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.displayDetail);
            detailFragment.updateGlumac(glumac);
        } else {
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setGlumac(glumac);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.displayList, detailFragment, "Detail_Fragment2");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack("Detail_Fragment2");
            ft.commit();
            listShown = false;
            detailShown = true;

        }
    } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed(){
        if(landscapeMode){
            finish();
        }else if (listShown == true){
            finish();
        }else if (detailShown == true) {
            getFragmentManager().popBackStack();
            ListFragment listFragment = new ListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.displayList, listFragment, "List_Fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            listShown = true;
            detailShown = false;
        }
    }

    //Metoda za komunikaciju sa bazom podataka
    public DatabaseHelper getDatabaseHelper(){
        if (databaseHelper == null){
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // nakon rada sa bazom podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


}
