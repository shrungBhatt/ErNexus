package com.example.andorid.ersnexus.database.attendance;


//This class is of attendance database schema.

public class AttendanceDbSchema {
    public static final class AttendanceTable{
        public static final String NAME = "attendance";

        public static final class Cols{
            public static final String ENROLLMENT_NUMBER = "enrollmentNumber";
            public static final String SUBJECT_CODE = "subjectCode";
            public static final String FACULTY_CODE = "facultyCode";
            public static final String DATE = "date";
            public static final String TIME = "time";


        }
    }
}
