package iphan.pibex.igarassu.ifpe.edu.br.Constants;

import com.google.android.gms.maps.model.LatLng;

public class Constants {

    //constante Banco de Dados
    public static final String BD_NOME = "MapaDeIgarassu";
    public static final int VERSAO_BD = 5;
    public static final String TABLE = "location";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String ADDRESS = "address";
    public static final String DESCRIPTION = "description";

    //tipos de mapa
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    public static final int MAP_TYPE_HYBRID = 4;

    //constante SharedPreferences
    public static final String PREF_NAME = "SharedPreferences";

    //constante center location para o mapa
    public static final LatLng CENTER_LOCATION = new LatLng(-7.834195, -34.906142);

}
