package com.example.andorid.ersnexus.database.userdata;


public class UserDbSchema {
    public static final class UserTable{
        public static final String NAME ="users";

        public static final class Cols{
            public static final String UUID = "UUID";
            public static final String ENROLLMENT_NUMBER = "enrollmentNumber";
            public static final String USER_NAME = "username";
            public static final String FULL_NAME = "fullName";
            public static final String PASSWORD = "password";
            public static final String EMAIL = "email";
            public static final String DATE_OF_BIRTH = "dateOfBirth";
        }
    }
}
