package com.smartjob.users.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[cl/com/org]{2,})$";
    public static final String EMAIL_EXISTS_MESSAGE = "El email ya existe en BD";
    public static final String EMAIL_NOT_EXISTS_MESSAGE = "El email no existe en BD";
    public static final String PASSWORD_INVALID_MESSAGE = "La contraseña es incorrecta";

}

