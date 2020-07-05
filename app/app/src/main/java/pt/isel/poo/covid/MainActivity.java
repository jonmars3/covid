package pt.isel.poo.covid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.TimeAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import pt.isel.poo.covid.model.Direction;
import pt.isel.poo.covid.model.Hero;
import pt.isel.poo.covid.model.Level;
import pt.isel.poo.covid.model.Loader;
import pt.isel.poo.covid.model.Virus;
import pt.isel.poo.covid.tile.TilePanel;
import pt.isel.poo.covid.view.LevelView;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "covid_levels.txt";
    private final String SAVEFILE = "saved_level.txt";
    private final String TESTFILE = "test_file.txt";
    private Level level ;
    private Scanner in  ;
    private int currentLevel = 1;
    private LevelView view;
    private Hero hero;
    TextView gameStateText;
    Button okButton;
    private int okButtonCount = 0; // when there are no more levels to be loaded

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Virus> virusArray = new ArrayList<>();
        final TextView virusText = findViewById(R.id.virusText);
        final TextView levelText = findViewById(R.id.levelText);
        final TilePanel panel = findViewById(R.id.levelView);
        if(savedInstanceState == null) {
            try {
                initializeLevel(FILENAME, currentLevel, panel, levelText, virusText, virusArray);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Loader.LevelFormatException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                    loadSavedLevel(TESTFILE,currentLevel,panel,levelText,virusText,virusArray);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Loader.LevelFormatException e) {
                e.printStackTrace();
            }
        }


        Button saveButton = findViewById(R.id.saveButton);
        //save on file
        //TODO: implement save by just creating a new Level object
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try (PrintStream output = new PrintStream(openFileOutput(SAVEFILE, MODE_PRIVATE))) {
                    SharedPreferences.Editor editor = getSharedPreferences("PreferencesName", MODE_PRIVATE).edit();
                    editor.putInt("savedLevel", currentLevel);
                    editor.apply();

                    level.save(output,currentLevel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hero.changeDirection(Direction.WEST);

            }
        });
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hero.changeDirection(Direction.EAST);


            }
        });

        okButton = findViewById(R.id.okButton);
        gameStateText = findViewById(R.id.gameStateMessage);
        Button loadButton = findViewById(R.id.loadButton);

        //load saved file
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if(fileExists(MainActivity.this)) {

                        SharedPreferences prefs = getSharedPreferences("PreferencesName", MODE_PRIVATE);
                        currentLevel = prefs.getInt("savedLevel", 0);
                        loadSavedLevel(SAVEFILE,currentLevel,panel,levelText,virusText,virusArray);
                        hideButtons();
                    }
                    else {
                        showButtons(getResources().getString(R.string.nothingToLoad));
                        okButton.setOnClickListener( new View.OnClickListener() {
                            public void onClick(View v) {
                                hideButtons();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Loader.LevelFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        final TimeAnimator animator = new TimeAnimator();
        animator.setTimeListener(new TimeAnimator.TimeListener() {
            int elapsedTime = 0;
            int interval = 350;
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (!hero.getDead() && (( virusArray.size() != 0))) {
                    //Gravity effect
                    if (elapsedTime >= interval) {
                        elapsedTime = 0;
                        for (int i = 0; i < virusArray.size(); i++) {
                            virusArray.get(i).changeDirection(Direction.SOUTH);
                            virusArray.get(i).move();
                            //if going towards trashcan
                            if(virusArray.get(i).isDead(Direction.SOUTH)){
                                virusArray.get(i).move();
                                virusArray.remove(i);
                                virusText.setText(getResources().getString(R.string.virus) + virusArray.size());

                            }
                            if(virusArray.size() > 0 && i<virusArray.size()) virusArray.get(i).changeDirection(Direction.NONE); //Restore no direction
                        }
                        heroGravity();

                    }
                    else {
                        //Movement by player
                        if(hero.getHeroDirection() == Direction.EAST || hero.getHeroDirection() == Direction.WEST){
                            //check if hero move towards virus
                            for (int i = 0; i < virusArray.size(); i++) {
                                if((hero.getPosition().add(hero.getHeroDirection()).x == virusArray.get(i).getPosition().x) &&
                                        hero.getPosition().add(hero.getHeroDirection()).y == virusArray.get(i).getPosition().y) {
                                    virusArray.get(i).changeDirection(hero.getHeroDirection());
                                    virusArray.get(i).move();
                                }
                            }
                            elapsedTime =0;
                            heroMove();

                        }
                        elapsedTime += deltaTime;//Increase time

                    }
                    if(level != null)
                        view.onChange(level);
                }
                //Game Over
                else{
                    if(virusArray.size() == 0 ){
                        if(level!=null)showButtons(getResources().getString(R.string.levelCompleted));

                        okButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                try {
                                    in = new Scanner(getAssets().open(FILENAME));
                                    currentLevel++;
                                    level = Level.setLevel(in,currentLevel);


                                    //Next level  not found
                                    if (level == null) {
                                        okButtonCount++;
                                        showButtons(getResources().getString(R.string.noMoreLevels));
                                       if (okButtonCount ==2)quit();
                                    }
                                    //Next level found
                                    else{
                                        hideButtons();
                                        initializeLevel(FILENAME,currentLevel,panel,levelText,virusText,virusArray);
                                    }
                                }
                                catch (Loader.LevelFormatException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else{
                        showButtons(getResources().getString(R.string.gameOver));
                        okButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                quit();
                            }
                        });

                    }
                }
            }
        });
        animator.start();
    }

    public boolean fileExists(Context context) {
        File file = context.getFileStreamPath(SAVEFILE);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public void initializeLevel(String fileName,int currentLevel,TilePanel panel,TextView levelText,TextView virusText,ArrayList virusArray) throws IOException, Loader.LevelFormatException {

        in = new Scanner(getAssets().open(fileName));
        level = level.setLevel(in,currentLevel);
        view = new LevelView(panel,level);
        hero = level.getHero();
        levelText.setText(getResources().getString(R.string.level) + currentLevel);
        for (int i = 0; i < level.getVirusCount(); i++) {
            virusArray.add(level.getVirus(i));
        }
        virusText.setText(getResources().getString(R.string.virus) + virusArray.size());
    }

    public void loadSavedLevel(String fileName,int currentLevel,TilePanel panel,TextView levelText,TextView virusText,ArrayList virusArray) throws IOException, Loader.LevelFormatException {

        in = new Scanner(openFileInput(fileName));
        level = level.setLevel(in,currentLevel);
        view = new LevelView(panel,level);
        hero = level.getHero();
        levelText.setText(getResources().getString(R.string.level) + currentLevel);
        virusArray.clear();
        for (int i = 0; i < level.getVirusCount(); i++) {
            virusArray.add(level.getVirus(i));
        }
        virusText.setText(getResources().getString(R.string.virus) + virusArray.size());

    }

    public void showButtons(String message){
        gameStateText.setVisibility(View.VISIBLE);
        gameStateText.setText(message);
        okButton.setVisibility(View.VISIBLE);
    }
    public void hideButtons(){
        gameStateText.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.INVISIBLE);
    }

    public void heroMove(){
        hero.move();
        hero.changeDirection(Direction.NONE);
    }
    public void heroGravity( ){
        hero.changeDirection(Direction.SOUTH);
        hero.move();
        hero.isDead(Direction.SOUTH);
        hero.changeDirection(Direction.NONE);
    }

    public void quit(){
        finish();
        System.exit(0);
    }


    //TODO: There might be a better way to do this
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        try (PrintStream output = new PrintStream(openFileOutput(TESTFILE, MODE_PRIVATE))) {
            level.save(output,currentLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
