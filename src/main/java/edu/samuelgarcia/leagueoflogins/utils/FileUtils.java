package edu.samuelgarcia.leagueoflogins.utils;

import edu.samuelgarcia.leagueoflogins.models.Account;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<Account> loadAccounts(String path) throws IOException {
        List<Account> accounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.lines().forEach(line -> {
                try {
                    if (line.split(";").length != 2)
                        throw new ParseException("Wrong format.", 0);

                    accounts.add(new Account(
                            line.split(";")[0],
                            line.split(";")[1]
                    ));
                } catch (ParseException ex) {
                    System.err.println();
                }
            });
        }

        return accounts;
    }

    public static void saveAccounts(List<Account> accounts, String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            accounts.forEach(acc -> {
                try {
                    bw.write(acc.toString() + "\n");
                } catch (IOException ignored) {}
            });
        }
    }
}
