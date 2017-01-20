import java.util.Date;
import java.text.SimpleDateFormat;

public class Administrator extends user
{

    public Administrator(String name, String id, String dept)
    {
        super(name,id,dept);
    }
    
    //admin can enter any door 
    public boolean key(door a)
    {
        return true;
    }

}
