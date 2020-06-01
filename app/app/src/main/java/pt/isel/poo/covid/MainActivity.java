package pt.isel.poo.covid;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.util.Scanner;

import pt.isel.poo.covid.model.Level;
import pt.isel.poo.covid.model.Loader;
import pt.isel.poo.covid.tile.TilePanel;
import pt.isel.poo.covid.view.LevelView;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "covid_levels.txt";
    private Level level ;
    private Scanner in  ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Implement loader better
        try {
            in = new Scanner(getAssets().open(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final TilePanel panel = findViewById(R.id.levelView);

        try {
            level = Level.setLevel(in,1);
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
        new LevelView(panel,level);



    }
}
