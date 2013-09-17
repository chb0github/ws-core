package org.bongiorno.common;

/**
 * @author cbongiorno
 */
public final class Constants {

    public static final String EMAIL_REGEX = ".+@.+\\..+";
    public static final String HEX_DEC = "[a-fA-F0-9]{0,}";
    public static final String SHA_256 = "[a-fA-F0-9]{64}";
    public static final String PHONE_REGEX = "\\d{10}";
    public static final String BS_HWID_REGEX = "[a-f0-9]{64}";
    public static final String RAIL_HWID_REGEX = "[a-f0-9]{32}";
    public static final String CHARGER_RFID_REGEX = "[a-f0-9]{16}";

    public static final String AZ_DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    private Constants(){

    }
}
