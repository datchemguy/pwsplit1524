package client;

import commons.Expense;
import commons.Flatmate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ObservableList<Expense> expenses;
    @FXML private ListView<Expense> expensesView;
    private ServerClient server;

    public void setServer(ServerClient server) {
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        payer.getItems().setAll(Flatmate.values());
        euros.textProperty().addListener(Utils.numConstraint(euros, 5));
        cents.textProperty().addListener(Utils.numConstraint(cents, 2));
        expenses = FXCollections.observableArrayList();
        expensesView.setItems(expenses);
    }

    public void refresh() {
        expenses.setAll(server.getAll());
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
            server.post(new Expense(0, who, howMuch, what));
            refresh();
        }
    }

    public void delete() {
        expensesView.getSelectionModel()
                .getSelectedItems()
                .forEach(server::delete);
        refresh();
    }

    public void keyPress(KeyEvent e) {
        switch(e.getCode()) {
            case F5 -> refresh();
            case ENTER -> post();
            case DELETE -> delete();
        }
    }
}