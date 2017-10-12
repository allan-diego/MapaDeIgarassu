package iphan.pibex.igarassu.ifpe.edu.br.Other;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import iphan.pibex.igarassu.ifpe.edu.br.Model.ConnectionFireBaseModel;

public class AddMarkerMapFirebase implements OnMapReadyCallback {

    private Context context;

    public AddMarkerMapFirebase(Context context) {
        this.context = context;
    }

    public void onAddMarker() {

        ConnectionFireBaseModel.getReferenceFirebase()
                .child("locations")
                .addValueEventListener(new ValueEventListenerMarker(this.context));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }

}

