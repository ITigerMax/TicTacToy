package tk.itiger.tictactoy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String X_CELLS_MARKED = "x-cells";
    private final String ZERO_CELLS_MARKED = "zero-cells";
    private final String X_COMBINATION= "X_COMBINATION";
    private final String ZERO_COMBINATION = "ZERO_COMBINATION";
    private final String IS_JUST_STARTED = "IS_JUST_STARTED";

    private final int[] BUTTONS_ID = {
            R.id.top_left_btn, R.id.top_center_btn, R.id.top_right_btn,
            R.id.center_left_btn, R.id.center_center_btn, R.id.center_right_btn,
            R.id.bottom_left_btn,R.id.bottom_center_btn, R.id.bottom_right_btn
    };

    private Map<Integer, Button> BUTTONS = new HashMap<>();
    private TicTacLogic LOGIC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtonList();
        loadGame(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(X_COMBINATION, LOGIC.getX());
        savedInstanceState.putStringArrayList(ZERO_COMBINATION, LOGIC.getZero());
        savedInstanceState.putIntegerArrayList(X_CELLS_MARKED, (ArrayList<Integer>) LOGIC.getXButton());
        savedInstanceState.putIntegerArrayList(ZERO_CELLS_MARKED, (ArrayList<Integer>) LOGIC.getZeroButton());
        savedInstanceState.putBoolean(IS_JUST_STARTED, LOGIC.isJustStarted());
    }

    public void doGame(View view) {
        LOGIC.userGo(view);
        if (LOGIC.isValidPlayerStep()){
            LOGIC.aiGo();
        }
    }

    private void loadGame(Bundle state) {
        if (state == null) {
            initGame();
        } else {
            initGame();
            ArrayList<String> xc = state.getStringArrayList(X_COMBINATION);
            ArrayList<String> zc = state.getStringArrayList(ZERO_COMBINATION);
            LOGIC.setX(new HashSet<>(xc));
            LOGIC.setZero(new HashSet<>(zc));
            ArrayList<Integer> xm = state.getIntegerArrayList(X_CELLS_MARKED);
            ArrayList<Integer> zm = state.getIntegerArrayList(ZERO_CELLS_MARKED);
            LOGIC.setXButton(xm);
            LOGIC.setZeroButton(zm);
            LOGIC.setJustStarted(state.getBoolean(IS_JUST_STARTED));
            LOGIC.reloadView();

        }
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
