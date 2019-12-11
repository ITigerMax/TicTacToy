package tk.itiger.tictactoy;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;



class TicTacAILogic {


    public static final String X = "X";
    public static final String ZERO = "0";
    private Map<Integer, Button> CELLS;
    private Map<String, Integer> directions = new HashMap<>();
    private Map<String, Integer> aiStep = new HashMap<>();
    private Set<String> zero = new HashSet<>();
    private Set<String> x = new HashSet<>();
    private List<Integer> xButton = new ArrayList<>();
    private List<Integer> zeroButton = new ArrayList<>();
    {   //1
        directions.put("12", 0);
        directions.put("36", 0);
        directions.put("48", 0);
        //2
        directions.put("02", 1);
        directions.put("47", 1);
        //3
        directions.put("01", 2);
        directions.put("46", 2);
        directions.put("56", 2);
        directions.put("58", 2);
        //4
        directions.put("06", 3);
//        directions.put("07", 3);
        directions.put("45", 3);
        //6
        directions.put("27", 5);
        directions.put("28", 5);
        directions.put("34", 5);
        //7
        directions.put("03", 6);
        directions.put("24", 6);
        directions.put("78", 6);
        //8
        directions.put("14", 7);
        directions.put("68", 7);
        //9
        directions.put("04", 8);
        directions.put("25", 8);
        directions.put("67", 8);

        /*-------------------------------*/
        //up-horizontal
        aiStep.put("01", 2);
        aiStep.put("02", 1);
        aiStep.put("12", 0);
        //middle-horizontal
        aiStep.put("34", 5);
        aiStep.put("35", 4);
        aiStep.put("45", 3);
        //down-horizontal
        aiStep.put("67", 8);
        aiStep.put("68", 7);
        aiStep.put("78", 6);
        //right-vertical
        aiStep.put("03", 6);
        aiStep.put("06", 3);
        aiStep.put("36", 0);
        //middle-vertical
        aiStep.put("14", 7);
        aiStep.put("17", 4);
        aiStep.put("47", 1);
        //left-vertical
        aiStep.put("25", 8);
        aiStep.put("28", 5);
        aiStep.put("58", 2);
        //up-left-right-bottom
        aiStep.put("04", 8);
        aiStep.put("08", 4);
        aiStep.put("48", 0);
        //up-right-left-bottom
        aiStep.put("24", 6);
        aiStep.put("26", 4);
        aiStep.put("46", 2);
    }

    private int currentButtonCell;
    private boolean justStarted;
    private boolean validPlayerStep = false;

     TicTacAILogic(Map<Integer, Button> cells) {
       this.CELLS = cells;
       justStarted = true;
    }


     void userGo(View view) {
        Button button = (Button) view;
        if (justStarted){
            lookUpForFirstPlayerStep(button);
        }
        if (button.getText().equals("")){
            button.setText(X);
            xButton.add(button.getId());
            lookUpForPlayersCombinations(X);
            validPlayerStep = true;
        }



    }

     void aiGo(){
        Button button;
       if (justStarted) {
           button = getRandomButton();
           button.setText(ZERO);
           zeroButton.add(button.getId());
           justStarted = false;
           lookUpForPlayersCombinations(ZERO);
       }else {
           doLogic();
           lookUpForPlayersCombinations(ZERO);
       }
       validPlayerStep = false;
    }

