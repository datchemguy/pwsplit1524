package client;

import commons.Expense;
import commons.Flatmate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private ComboBox<Flatmate> payer;
    @FXML private TextField euros;
    @FXML private TextField cents;
    @FXML private TextField item;
    @FXML private ListView<Expense> expenses;
    @FXML private ListView<Debt> debts;
    private ServerClient server;

    public void setServer(ServerClient server) {
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        payer.getItems().setAll(Flatmate.values());
        euros.textProperty().addListener(Utils.numConstraint(euros, 5));
        cents.textProperty().addListener(Utils.numConstraint(cents, 2));
    }

    public void refresh() {
        Expense[] exps = server.getAll();
        if(exps == null) error();
        else {
            expenses.getItems().setAll(exps);
            debts.getItems().setAll(Utils.calculateDebts(exps));
        }
    }

    public void post() {
        boolean ok = true;
        Flatmate who = payer.getValue();
        if(who == null) {
            ok = false;
            payer.setPromptText("Select!");
        }
        String eur = euros.getText(), cnt = cents.getText();
        int howMuch = (eur.isEmpty() ? 0 : Integer.parseInt(eur)) * 100
                + (cnt.isEmpty() ? 0 : Integer.parseInt(cnt));
        if(howMuch <= 0) {
            ok = false;
            euros.setText("");
            euros.setPromptText("For free?");
        }
        String what = item.getText();
        if(what.isBlank()) what = Utils.defaultItem();
        if(ok) {
            server.post(new Expense(who, howMuch, what));
            refresh();
        }
    }

    public void delete() {
        for(Expense e : expenses.getSelectionModel().getSelectedItems())
            if(!server.delete(e)) {
                error();
                return;
            }
        refresh();
    }

    public void keyPress(KeyEvent e) {
        switch(e.getCode()) {
            case F5 -> refresh();
            case ENTER -> post();
            case DELETE -> delete();
        }
    }

    private void error() {
        Alert err = new Alert(Alert.AlertType.ERROR, "server down idk");
        err.setTitle("Error");
        err.setHeaderText("Network Error");
        err.showAndWait();
    }
}