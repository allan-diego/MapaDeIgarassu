package iphan.pibex.igarassu.ifpe.edu.br.ui.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import iphan.pibex.igarassu.ifpe.edu.br.ui.adapter.GoogleInfoWindowAdapter;
import iphan.pibex.igarassu.ifpe.edu.br.ui.dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.ui.fragments.DialogTypeMapsFragment;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.ui.other.InvokeAddMarkerMapOther;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.ui.other.MarkerOther;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.SharedPreferencesUtil;

import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private InvokeAddMarkerMapOther invokeAddMarkerMapOther;
    private View markerView;
    private final Context context;

    public MapActivity() {
        this.context = this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /*SupportMapFragment ==> Mapa*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Cabeçalho do menu*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*Menu*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Inflate para o pop-up dos markers(Janela em cima do marker)*/
        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

        invokeAddMarkerMapOther = new InvokeAddMarkerMapOther(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        GoogleMapsModel.setMap(googleMap);

        /*Verificação de tipos de mapa*/
        if (Constants.MAP_TYPE_HYBRID == SharedPreferencesUtil.getTypeMaps(this)) {
            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_HYBRID);
        } else {
            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_NORMAL);
        }

        if (SharedPreferencesUtil.isNewPoints(this)) {
            invokeAddMarkerMapOther.onAddMarkerFirebase();
            SharedPreferencesUtil.isNewPoints(this, false);
        } else {
            invokeAddMarkerMapOther.onAddMarkerSqlite();
        }

        GoogleMapsModel.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16)); /*Centro do mapa*/
        GoogleMapsModel.getMap().setOnMarkerClickListener(this); /*Listener*/

        /*Botões de Zoom*/
        GoogleMapsModel.getMap().getUiSettings().setZoomControlsEnabled(true);

        infoWindow();

        /*Listener de cada marker*/
        GoogleMapsModel.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                DataBaseUtil dataBaseUtil = new DataBaseUtil(context); /*Instância da base de dados local*/

                String name = marker.getTitle();
                LocationModel locationModel = dataBaseUtil.searchLocation(name);

                if (name.equals(locationModel.getName())) {
                    Intent intent = new Intent(MapActivity.this, SeeMoreActivity.class);
                    Bundle b = new Bundle();
                    b.putString("name", locationModel.getName());
                    b.putString("address", locationModel.getAddress());
                    b.putString("description", locationModel.getDescription());
                    intent.putExtras(b);
                    startActivity(intent);

                }
            }

        });

    }

    /*Método infoWindow, colocar pop-up para todos os marker*/
    private void infoWindow() {

        if (GoogleMapsModel.getMap() != null) {
            GoogleMapsModel.getMap().setInfoWindowAdapter(new GoogleInfoWindowAdapter(markerView));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /*Menu*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) { /*Ação para ir a tela de sobre*/
            Intent intent = new Intent(MapActivity.this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_type_maps) { /*Alert Dialog para escolher o tipo do mapa*/
            DialogTypeMapsFragment.alertDialog(this);

        } else if (id == R.id.nav_check_new_points) {
            invokeAddMarkerMapOther.onAddMarkerFirebase(); /*Adicionando MarkerOther no mapa*/
            SharedPreferencesUtil.isNewPoints(this, false); /* false, há novos pontos */
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Método de back do botão do celular*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager != null ? manager.getSearchableInfo(getComponentName()) : null);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d("Escrito", " "+s);
                DataBaseUtil dataBaseUtil = new DataBaseUtil(context);
                dataBaseUtil.getLinksToSearchResults(s);
                return false;

            }

        });

        return true;

    }


}
