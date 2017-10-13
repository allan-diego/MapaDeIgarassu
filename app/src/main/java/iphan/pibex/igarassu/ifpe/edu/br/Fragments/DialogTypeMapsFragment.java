package iphan.pibex.igarassu.ifpe.edu.br.Fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;

import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.Util.SharedPreferencesUtil;

/**
 * Método de AlertDialog para escolha do tipo do mapa
 */
public class DialogTypeMapsFragment extends DialogFragment {
    public static void alertDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.type_maps)
                .setItems(R.array.type_maps, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_NORMAL);
                            SharedPreferencesUtil.setTypeMaps(context, Constants.MAP_TYPE_NORMAL);
                        } else if (which == 1) {
                            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_HYBRID);
                            SharedPreferencesUtil.setTypeMaps(context, Constants.MAP_TYPE_HYBRID);
                        }
                    }
                });
        builder.show();
    }
}
