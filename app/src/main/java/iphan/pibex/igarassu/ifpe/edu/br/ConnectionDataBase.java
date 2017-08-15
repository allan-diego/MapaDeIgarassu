package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import iphan.pibex.igarassu.ifpe.edu.br.Constants;

/**
 * Created by gabri on 06/06/2017.
 */

public class ConnectionDataBase extends SQLiteOpenHelper{

    public ConnectionDataBase(Context context) {
        super(context, Constants.BD_NOME, null, Constants.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location( " +
                "_id integer primary key autoincrement, " +
                "name text not null, " +
                "longitude double not null, " +
                "latitude double not null, " +
                "address text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table location;");
        onCreate(db);
    }

}