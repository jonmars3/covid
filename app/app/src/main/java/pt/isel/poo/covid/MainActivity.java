package pt.isel.poo.covid;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import pt.isel.poo.covid.model.Level;
import pt.isel.poo.covid.model.Loader;
import pt.isel.poo.covid.tile.TilePanel;
import pt.isel.poo.covid.view.LevelView;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "covid_levels.txt";
    private final String SAVEFILE = "saved_level.txt";
    private Level level ;
    private Scanner in  ;
    private Button saveButton,loadButton;
    private int currentLevel = 1;
    private int savedLevel;

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

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try (PrintStream output = new PrintStream(openFileOutput(SAVEFILE, MODE_PRIVATE))) {
                    savedLevel = currentLevel;
                    level.save(output,savedLevel);
                    System.out.println(MainActivity.this.getFilesDir().getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.loadButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    in = new Scanner(openFileInput(SAVEFILE));

                } catch (FileNotFoundException | Loader.LevelFormatException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
