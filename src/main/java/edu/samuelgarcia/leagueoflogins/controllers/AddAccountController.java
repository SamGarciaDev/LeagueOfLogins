package edu.samuelgarcia.leagueoflogins.controllers;

import edu.samuelgarcia.leagueoflogins.models.Account;
import edu.samuelgarcia.leagueoflogins.utils.DataSender;
import edu.samuelgarcia.leagueoflogins.utils.MessageUtils;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAccountController {
    @FXML
    private TextField tfAccount;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnSave;

    private DataSender ds;

    public void initialize() {
        btnSave.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> tfAccount.getText().isBlank() || pfPassword.getText().isBlank(),
                        tfAccount.textProperty(),
                        pfPassword.textProperty()
                )
        );
    }

    public void setOnClickListener(Stage stage) {
        if (stage == null)
            return;
        stage.setOnCloseRequest(
                e -> {
                    var result = MessageUtils.showConfirmation(
                            "Are you sure you want to close the window?",
                            stage
                    );

                    if (result.orElse(null) != ButtonType.OK)
                        e.consume();
                }
        );
    }

    @FXML
    private void exitWithoutSaving(ActionEvent e) {
        Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();

        var result = MessageUtils.showConfirmation(
                "Are you sure you want to close the window?",
                stage
        );

        if (result.orElse(null) == ButtonType.OK)
            stage.close();
    }

    @FXML
    private void exitSaving(ActionEvent e) {
        ds.sendData(new Account(tfAccount.getText(), pfPassword.getText()));
        ((Stage)((Node)(e.getSource())).getScene().getWindow()).close();
    }

    public void loadData(Account account) {
        tfAccount.setText(account.getAccount());
        pfPassword.setText(account.getPassword());
    }

    public void setDataSender(DataSender ds) {
        this.ds = ds;
    }
}
