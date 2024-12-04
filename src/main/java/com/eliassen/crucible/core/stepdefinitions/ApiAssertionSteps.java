package com.eliassen.crucible.core.stepdefinitions;

import com.eliassen.crucible.core.helpers.ApiHelper;
import com.eliassen.crucible.core.helpers.Logger;
import com.eliassen.crucible.core.sharedobjects.CurrentObjectBase;
import com.eliassen.crucible.core.stepdefinitions.stephelpers.ApiStepHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiAssertionSteps
{
    @Then("The response {string} array should contain an object with the key {string} with the value {string}")
    public void theResponseArrayShouldContainAnObjectWithTheKeyWithTheValue(String arrayName, String key, String value) {

        boolean foundValue = false;
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        JSONArray array = response.getJSONArray(arrayName);

        for(Object object : array)
        {
            JSONObject json = (JSONObject) object;
            if(json.has(key))
            {
                if(json.getString(key).equals(value))
                {
                    foundValue = true;
                }
            }
        }
        assertTrue(foundValue,"Did not find the value: " + value + " in the response body");
    }

    @And("The response status code should be {string}")
    @Then("The response should contain the error code {string}")
    public void theResponseShouldContainTheErrorCode(String errorCode)
    {
        String actualValue = CurrentObjectBase.retrievePersisted("responsecode");
        assertEquals(errorCode, actualValue, "Expected " + errorCode + "  but it was " + actualValue);
    }

    @Then("The response should contain the key {string} with the integer value {int}")
    public void theResponseShouldContainTheKeyWithTheIntegerValue(String expectedKey, int value)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        boolean hasValue = false;
        boolean hasExpectedKey = false;
        String actualValue = "not present";
        if (response.has(expectedKey))
        {
            hasExpectedKey = true;

            if(response.get(expectedKey).equals(value))
            {
                hasValue = true;
            }
        }

        assertTrue(hasExpectedKey, "Expected " + expectedKey + " to not be empty, but it was " + actualValue);
        assertTrue(hasValue, "Expected " + value + "  but it was " + actualValue);
    }

    @Then("The code in the stored {string} is 200")
    public void theResponseCodeIs200(String code)
    {
        String string = CurrentObjectBase.retrieve(code);
        assertEquals("200", string);
    }

    @Then("The response object should contain a {string} object with the key {string} with the String value {string}")
    public void theResponseShouldContainAnObjectWithTheKeyWithAValueOf(String objectKey, String expectedKey, String value)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        String failMessage = "";
        boolean hasExpectedKey = false;
        boolean hasValue = false;
        String actualValue = "not present";
        if (response.has(objectKey))
        {
            JSONObject expectedObject = response.getJSONObject(objectKey);
            if (expectedObject.has(expectedKey))
            {
                hasExpectedKey = true;

                if (expectedObject.get(expectedKey).toString().trim().equals(value))
                {
                    hasValue = true;
                }

            } else {
                failMessage = expectedKey;
            }

        } else {
            failMessage = objectKey;
        }

        assertTrue(hasExpectedKey, failMessage + " was not present");
        assertTrue(hasValue, "Expected " + expectedKey + " to be " + value + " but it was " + actualValue);
    }

    @Then("The response object should contain a {string} object with the key {string} with the integer value {int}")
    public void theResponseShouldContainAnObjectWithTheKeyWithAValueOf(String objectKey, String expectedKey, int value)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        String failMessage = "";
        boolean hasExpectedKey = false;
        boolean hasValue = false;
        String actualValue = "not present";
        if (response.has(objectKey))
        {
            JSONObject expectedObject = response.getJSONObject(objectKey);
            if (expectedObject.has(expectedKey))
            {
                hasExpectedKey = true;

                if (expectedObject.get(expectedKey).equals(value))
                {
                    hasValue = true;
                }

            } else {
                failMessage = expectedKey;
            }

        } else {
            failMessage = objectKey;
        }

        assertTrue(hasExpectedKey, failMessage + " was not present");
        assertTrue(hasValue, "Expected " + expectedKey + " to be " + value + " but it was " + actualValue);
    }

    @Then("The response object should contain (a)(an) {string} array with an object with the key {string}")
    public void theResponseObjectShouldContainAnArrayWithAnObjectWithTheKey(String arrayKey, String expectedKey)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        String failMessage = "";
        boolean hasExpectecKey = false;
        if (response.has(arrayKey))
        {
            JSONArray expectedArray = response.getJSONArray(arrayKey);
            for (Object object : expectedArray)
            {
                if (object instanceof JSONObject)
                {
                    JSONObject json = (JSONObject) object;
                    if (json.has(expectedKey))
                    {
                        hasExpectecKey = true;
                    } else {
                        failMessage = expectedKey;
                    }
                }
            }
        } else {
            failMessage = arrayKey;
        }
        assertTrue(hasExpectecKey, failMessage + " was not present");
    }

    @Then("The response object should contain aan {string} array with innerarray {string} object with the key {string}")
    public void theResponseObjectShouldContainAnArrayWithInnerArrayObjectWithTheKey(String array, String innerarray, String expectedKey)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        JSONArray dataSet = response.getJSONArray(array);
        JSONObject response1 = dataSet.getJSONObject(0);
        JSONArray array1 = response1.getJSONArray(innerarray);
        JSONObject response2 = array1.getJSONObject(0);
        String failMessage = "";
        boolean hasExpectecKey = false;
        if (response2.has(expectedKey)) {
            hasExpectecKey = true;
        } else {
            failMessage = expectedKey;
        }
        assertTrue(hasExpectecKey, failMessage + " was not present");
    }

    @Then("The response object should contain aan {string} array with innerarray {string} inside innerarray {string} object with the key {string}")
    public void theResponseObjectShouldContainAnArrayWithInnerarrayInsideInnerarrayObjectWithTheKey(String array, String innerarray1, String innerarray, String expectedKey)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        JSONArray dataSet = response.getJSONArray(array);
        JSONObject response1 = dataSet.getJSONObject(0);
        JSONArray array1 = response1.getJSONArray(innerarray);
        JSONObject response2 = array1.getJSONObject(0);
        JSONArray array2 = response2.getJSONArray(innerarray1);
        JSONObject response3 = array2.getJSONObject(0);
        String failMessage = "";
        boolean hasExpectecKey = false;
        if (response3.has(expectedKey)) {
            hasExpectecKey = true;
        } else {
            failMessage = expectedKey;
        }
        assertTrue(hasExpectecKey, failMessage + " was not present");
    }

    @Then("The response object should contain (a)(an) {string} array with (a)(an) {string} object with the key {string}")
    public void theResponseObjectShouldContainAnArrayWithAnObjectWithTheKey(String arrayKey, String objectKey, String expectedKey)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        String failMessage = "";
        boolean hasExpectecKey = false;
        if (response.has(arrayKey))
        {
            JSONArray expectedArray = response.getJSONArray(arrayKey);
            for (Object object : expectedArray)
            {
                if (object instanceof JSONObject)
                {
                    JSONObject json = (JSONObject) object;

                    if (json.has(objectKey))
                    {
                        JSONObject expectedObject = json.getJSONObject(objectKey);
                        if (expectedObject.has(expectedKey))
                        {
                            hasExpectecKey = true;
                        } else {
                            failMessage = expectedKey;
                        }
                    } else {
                        failMessage = objectKey;
                    }
                }
            }
        } else {
            failMessage = arrayKey;
        }
        assertTrue(hasExpectecKey, failMessage + " was not present");
    }

    @Then("The response should contain the key {string}")
    public void theResponseShouldContainTheKey(String expectedKey) {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        assertTrue(response.has(expectedKey));
    }

    @Then("The response array objects should contain the key {string}")
    public void theResponseArrayObjectsShouldContainTheKey(String expectedKey)
    {
        ApiStepHelper.objectsInRememberedJSONArrayContainKey(ApiHelper.PAYLOAD, expectedKey);
    }

    @Then("The response should contain {int} objects with the key {string}")
    public void theResponseShouldContainObjectsWithTheKey(int expected, String expectedKey)
    {
        JSONArray jArray = new JSONArray(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        int foundvrblCd = 0;
        for (Object group : jArray)
        {
            JSONObject jsonObject = new JSONObject(group.toString());
            if (jsonObject.has(expectedKey))
            {
                foundvrblCd++;
            }
        }

        assertEquals(expected, foundvrblCd);
    }

    @Then("Each member of my saved array, {string}, has the key {string}")
    public void eachMemberOfMySavedArrayHasTheKey(String arrayName, String expectedKey)
    {
        ApiStepHelper.objectsInRememberedJSONArrayContainKey(arrayName, expectedKey);
    }

    @Then("The response should contain the key {string} with the value {string}")
    public void theResponseShouldContainTheKeyWithTheValue(String key, String value)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        boolean hasValue = false;
        String actualValue = "not present";
        if (response.has(key) && response.get(key).equals(value))
        {
            hasValue = true;
            actualValue = response.get(key).toString();
        }

        assertTrue(hasValue, "Expected " + key + " to be " + value + " but it was " + actualValue);
    }

    @Then("The response should contain the key {string} with a value")
    public void theResponseShouldContainTheKeyWithAValue(String key)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        boolean hasValue = false;
        String actualValue = "not present";
        if (response.has(key))
        {
            String value = response.get(key).toString();
            if (!value.isEmpty() && !value.toLowerCase(Locale.ROOT).equals("null"))
            {
                hasValue = true;
                actualValue = response.get(key).toString();
            }
        }

        assertTrue(hasValue, "Expected " + key + " to not be empty, but it was " + actualValue);
    }

    @Then("The response should contain (a)(an) {string} object with the key {string}")
    public void theResponseShouldContainAnObjectWithTheKey(String objectKey, String expectedKey)
    {
        JSONObject response = ApiHelper.getPayloadFromStorage();
        String failMessage = "";
        boolean hasExpectecKey = false;
        if (response.has(objectKey))
        {
            JSONObject expectedObject = response.getJSONObject(objectKey);
            if (expectedObject.has(expectedKey))
            {
                hasExpectecKey = true;
            } else {
                failMessage = expectedKey;
            }
        } else {
            failMessage = objectKey;
        }

        assertTrue(hasExpectecKey, failMessage + " was not present");
    }

    @Then("The response data array objects should contain {string}")
    public void theResponseDataArrayObjectsShouldContainKey(String expectedKey)
    {
        JSONArray data = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD)).getJSONArray("data");
        CurrentObjectBase.store("array",data.toString());
        ApiStepHelper.objectsInRememberedJSONArrayContainKey("array",expectedKey);
    }

    @Then("The response data array objects should contain {string} if there is data")
    public void theResponseDataArrayObjectsShouldContainKeyIfThereIsData(String expectedKey)
    {
        JSONArray data = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD)).getJSONArray("data");
        if(data.length() == 0)
        {
            Logger.log("Payload was empty");
            return;
        }
        CurrentObjectBase.store("array",data.toString());
        ApiStepHelper.objectsInRememberedJSONArrayContainKey("array",expectedKey);
    }

    @Then("The response dataSet array objects should contain {string}")
    public void theResponseDataSetArrayObjectsShouldContainKey(String expectedKey)
    {
        JSONArray data = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD)).getJSONArray("dataSet");
        CurrentObjectBase.store("array",data.toString());
        ApiStepHelper.objectsInRememberedJSONArrayContainKey("array",expectedKey);
    }

    @Then("The response payload should contain {string}")
    public void theResponsePayloadShouldContainKey(String expectedKey)
    {
        JSONObject data = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        CurrentObjectBase.store("key", data.toString());
        ApiStepHelper.objectInRememberedJSONObjectContainKey("key", expectedKey);
    }

    @Then("The response payload should contain value of {string} in key {string}")
    public void theResponsePayloadShouldContainKeyValueOf(String expectedValue, String keyValue)
    {
        JSONObject data = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        CurrentObjectBase.store("key", data.toString());
        ApiStepHelper.objectInRememberedJSONObjectContainVBoolean("key", expectedValue, keyValue);
    }

    @Then("the total matches the number of invoices returned")
    public void theTotalMatchesTheNumberOfInvoicesReturned()
    {
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        JSONArray data = response.getJSONArray("data");
        int invoiceCount = data.length();
        int total = response.getInt("total");
        assertEquals(total,invoiceCount);
    }

    @Then("the total matches the number of data invoices returned")
    public void theTotalMatchesTheNumberOfDataInvoicesReturned()
    {
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        JSONArray data = response.getJSONArray("dataSet");
        int invoiceCount = data.length();
        int total = response.getInt("rowTotalCount");
        assertEquals(total,invoiceCount);
    }

    @Then("The {string} array should contain {string} total number of objects")
    public void theArrayShouldContainTotalNumberOfObjects(String arrayName, String size) {
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        JSONArray array = response.getJSONArray(arrayName);
        int arrayCount = array.length();
        assertEquals(Integer.parseInt(size), arrayCount);
    }

    @Then("The {string} array total matches the api response {string}")
    public void TheArrayTotalMatchesTheApiResponse(String arrayNum, String totalNum) {
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        JSONArray array = response.getJSONArray(arrayNum);
        int arrayCount = array.length();
        int total = response.getInt(totalNum);
        assertEquals(total, arrayCount);
    }

    @Then("The API response object {string} element value should match my remembered {string} element where api response object {string} element matches my remembered {string} element")
    public void theAPIResponseObjectElementValueShouldMatchMyRememberedElementWhereApiResponseObjectElementMatchesMyRememberedElement(String Apivalue, String rememberedvalue, String Apikey, String rememberedkey) {
        String rememberedValue = CurrentObjectBase.retrieve(rememberedvalue);
        int rememberedKey = Integer.parseInt(CurrentObjectBase.retrieve(rememberedkey));
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve("PAYLOAD"));
        JSONArray array = response.getJSONArray("data");
        for (Object object: array) {
            JSONObject json = (JSONObject) object;
            if (json.getInt(Apikey) == rememberedKey) {
                String apiValue = json.getString(Apivalue);
                Assert.assertEquals(rememberedValue, apiValue);
                Logger.log(Apivalue+" = "+apiValue);
                Logger.log(Apikey+" = "+rememberedKey);
            }
        }
    }

    @Then("the {string} array total matches the api response {string}")
    public void theArrayTotalMatchesTheApiResponse(String arrayNm, String totalNm) {
        JSONObject response = new JSONObject(CurrentObjectBase.retrieve(ApiHelper.PAYLOAD));
        JSONArray array = response.getJSONArray(arrayNm);
        int arrayCount = array.length();
        int total = response.getInt(totalNm);
        assertEquals(total, arrayCount);
    }

    @Then("the API response code should be success")
    public void theAPIResponseCodeShouldBeSuccess()
    {
        int responseCode = Integer.parseInt(CurrentObjectBase.retrievePersisted("responsecode"));
        Assertions.assertTrue(responseCode < 300, "Expected a response code less than 300 but it was " + responseCode);
    }

    @Then("I verify my remembered {string} value will match the response {string} value")
    public void iVerifyMyRememberedValueWillMatchTheResponseValue(String rememberedValue, String responseKey) {
        String value = CurrentObjectBase.retrieve(rememberedValue);
        JSONObject response = ApiHelper.getPayloadFromStorage();
        String responseValue = response.get(responseKey).toString();
        Assert.assertEquals(value, responseValue);
    }

    @Then("^Api call should be OK$")
    public void apiCallShouldBeOK()
    {
        Integer[] okStatusCodes = {HttpStatus.SC_OK,HttpStatus.SC_CREATED,HttpStatus.SC_ACCEPTED,
                HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION,HttpStatus.SC_NO_CONTENT,HttpStatus.SC_RESET_CONTENT,
                HttpStatus.SC_PARTIAL_CONTENT,HttpStatus.SC_MULTI_STATUS};
        ArrayList<Integer> okStatusCodesList = new ArrayList<>(Arrays.asList(okStatusCodes));

        int responseCode = Integer.parseInt(CurrentObjectBase.retrievePersisted(ApiHelper.RESPONSE_CODE));
        assertTrue(okStatusCodesList.contains(responseCode));
    }
}
