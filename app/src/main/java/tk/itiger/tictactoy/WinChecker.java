package tk.itiger.tictactoy;

import android.widget.Button;

import java.util.List;

public class WinChecker {

    private int[] buttonsId;
    private List<Button> buttons;


    public WinChecker(int[] buttonsId, List<Button> buttons) {
        this.buttonsId = buttonsId;
        this.buttons = buttons;
    }

    public boolean checkWinner() {
        if (checkTop() || checkCenter() || checkBottom()
        || checkLeft() || checkCenterUpDown() || checkRight()
        || checkDiagonalTopLeftRight() || checkDiagonalTopRightLeft()) {
            return true;
        }
        return false;
    }

    private boolean checkTop(){
        if (checkCell(buttonsId[0])
                && checkCell(buttonsId[1])
                    && checkCell(buttonsId[2])){
            return true;
        }
        return false;
    }

    private boolean checkCenter(){
        if (checkCell(buttonsId[3])
                && checkCell(buttonsId[4])
                    && checkCell(buttonsId[5])){
            return true;
        }
        return false;
    }

    private boolean checkBottom(){
        if (checkCell(buttonsId[6])
                && checkCell(buttonsId[7])
                    && checkCell(buttonsId[8])){
            return true;
        }
        return false;
    }

    private boolean checkLeft(){
        if (checkCell(buttonsId[0])
                && checkCell(buttonsId[3])
                    && checkCell(buttonsId[6])){
            return true;
        }
        return false;
    }

    private boolean checkCenterUpDown(){
        if (checkCell(buttonsId[1])
                && checkCell(buttonsId[4])
                    && checkCell(buttonsId[7])){
            return true;
        }
        return false;
    }

    private boolean checkRight(){
        if (checkCell(buttonsId[2])
                && checkCell(buttonsId[5])
                    && checkCell(buttonsId[8])){
            return true;
        }
        return false;
    }

    private boolean checkDiagonalTopLeftRight(){
        if (checkCell(buttonsId[0])
                && checkCell(buttonsId[4])
                    && checkCell(buttonsId[8])){
            return true;
        }
        return false;
    }

    private boolean checkDiagonalTopRightLeft(){
        if (checkCell(buttonsId[2])
                && checkCell(buttonsId[4])
                    && checkCell(buttonsId[6])){
            return true;
        }
        return false;
    }

    private boolean checkCell(int id){
        for (Button button: buttons){
            if (button.getText().equals(TicTacLogic.ZERO) && button.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
