package com.eliassen.crucible.core.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.eliassen.crucible.core.sharedobjects.CurrentObjectBase;
import com.eliassen.crucible.common.helpers.FileHelper;
import com.eliassen.crucible.common.helpers.Functions;
import com.eliassen.crucible.common.helpers.JsonHelper;
import com.eliassen.crucible.common.helpers.UserHelper;
import io.cucumber.datatable.DataTable;
import org.json.JSONArray;
import org.json.JSONObject;

public class TestHelperBase extends UserHelper
{
	public static String getPrettyJson(JSONObject object)
	{
		return JsonHelper.getPrettyJson(object);
	}

	public static String getPrettyJson(JSONArray array)
	{
		return JsonHelper.getPrettyJson(array);
	}

	public JSONObject getJSONFileContent(String jsonPath)
    {
		return JsonHelper.getJSONFileContent(jsonPath);
    }

    public String getTextFileContent(String filePath)
	{
		return new FileHelper().getTextFileContent(filePath);
	}

    public static  void wait(int seconds)
	{
		boolean notify = true;
		wait(seconds,notify);
	}

	public static void waitSilently(int seconds)
	{
		boolean notify = false;
		wait(seconds, notify);
	}

    public static void wait(int seconds, boolean notify)
	{
		int milliseconds = seconds * 1000;
		try
		{
			if(notify)
			{
				System.out.println("Waiting " + seconds + " seconds");
			}
			Thread.sleep((long)milliseconds);

		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static Hashtable<String,String> convertDataTableToHashtable(DataTable table)
	{
		return Functions.convertDataTableToHashtable(table);
	}

	public static String convertHashtableToJSONString(Hashtable<String,String> table)
	{
		String jsonString = "{";

		for(String key : table.keySet())
		{
			if(jsonString.length() > 1)
			{
				jsonString += ",";
			}

			String value = table.get(key);
			if(value.equals("array"))
			{
				jsonString += "\"" + key + "\":[]";
			}
			else if (value.equals(NULL))
			{
				jsonString += "\"" + key + "\": null";
			}
			else
			{
				jsonString += "\"" + key + "\":\"" + value +"\"";
			}
		}

		jsonString += "}";

		return jsonString;
	}

	public static String getNewGUID()
	{
		String guid = UUID.randomUUID().toString();

		return guid;
	}

	public static int getMonthNumberFromMonthString(String monthString) throws ParseException
	{
		return getMonthNumberFromMonthString(monthString, Locale.ENGLISH);
	}

	public static int getMonthNumberFromMonthString(String monthString, Locale locale) throws ParseException
	{
		LocalDate date = convertDateToLocalDate(new SimpleDateFormat("MMM", locale).parse(monthString));

		int num = date.getMonthValue();

		return num;
	}

	public static LocalDate convertDateToLocalDate(Date date)
	{
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	public static JSONObject getSampleObject(String sampleObjectName)
	{
		String sampleObjectsFileName = "sampleObjects.json";
		JSONObject sampleObjects = new TestHelperBase().getJSONFileContent(sampleObjectsFileName);
		JSONObject requestedObject = sampleObjects.getJSONObject(sampleObjectName);

		return requestedObject;
	}

	public static void addToCSVListInStorage(String key, String value)
	{
		String list = CurrentObjectBase.retrieve(key);
		if(list == null)
		{
			list = value;
		}
		else
		{
			list += "," + value;
		}

		CurrentObjectBase.store(key, list);
	}

	public static void addToCSVListInPersistedStorage(String key, String value)
	{
		String list = CurrentObjectBase.retrievePersisted(key);
		if(list == null)
		{
			list = value;
		}
		else
		{
			list += "," + value;
		}

		CurrentObjectBase.storePersisted(key, list);
	}

	public static boolean isNumeric(String text)
	{
		if (text == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(text);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
