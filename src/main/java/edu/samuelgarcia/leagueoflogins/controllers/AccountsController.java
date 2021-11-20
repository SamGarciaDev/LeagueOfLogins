package edu.samuelgarcia.leagueoflogins.controllers;

import edu.samuelgarcia.leagueoflogins.*;
import edu.samuelgarcia.leagueoflogins.models.*;
import edu.samuelgarcia.leagueoflogins.utils.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AccountsController implements DataSender {
    @FXML
    private TableView<Account> tvAccounts;
    @FXML
    private TableColumn<Account, String> tcAccount;
    @FXML
    private TableColumn<Account, String> tcPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;

    private Account sendableAccount;

    private ObservableList<Account> accounts = FXCollections.observableArrayList(new ArrayList<>());

    private final String filePath = "accounts.csv";

    public void initialize() {
        try {
            accounts.addAll(FileUtils.loadAccounts("accounts.csv"));
        } catch (IOException e) {
            System.err.println("Error reading file.");
        }

        setUpTable();
        setUpButtons();
    }

    private void setUpTable() {
        tcAccount.setCellValueFactory(new PropertyValueFactory<>("Account"));
        tcPassword.setCellValueFactory(new PropertyValueFactory<>("CensoredPassword"));

        tvAccounts.setItems(accounts);
    }

    private void setUpButtons() {
        BooleanBinding binding = Bindings.createBooleanBinding(
                () -> tvAccounts.getSelectionModel().getSelectedIndex() == -1,
                tvAccounts.getSelectionModel().selectedIndexProperty()
        );

        btnLogin.disableProperty().bind(binding);
        btnDelete.disableProperty().bind(binding);
        btnModify.disableProperty().bind(binding);
    }

    @FXML
    private void addAccount(ActionEvent e) {
        Stage caller = (Stage)((Node)(e.getSource())).getScene().getWindow();
        try {
            sendableAccount = null;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/samuelgarcia/leagueoflogins/add-account-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();

            AddAccountController controller = loader.getController();
            controller.setDataSender(this);
            controller.setOnClickListener(stage);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)(e.getSource())).getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (sendableAccount != null) {
                accounts.add(sendableAccount.clone());
                FileUtils.saveAccounts(accounts, filePath);
            }

        } catch (IOException | NullPointerException ex) {
            MessageUtils.showError("Couldn't open window.", caller);
        }
    }

    @FXML
    private void modifyAccount(ActionEvent e) {
        Stage caller = (Stage)((Node)(e.getSource())).getScene().getWindow();
        Account selectedAccount = tvAccounts.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            sendableAccount = null;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/samuelgarcia/leagueoflogins/add-account-view.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();

                AddAccountController controller = loader.getController();
                controller.setDataSender(this);
                controller.loadData(selectedAccount);
                controller.setOnClickListener(stage);

                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(caller);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException | NullPointerException ex) {
                MessageUtils.showError("Couldn't open window.", caller);
            }

            if (sendableAccount != null) {
                selectedAccount.setAccount(sendableAccount.getAccount());
                selectedAccount.setPassword(sendableAccount.getPassword());
                tvAccounts.refresh();
                try {
                    FileUtils.saveAccounts(accounts, filePath);
                } catch (IOException ex) {
                    MessageUtils.showError("Couldn't save to file.", caller);
                }
            }

        }
    }

    @FXML
    private void deleteAccount(ActionEvent e) {
        Stage caller = (Stage)((Node)(e.getSource())).getScene().getWindow();
        Account selectedAccount = tvAccounts.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            var result = MessageUtils.showConfirmation(
                    "Are you sure you want to delete this account?",
                    (Stage)((Node)(e.getSource())).getScene().getWindow()
            );

            if (result.orElse(null) == ButtonType.OK) {
                accounts.remove(selectedAccount);
                try {
                    FileUtils.saveAccounts(accounts, filePath);
                } catch (IOException ex) {
                    MessageUtils.showError("Couldn't save to file.", caller);
                }
            }
        }
    }

    @FXML
    private void login(ActionEvent e) {
        Account selectedAccount = tvAccounts.getSelectionModel().getSelectedItem();

        if (selectedAccount != null) {
            ((Stage)((Node)(e.getSource())).getScene().getWindow()).close();

            RobotWriter.type(selectedAccount.getAccount());
            RobotWriter.tab();
            RobotWriter.type(selectedAccount.getPassword());
            RobotWriter.enter();
        }
    }

    @Override
    public void sendData(Object data) {
        sendableAccount = (Account) data;
    }
}