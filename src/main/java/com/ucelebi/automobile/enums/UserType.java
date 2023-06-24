package com.ucelebi.automobile.enums;

public enum UserType {
    BIREYSEL(Constants.BIREYSEL_VALUE),
    KURUMSAL(Constants.KURUMSAL_VALUE);

    UserType(String typeString) {
    }

    public static class Constants {
        public static final String BIREYSEL_VALUE = "BIREYSEL";
        public static final String KURUMSAL_VALUE = "KURUMSAL";
    }
}
