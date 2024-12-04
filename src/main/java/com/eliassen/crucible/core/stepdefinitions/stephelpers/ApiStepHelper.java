package com.eliassen.crucible.core.stepdefinitions.stephelpers;

import com.eliassen.crucible.core.helpers.TestHelperBase;
import com.eliassen.crucible.core.sharedobjects.CurrentObjectBase;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiStepHelper
{
    public static void objectsInRememberedJSONArrayContainKey(String arrayName, String expectedKey)
    {
        JSONArray jArray = new JSONArray(CurrentObjectBase.retrieve(arrayName));
        int arrayLength = jArray.length();
        int found = 0;

        for (Object object : jArray)
        {
            JSONObject jObject = new JSONObject(object.toString());
            found += jObject.has(expectedKey) ? 1 : 0;
        }

        assertEquals(arrayLength, found, "Only " + found + " of the " + arrayLength + " objects contained " + expectedKey);
    }

    public static void objectInRememberedJSONObjectContainKey(String jsonName, String expectedKey)
    {
        JSONObject json = new JSONObject(CurrentObjectBase.retrieve(jsonName));
        boolean foundExpected = false;
        if(json.toMap().containsKey(expectedKey))
        {
            foundExpected = true;
        }
        assertTrue(foundExpected, "Could not find expected key:" + expectedKey);
    }

    public static void objectInRememberedJSONObjectContainVBoolean(String jsonName, String expectedValue, String key)
    {
        JSONObject json = new JSONObject(CurrentObjectBase.retrieve(jsonName));
        boolean foundExpected = false;
        boolean value;
        if(expectedValue.equals("false"))
        {
            value = false;
            System.out.println("worked");
        }
        else
        {
            value = true;
        }

        if(json.getBoolean(key) == value)
        {
            foundExpected = true;
        }
        assertTrue(foundExpected, "Could not find expected key:" + expectedValue);
    }

    public static void PrintResults(String responseString)
    {
        JSONObject settings = new TestHelperBase().getJSONFileContent("settings.json");
        if (settings.getBoolean("logging"))
        {
            try
            {
                JSONArray array = new JSONArray(responseString);
                array.forEach(System.out::println);
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
