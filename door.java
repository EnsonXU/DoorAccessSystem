import java.util.*;
import java.sql.Time;

public class door
{

    private String id;
    private String sTime;
    private String eTime;
    private String dept;
    private String roll;
    private String exception;

    public door(String id,String sTime, String eTime, String dept,String roll,String exception)
    {  
        this.id = id;
        this.sTime = sTime;
        this.eTime = eTime;
        this.dept = dept;
        this.roll = roll;
        this.exception = exception;
    }

    public String getId()
    {
        return id;
    }

    public String getStime()
    {
        return sTime;
    }

    public String getEtime()
    {
        return eTime;
    }

    public String getDept()
    {
        return dept;
    }

    public String getRoll()
    {
        return roll;
    }

    public String getException()
    {
        return exception;
    }

}

