package iphan.pibex.igarassu.ifpe.edu.br.Other;


import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import iphan.pibex.igarassu.ifpe.edu.br.Dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.Model.ConnectionFireBaseModel;

public class InvokeAddMarkerMapFirebaseOther implements OnMapReadyCallback {

    private Context context;

    public InvokeAddMarkerMapFirebaseOther(Context context) {
        this.context = context;
    }

    public void onAddMarker() {

        ConnectionFireBaseModel.getReferenceFirebase().onDisconnect();

        InvokeProgressDialog.progressDialogStart(context, "Aguarde", "Os pontos estão sendo carregados..."); //Exibindo janela de progresso
        ConnectionFireBaseModel.getReferenceFirebase()
                .child("locations")
                .addValueEventListener(new ValueEventListenerMarkerOther(this.context));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }

}

