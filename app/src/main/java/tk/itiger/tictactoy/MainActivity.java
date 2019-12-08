package tk.itiger.tictactoy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int[] BUTTONS_ID = {
            R.id.top_left_btn, R.id.top_center_btn, R.id.top_right_btn,
            R.id.center_left_btn, R.id.center_center_btn, R.id.center_right_btn,
            R.id.bottom_left_btn,R.id.bottom_center_btn, R.id.bottom_right_btn
    };

    private final Map<Integer, Button> BUTTONS = new HashMap<>();
    private TicTacLogic LOGIC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtonList();
        initGame();
    }

    public void doGame(View view) {
        LOGIC.userGo(view);
        LOGIC.aiGo();
        System.out.println(LOGIC);

    }

    private void initGame() {
        LOGIC = new TicTacLogic(BUTTONS);
    }

    private void initButtonList() {
        for (int index = 0; index < BUTTONS_ID.length; index++) {
            BUTTONS.put(index, (Button) findViewById(BUTTONS_ID[index]));
        }
    }


}
