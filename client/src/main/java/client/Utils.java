package client;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

import java.util.Random;

class Utils {
    static String[] placeholders = {"something", "idk", "random shit", "why do you care"};
    static Random rand = new Random();

    static String defaultItem() {
        return defaultItem(rand);
    }

    static String defaultItem(Random rand) {
        return placeholders[rand.nextInt(placeholders.length)];
    }

    static ChangeListener<String> numConstraint(TextField field, final int length) {
        return (_, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
                if(newValue.length() > length) throw new RuntimeException();
            } catch (RuntimeException _) {
                if(!newValue.isEmpty()) field.setText(oldValue);
            }
        };
    }
}