    private void doLogic() {
        int a;
        boolean zeroWin = true;
        for (String s: zero) {
            if (aiStep.containsKey(s) || aiStep.containsKey(checkAndReverseString(s))){
                a = aiStep.get(checkAndReverseString(s));
                Button b = CELLS.get(a);
                if (!b.getText().equals("")){
                    continue;
                }else {
                    b.setText(ZERO);
                    zeroButton.add(b.getId());
                    break;
                }
            } else {
                zeroWin = false;
            }
        }

        if (!zeroWin) {
            boolean deadHeat = true;
            boolean aggressiveAttack = true;
            Button b;
            for (String s: x) {
                if (directions.containsKey(s) || directions.containsKey(checkAndReverseString(s))){
                    a = directions.get(checkAndReverseString(s));
                    if (aggressiveAttack){
                        if (!justStarted && checkAndReverseString(s).equals("48") && CELLS.get(0).getText().equals(ZERO)) {
                            b = CELLS.get(2);
                            aggressiveAttack = false;
                        }else if(!justStarted && checkAndReverseString(s).equals("04") && CELLS.get(8).getText().equals(ZERO)){
                            b = CELLS.get(6);
                            aggressiveAttack = false;
                        } else {
                            b = CELLS.get(a);
                            aggressiveAttack = false;
                        }
                    }else {
                        b = CELLS.get(a);
                    }
                    if (!b.getText().equals("")){
                        continue;
                    }else {
                        b.setText(ZERO);
                        zeroButton.add(b.getId());
                        deadHeat = false;
                        break;
                    }
                }
            }
            if (deadHeat) {
                for (Button remainButton : CELLS.values()) {
                    if (remainButton.getText().equals("")){
                        remainButton.setText(ZERO);
                        zeroButton.add(remainButton.getId());
                        return;
                    }
                }
            }
        }
    }


    private Button getRandomButton() {
        Button randomButton;
        if (currentButtonCell != 4){
            randomButton = CELLS.get(4);
        } else {
            Random random = new Random();
            int randInt;
            do{
                randInt = random.nextInt(9);
            }while (randInt == 1 || randInt == 3 || randInt == 4 || randInt == 5 || randInt == 7);
            randomButton = CELLS.get(randInt);
        }
        return randomButton;
    }


    public void lookUpForPlayersCombinations(String playerType){
        Set<String> numbers = new HashSet<>();
        for(Map.Entry<Integer, Button> cells: CELLS.entrySet()){
           if (cells.getValue().getText().equals(playerType)){
               numbers.add(String.valueOf(cells.getKey()));
           }
        }
        List<String> numberList = new ArrayList<>(numbers);
        Collections.sort(numberList);
        for (int i = 0; i < numberList.size(); i++) {
            StringBuilder sb = new StringBuilder();
            if (numberList.size() == 1){
                sb.append(numberList.get(i));
                if (playerType.equals(ZERO)) {
                    zero.add(sb.toString());
                }else {
                    x.add(sb.toString());
                }
                return;
            }
            String currentValue = numberList.get(i);
            for (int j = 0; j < numberList.size(); j++) {
                if (currentValue.equals(numberList.get(j))) continue;
                String addingValue = numberList.get(j);
                sb.append(currentValue).append(addingValue);
                String s = sb.toString();
                if (playerType.equals(ZERO)) {
                    zero.add(sb.toString());
                }else {
                    x.add(sb.toString());
                }
                sb=new StringBuilder();
            }
        }
    }

    private void lookUpForFirstPlayerStep(Button button){
        for (Map.Entry<Integer, Button> cells: CELLS.entrySet()){
            if (cells.getValue().equals(button)){
                currentButtonCell = cells.getKey();
            }
        }
    }

    private String checkAndReverseString(String src) {
        StringBuilder sb = new StringBuilder();
        String[] parts = src.split("");
        Arrays.sort(parts);
        for (String s : parts){
            sb.append(s);
        }
        return sb.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return "Player's combination -> " + x + "\nAI's combinations -> " + zero;
    }

    public ArrayList<String> getZero() {
        return new ArrayList<>(zero);
    }

    public void setZero(Set<String> zero) {
        this.zero = zero;
    }

    public ArrayList<String> getX() {
        return new ArrayList<>(x);
    }

    public void setX(Set<String> x) {
        this.x = x;
    }

    public List<Integer> getXButton() {
        return xButton;
    }

    public void setXButton(List<Integer> xButton) {
        this.xButton = xButton;
    }

    public List<Integer> getZeroButton() {
        return zeroButton;
    }

    public void setZeroButton(List<Integer> zeroButton) {
        this.zeroButton = zeroButton;
    }

    public boolean isJustStarted() {
        return justStarted;
    }

    public void setJustStarted(boolean justStarted) {
        this.justStarted = justStarted;
    }

    public void reloadView() {
        for (Button b: CELLS.values()){
            if (zeroButton.contains(b.getId())){
                b.setText(ZERO);
            }else if (xButton.contains(b.getId())){
                b.setText(X);
            }
        }
    }

    public boolean isValidPlayerStep() {
        return validPlayerStep;
    }
}
