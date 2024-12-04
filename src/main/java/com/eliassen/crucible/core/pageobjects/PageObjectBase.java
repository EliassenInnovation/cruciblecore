package com.eliassen.crucible.core.pageobjects;

import com.eliassen.crucible.core.sharedobjects.ProgressHandler;
import com.eliassen.crucible.common.helpers.SystemHelper;
import org.json.JSONObject;

public abstract class PageObjectBase implements iPageObject
{
	private ProgressHandler progressHandler;
	protected PageObjectResolver resolver;

	public static final String URL_STRING = "url";
	public String pageUrlPart = null;
	
	private PageObjectTable pageTable;
	
	protected PageObjectTable getPageTable()
	{
		if(pageTable == null)
		{
			pageTable = new PageObjectTable();
			fillPageTable();
		}
		
		return pageTable;
	}

	public void setProgressHandler(ProgressHandler _progressHandler)
	{
		progressHandler = _progressHandler;
	}

	public ProgressHandler getProgressHandler()
	{
		return progressHandler;
	}

	public void store(String key, String value)
	{
		if(getPageTable().containsKey(key))
		{
			getPageTable().replace(key, value);
		}
		else
		{
			getPageTable().put(key, value);
		}
	}

	public String retrieve(String key)
	{
		return getPageTable().get(key);
	}
	
	public abstract void fillPageTable();
	
	protected String pageName;
	public String getPageName()
	{
		return pageName;
	}
	protected void setPageName(String value)
	{
		pageName = value;
	}

	public String getURL(String environmentName)
	{
		String environmentURLName = URL_STRING + "_" + environmentName;
		String url =  getPageTable().get(environmentURLName);
		if (pageUrlPart != null)
		{
			url += pageUrlPart;
		}

		return url;
	}

	//TODO need a fix
//	public String getBaseUrl() throws URISyntaxException
//	{
//		String currentUrl = CurrentObjectBase.actualURL();
//		URI uri = new URI(currentUrl);
//		String baseUrl = uri.getAuthority();
//
//		return baseUrl;
//	}
	
	protected void addCommonObjects(PageObjects_Common common)
	{
		PageObjects_Common.CommonObject[] commonObjects = common.getCommonObjects();
		
		for(PageObjects_Common.CommonObject commonObject : commonObjects)
		{
			store(commonObject.key, commonObject.value);
		}
	}

	protected void addSubViews(String[] pages)
	{
		for(String page : pages)
		{
			PageObjectBase base = (PageObjectBase)resolver.getPageObjectByName(page);
			getPageTable().putAll(base.getPageTable());
		}
	}

	protected void addUrls()
	{
		JSONObject urls = new JSONObject(SystemHelper.getConfigSetting("urls"));
		if(urls != null) {
			for (String urlName : urls.keySet()) {
				store(urlName, urls.getString(urlName));
			}
		}
	}
}
