package com.wildgroup.message;

/**
 * @author Martin Juul Johansen
 * @date 04/02/2019
 */

public class MessageMethods {
    // User methods
    static public final int CREATEUSER = 1 ; // from API;
    static public final int LOGIN = 2 ; // from API;

    // Room methods
    static public final int CREATEROOM = 10; // from API
    static public final int JOINROOM = 11; // from API
    static public final int LEAVEROOM = 12; // from API
    static public final int GETROOMS = 13; //from API
    static public final int SENDCHAT = 14; // from API
    static public final int RECEIVECHAT = 15; // from Server


    // Game methods
    static public final int DEALCARDS = 20; // from Server  // This may be obsolete when we have UPDATEPILES
    static public final int REQUESTCARDSELECT = 21; // from Server
    static public final int SELECTCARDS = 22; // from API
    static public final int SENDSCORE = 23; // from Server
    static public final int UPDATEPILES = 24; // from Server
    static public final int GAMEMESSAGE = 25; // from Server
    static public final int SHOWCARDS = 26; // from Server

    // General methods
    static public final int TOAST = 50;




}
