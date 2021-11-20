package edu.samuelgarcia.leagueoflogins;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("accounts-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 480, 300);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setTitle("LeagueOfLogins");
        stage.setMinWidth(320);
        stage.setMinHeight(240);
        stage.setMaxWidth(600);
        stage.setMaxHeight(400);

        URL iconUrl = getClass().getResource("icon.gif");
        stage.getIcons().add(new Image(iconUrl.toString()));
        FXTrayIcon trayIcon = new FXTrayIcon(stage, iconUrl);

        MenuItem miAccounts = new MenuItem();
        miAccounts.setText("Accounts");
        miAccounts.setOnAction(e -> stage.show());

        MenuItem miExit = new MenuItem();
        miExit.setText("Exit");
        miExit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        trayIcon.addMenuItem(miAccounts);
        trayIcon.addSeparator();
        trayIcon.addMenuItem(miExit);
        trayIcon.show();

        Thread wakeUp = new Thread(() -> {
            String[] cmd = {"TASKLIST", "/FI", "\"IMAGENAME eq RiotClientUx.exe\""};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            boolean open = false;

            while(true) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(pb.start().getInputStream()))) {
                    boolean prevOpen = open;
                    open = br.lines().count() > 1;

                    if (!prevOpen && open) {
                        Thread.sleep(500);
                        Platform.runLater(stage::show);
                    }

                    Thread.sleep(2000);
                } catch (IOException | InterruptedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        });

        wakeUp.start();
    }

    public static void main(String[] args) {
        launch();
    }
}