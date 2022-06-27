package com.aurages.ArestaurantWeb.Utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.aurages.ArestaurantWeb.Aurages;
import com.aurages.ArestaurantWeb.R;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Global {

    public static SQLiteDatabase SqlLitDb;

    public static void openDataBase() throws SQLException {
        SqlLiteDataBase db = new SqlLiteDataBase(Aurages.getContext());
        SqlLitDb = db.getWritableDatabase();
        db.close();
    }

    public static void GetImage(String guid, ImageView v1) {
        if (guid.equals("")) {
            v1.setImageResource(R.drawable.ic_launcher_background);
            return;
        }
        String image = guid + ".png";
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            String fileImage = sdCard.getAbsolutePath() + "/load_image/"
                    + image;

            File file = new File(fileImage);
            if (file.exists()) {
                v1.setImageURI(Uri.parse(fileImage));
            } else {
                v1.setImageResource(R.drawable.ic_launcher_background);
            }

      /*      Glide.with(Aurages.getContext()).load(Uri.parse(fileImage))
                    .into(v1);*/


        } catch (Exception e) {
            v1.setImageResource(R.drawable.ic_launcher_background);
        }
    }


}
