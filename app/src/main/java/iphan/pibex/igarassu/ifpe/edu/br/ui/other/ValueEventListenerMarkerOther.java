package iphan.pibex.igarassu.ifpe.edu.br.ui.other;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import iphan.pibex.igarassu.ifpe.edu.br.ui.dialog.InvokeProgressDialog;
import iphan.pibex.igarassu.ifpe.edu.br.model.ConnectionFireBaseModel;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;

public class ValueEventListenerMarkerOther implements ValueEventListener {

    private DataBaseUtil dataBaseUtil;

    /**
     * Método de Listener(esse método ficará ouvindo um evento se por acaso ouver alguma mudança no firebase
     * por exemplo: a adição de um novo ponto).
     *
     */

    public ValueEventListenerMarkerOther(DataBaseUtil dataBaseUtil) {
        this.dataBaseUtil = dataBaseUtil;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/

        this.dataBaseUtil.dropTable(); //drop table

        for (DataSnapshot dataSnapshot1 : dataSnapshots) { /*Inserindo pontos ao mapa*/

            final LocationModel local = dataSnapshot1.getValue(LocationModel.class);
            this.dataBaseUtil.insertLocation(local); /*Inserindo pontos marcados no mapa para o banco local*/
            MarkerOther.marker(local.getName(), local.getLatitude(), local.getLongitude()); //Add marker

        }

        InvokeProgressDialog.progressDialogDismiss(); //Fechando progress Dialog
        ConnectionFireBaseModel.getReferenceFirebase().onDisconnect(); //Fechando conexão do firebase.

    }

    @Override
    public void onCancelled(DatabaseError error) {
    }

}
