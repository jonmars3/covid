package pt.isel.poo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TimeAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import pt.isel.poo.covid.R;
import pt.isel.poo.model.Direction;
import pt.isel.poo.model.Level;
import pt.isel.poo.model.Loader;
import pt.isel.poo.model.Location;
import pt.isel.poo.model.Nurse;
import pt.isel.poo.tile.TilePanel;
import pt.isel.poo.view.LevelView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LEVELS_FILE = "covid_levels.txt", SAVE_FILE = "save_state.txt";
    private Level level = new Level (1, 9, 9);
    private LevelView view;
    private Nurse nurse;

    //TODO: Implement FigureFactory

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load first level on startup
        /*try (Scanner in = new Scanner(getAssets().open(LEVELS_FILE))) {
            level = Level.load(in, 1);
        } catch (IOException | Loader.LevelFormatException e) {
            view.printText(R.string.failed_level_load);
        }
*/
        final TilePanel panel = findViewById(R.id.levelView);
        nurse = level.getNurse();
        view = new LevelView (panel, level, nurse);

        TimeAnimator animator = new TimeAnimator ();
        animator.setTimeListener(new TimeAnimator.TimeListener() {
            int elapsedTime = 0;
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (elapsedTime >= 1000) {
                    elapsedTime = 0;
                    Location prevLocation = nurse.getPosition();
                    nurse.move(Direction.DOWN);
                    view.updatePosition(prevLocation);
                }
                elapsedTime += deltaTime;
            }
        });

        animator.start();

        Button saveButton = findViewById((R.id.saveButton));
        Button loadButton = findViewById((R.id.loadButton));
        Button leftMoveButton = findViewById((R.id.leftMoveButton));
        Button rightMoveButton = findViewById((R.id.rightMoveButton));
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        leftMoveButton.setOnClickListener(this);
        rightMoveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Location prevLocation = new Location(nurse.getPosition().x, nurse.getPosition().y);

        switch (v.getId()) {
            case R.id.saveButton:
                try (PrintStream out = new PrintStream(openFileOutput(SAVE_FILE, MODE_PRIVATE))) {
                    level.save(out);
                    view.printText(R.string.level_saved);
                } catch (IOException e) {
                    view.printText(R.string.failed_level_save); }
                break;
            case R.id.loadButton:
                try (Scanner in = new Scanner(openFileInput(LEVELS_FILE))) {
                    level.load(in, level.getNumber());
                    view.printText(R.string.level_loaded);
                } catch (FileNotFoundException | Loader.LevelFormatException noFile) {
                    view.printText(R.string.failed_level_load); }
                break;
            case R.id.leftMoveButton:
                nurse.move(Direction.LEFT);
                break;
            case R.id.rightMoveButton:
                nurse.move(Direction.RIGHT);
                break;
        }

        view.updatePosition(prevLocation);
    }
}
