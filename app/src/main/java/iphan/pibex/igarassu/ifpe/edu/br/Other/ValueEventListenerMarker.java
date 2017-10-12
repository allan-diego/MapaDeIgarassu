package iphan.pibex.igarassu.ifpe.edu.br.Other;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import iphan.pibex.igarassu.ifpe.edu.br.Model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.Util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;

public class ValueEventListenerMarker implements ValueEventListener {

    private Context context;

    /**
     * Método de Listener(esse método ficará ouvindo um evento se por acaso ouver alguma mudança no firebase
     * por exemplo: a adição de um novo ponto).
     * @param context
     */
    public ValueEventListenerMarker(Context context) {
        this.context = context;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/

        DataBaseUtil dataBaseUtil = new DataBaseUtil(this.context);

        for (DataSnapshot dataSnapshot1 : dataSnapshots) { /*Inserindo pontos ao mapa*/

            final LocationModel local = dataSnapshot1.getValue(LocationModel.class);
            GoogleMapsModel.getMap().addMarker(new MarkerOptions()
                    .position(new LatLng(local.getLatitude(), local.getLongitude()))
                    .title(local.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_map)));
            dataBaseUtil.insertLocation(local); /*Inserindo pontos marcados no mapa para o banco local*/

        }

    }

    @Override
    public void onCancelled(DatabaseError error) {}

}
