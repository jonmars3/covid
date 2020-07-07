package pt.isel.poo.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.TimeAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import pt.isel.poo.covid.model.Direction;
import pt.isel.poo.covid.model.Hero;
import pt.isel.poo.covid.model.Level;
import pt.isel.poo.covid.model.Virus;
import pt.isel.poo.covid.tile.TilePanel;
import pt.isel.poo.covid.view.LevelView;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "covid_levels.txt";
    private final String SAVEFILE = "saved_level.txt";
    private final String TESTFILE = "test_file.txt";
    public int currentLevel = 1;
    private Level level ;
    private Scanner in  ;
    private LevelView view;
    private Hero hero;
    private ArrayList <Virus> virusList;
    TextView gameStateText;
    TextView virusText;
    TextView levelText;
    Button okButton;
    private int okCount = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TilePanel panel = findViewById(R.id.levelView);
        virusText = findViewById(R.id.virusText);
        levelText = findViewById(R.id.levelText);
        if(savedInstanceState == null) {

            try {
                initializeLevel(FILENAME, currentLevel, panel);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Loader.LevelFormatException e) {
                e.printStackTrace();
            }


        }
        else{
            try {
                SharedPreferences prefs = getSharedPreferences("PreferencesName", MODE_PRIVATE);
                currentLevel = prefs.getInt("currentLevel", 0);
                loadSavedLevel(TESTFILE,currentLevel,panel);
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
        gameStateText = findViewById(R.id.gameStateMessage);
        okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(gameStateText.getText().toString().equals(getResources().getString(R.string.nothingToLoad))) hideButtons();
                if(gameStateText.getText().toString().equals(getResources().getString(R.string.gameOver))) quit();
                if(gameStateText.getText().toString().equals(getResources().getString(R.string.noMoreLevels))) quit();
                if(gameStateText.getText().toString().equals(getResources().getString(R.string.levelCompleted))){
                    try {
                        currentLevel++;
                        initializeLevel(FILENAME,currentLevel,panel);
                        hideButtons();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Loader.LevelFormatException e) {
                        e.printStackTrace();
                    }
                    if(level == null){
                        showButtons(getResources().getString(R.string.noMoreLevels));
                        System.out.println(level);
                        currentLevel--;
                    }
                }
            }
        });

        Button loadButton = findViewById(R.id.loadButton);

        //load saved file
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if(fileExists(MainActivity.this)) {

                        SharedPreferences prefs = getSharedPreferences("PreferencesName", MODE_PRIVATE);
                        currentLevel = prefs.getInt("savedLevel", 0);
                        loadSavedLevel(SAVEFILE,currentLevel,panel);
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
            if(level!=null)
                    if(!hero.isDead() && level.getVirusCount() > 0){
                        //Movement caused by gravity
                        if (elapsedTime >= interval) {
                            elapsedTime = 0;
                            heroMoveDown();
                            virusMoveDown();
                            updateText();

                        }
                    //Movement cause by player
                        else{
                            elapsedTime += deltaTime;
                            if(hero.getHeroDirection() == Direction.EAST || hero.getHeroDirection() == Direction.WEST){
                                //Virus movement
                                virusMove(hero.getHeroDirection());
                                //Player movement
                                heroMove();
                                //Reset time
                                elapsedTime = 0 ;
                        }
                    }
                    }
                // Player is dead or no more virus on the current level.
                else{
                    //Level Completed / No more Virus
                    if(virusList.size() == 0){
                        showButtons(getResources().getString(R.string.levelCompleted));

                    }
                    //Player is dead
                    else{
                        showButtons(getResources().getString(R.string.gameOver));
                    }
                }
            }
        });
        animator.start();
    }

    public  void setLevel(Scanner in, int currentLevel) throws Loader.LevelFormatException {
        Loader loader = new Loader(in);
        level =  loader.load(currentLevel);

    }

    public void initializeLevel(String fileName,int currentLevel,TilePanel panel) throws IOException, Loader.LevelFormatException {

        in = new Scanner(getAssets().open(fileName));
        setLevel(in,currentLevel);
        if(level!=null){
            view = new LevelView(panel,level);
            hero = level.getHero();
            virusList = level.getVirusList();
            updateText();

        }
    }

    public void heroMove(){
        hero.move();
        hero.changeDirection(Direction.NONE);
        view.onChange(hero.getOldPosition().x,hero.getOldPosition().y,hero.getPosition().x,hero.getPosition().y);

    }

    public void heroMoveDown( ){
        hero.changeDirection(Direction.SOUTH);
        hero.move();
        hero.changeDirection(Direction.NONE);
        view.onChange(hero.getOldPosition().x,hero.getOldPosition().y,hero.getPosition().x,hero.getPosition().y);

    }

    public void virusMove(Direction direction){
        Iterator<Virus> itr = virusList.iterator();
        while (itr.hasNext()){
            Virus virus = itr.next();
            if(hero.getPosition().add(direction).x == virus.getPosition().x &&
            hero.getPosition().add(direction).y == virus.getPosition().y){
                virus.changeDirection(direction);
                virus.move();
                virus.changeDirection(Direction.NONE);
                view.onChange(virus.getOldPosition().x,virus.getOldPosition().y,virus.getPosition().x,virus.getPosition().y);


            }
        }
    }

    public void virusMoveDown(){
        Iterator<Virus> itr = virusList.iterator();
        while (itr.hasNext()){
            Virus virus = itr.next();
            virus.changeDirection(Direction.SOUTH);
            if(virus.isDead())itr.remove();
            virus.move();
            updateText();
            virus.changeDirection(Direction.NONE);
            view.onChange(virus.getOldPosition().x,virus.getOldPosition().y,virus.getPosition().x,virus.getPosition().y);

        }
    }

    public void quit(){
        finish();
        System.exit(0);
    }

    public void hideButtons(){
        gameStateText.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.INVISIBLE);
    }

    public void showButtons(String message){
        gameStateText.setVisibility(View.VISIBLE);
        gameStateText.setText(message);
        okButton.setVisibility(View.VISIBLE);
    }

    public void updateText(){
        levelText.setText(getResources().getString(R.string.level)+ currentLevel);
        virusText.setText(getResources().getString(R.string.virus) + virusList.size());
    }

    public boolean fileExists(Context context) {
        File file = context.getFileStreamPath(SAVEFILE);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public void loadSavedLevel(String fileName,int currentLevel,TilePanel panel) throws IOException, Loader.LevelFormatException {

        in = new Scanner(openFileInput(fileName));
        setLevel(in,currentLevel);
        view = new LevelView(panel,level);
        hero = level.getHero();
        virusList = level.getVirusList();
        updateText();

    }


    //TODO: There might be a better way to do this
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        try (PrintStream output = new PrintStream(openFileOutput(TESTFILE, MODE_PRIVATE))) {
            level.save(output,currentLevel);
            SharedPreferences.Editor editor = getSharedPreferences("PreferencesName", MODE_PRIVATE).edit();
            editor.putInt("currentLevel", currentLevel);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

