package edu.samuelgarcia.leagueoflogins;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotWriter {
    public static void type(String text) {
        if (!text.isBlank()) {
            try {
                Robot r = new Robot();
                for (int i = 0; i < text.length(); i++) {
                    typeChar(text.charAt(i));
                }
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    public static void tab() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_TAB);
            r.keyRelease(KeyEvent.VK_TAB);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void enter() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static void typeChar(char c) {
        try {
            Robot r = new Robot();

            if (c >= 'A' && c <='Z') {
                r.keyPress(KeyEvent.VK_SHIFT);
                r.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                r.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
                r.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                r.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                r.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
            }
        } catch (AWTException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }
}
