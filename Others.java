import java.util.Date;
import java.text.SimpleDateFormat;

public class Others extends user
{
    private boolean key;
    private boolean keyRoll;
    private boolean keyDept;
    private boolean keyTime;
    
    public Others(String name, String id, String dept)
    {
        super(name,id,dept);
    }

    //decide if the user could open the door
    public boolean key(door a)
    {

        if(a.getRoll().equals("ALL") )
        {
            keyRoll=true;
        }else
        {
            keyRoll=false;
        }

        if(a.getDept().contains(";"))
        {
            String [] deptarray = a.getDept().split(";");
            for(int i = 0; i < deptarray.length;i++)
            {
                if(super.getDept().contains(deptarray[i]))
                {
                    keyDept = true;
                    break;
                }else
                {
                    keyDept=false;
                }
                System.out.println(deptarray[i]+"\t"+super.getDept());
            }

        }else
        {
            if(a.getDept().contains(super.getDept()) || a.getDept().equals("ALL") )
            {
                keyDept = true;
            }else
            {
                keyDept=false;
            }
        }

        String [] timearray = a.getStime().split(":");
        long Stime = (Integer.parseInt(timearray[0]))*10000 + (Integer.parseInt(timearray[1]))*100 + (Integer.parseInt(timearray[2]));

        String [] timearray2 = a.getEtime().split(":");
        long Etime = (Integer.parseInt(timearray2[0]))*10000 + (Integer.parseInt(timearray2[1]))*100 + (Integer.parseInt(timearray2[2]));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String [] timearray3 = sdf.format(new Date()).split(":");
        long now = (Integer.parseInt(timearray3[0]))*10000 + (Integer.parseInt(timearray3[1]))*100 + (Integer.parseInt(timearray3[2]));

        if(now>=Stime && now<=Etime)
        {
            keyTime=true;
        }else
        {
            keyTime=false;
        }

        if((a.getException().contains(super.getId())  && (keyTime==true))|| ( keyDept==true && keyTime==true))
        {
            key = true;
        }else
        {
            key = false;
        }

        return key;
    }
}
