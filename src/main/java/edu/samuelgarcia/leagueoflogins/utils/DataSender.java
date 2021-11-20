package edu.samuelgarcia.leagueoflogins.utils;

/**
 * Interface to allow communication of data between controllers.
 */
public interface DataSender {
    /**
     *
     * @param data Object to be sent from one controller to another.
     */
    public void sendData(Object data);
}
