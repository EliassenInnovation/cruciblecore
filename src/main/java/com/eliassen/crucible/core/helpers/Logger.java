package com.eliassen.crucible.core.helpers;

import com.eliassen.crucible.core.sharedobjects.CurrentObjectBase;

public class Logger extends com.eliassen.crucible.common.helpers.Logger
{
    public Logger()
    {

    }

    public static void log(String message)
    {
        if(CurrentObjectBase.getScenario() != null)
        {
            CurrentObjectBase.getScenario().log(message);
        }
        else
        {
            System.out.println(message);
        }
    }

    public static void log(String message, boolean logToScenario)
    {
        if(CurrentObjectBase.getScenario() != null && logToScenario)
        {
            CurrentObjectBase.getScenario().log(message);
        }
        else
        {
            System.out.println(message);
        }
    }
}
