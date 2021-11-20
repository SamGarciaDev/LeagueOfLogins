module edu.samuelgarcia.leagueoflogins {
    requires javafx.controls;
    requires javafx.fxml;
    requires jnativehook;
    requires FXTrayIcon;
    requires java.desktop;

    exports edu.samuelgarcia.leagueoflogins;

    opens edu.samuelgarcia.leagueoflogins to javafx.fxml, java.datatransfer;
    exports edu.samuelgarcia.leagueoflogins.controllers;
    opens edu.samuelgarcia.leagueoflogins.controllers to java.datatransfer, javafx.fxml;
    opens edu.samuelgarcia.leagueoflogins.models to javafx.fxml, javafx.base;
}