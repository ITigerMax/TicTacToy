package tk.itiger.tictactoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static int counter;

    private final int[] BUTTONS_ID = {
            R.id.top_left_btn, R.id.top_center_btn, R.id.top_right_btn,
            R.id.center_left_btn, R.id.center_center_btn, R.id.center_right_btn,
            R.id.bottom_left_btn,R.id.bottom_center_btn, R.id.bottom_right_btn
    };

    private Map<Integer, Button> BUTTONS = new HashMap<>();
    private TicTacAILogic LOGIC;
    private WinChecker winChecker;
    private TextView gameStatus;

    MediaPlayer player;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        player = MediaPlayer.create(this, R.raw.ost);
        player.setLooping(true);
        player.start();
        initButtonList();
        initGame();
    }


    public void doGame(View view) {
        LOGIC.userGo(view);
        if (LOGIC.isValidPlayerStep()){
            if (counter++ >= 4) {
                stopGame(getString(R.string.not_bad_result));
            }
            LOGIC.aiGo();
            if (winChecker.checkWinner()) {
               stopGame(getString(R.string.bad_result));
            }
        }

    }


    private void initGame() {
        gameStatus = findViewById(R.id.game_status);
        LOGIC = new TicTacAILogic(BUTTONS);
        winChecker = new WinChecker(BUTTONS_ID, new ArrayList<>(BUTTONS.values()));
    }

    private void initButtonList() {
        for (int index = 0; index < BUTTONS_ID.length; index++) {
            BUTTONS.put(index, (Button) findViewById(BUTTONS_ID[index]));
        }

    }

    private void stopGame(String msg) {
        counter = 0;
        gameStatus.setText(msg);
        for (Button b : BUTTONS.values()) {
            b.setEnabled(false);
        }
        gameStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButtonList();
                initGame();
               for (Button b : BUTTONS.values()){
                   b.setText("");
                   b.setEnabled(true);
                   gameStatus.setText(R.string.gameStatus);
               }
                gameStatus.setOnClickListener(null);
            }
        });

    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
        player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

}
