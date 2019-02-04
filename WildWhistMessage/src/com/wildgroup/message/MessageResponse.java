package com.wildgroup.message;

/**
 * @author Martin Juul Johansen
 * @date 04/02/2019
 */

public class MessageResponse {
    static public final int CREATEUSER_SUCCESS = 1 ;
    static public final int CREATEUSER_ALREADY_EXITS = 2 ;
    static public final int CREATEUSER_FAILED = 3 ;

    static public final int LOGIN_SUCCESS = 1 ;
    static public final int LOGIN_FAILED = 2 ;
    static public final int LOGIN_PASSWORD_WRONG = 3 ;
    static public final int LOGIN_ALREADY_LOGIN = 4 ;

    static public final int CREATEROOM_SUCCESS = 1 ;
    static public final int CREATEROOM_ALREADY_EXITS = 2 ;

    static public final int GENERAL_SUCCESS = 1 ;
    static public final int GENERAL_FAIL = 2 ;


}
