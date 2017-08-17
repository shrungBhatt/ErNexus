package com.example.andorid.ersnexus;

import com.example.andorid.ersnexus.models.AssignmentData;
import com.example.andorid.ersnexus.userprofile.tabs.assignment.Assignments;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void checkParsing(){
        List<AssignmentData> data = Assignments.getAssignmentDatas("[{\"id\":\"1\",\"assignmentname\":\"OOPC practicals\",\"subjectcode\":\"311325\",\"facultycode\":\"456456\",\"date\":\"28\\/04\\/2017\",\"class\":\"4 CE A\",\"details\":\"http:\\/\\/www.ersnexus.esy.es\"},{\"id\":\"2\",\"assignmentname\":\"OOPC practicals\",\"subjectcode\":\"311325\",\"facultycode\":\"456456\",\"date\":\"28\\/04\\/2017\",\"class\":\"4 CE A\",\"details\":\"\"}]");
        assertNotNull(data);

    }


}