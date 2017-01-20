
/**
 * Write a description of class user here.
 * user is a class of system users
 * @author (your name) 
 * 
 */
public abstract class user
{

    private String roll;
    private String name;
    private String id;
    private String dept;

    //constrcutor
    public user( String name, String id, String dept)
    {
        this.roll = roll;
        this.name = name;
        this.id = id;
        this.dept = dept;
    }

    //methods
    public String getRoll()
    {
        return roll;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public String getDept()
    {
        return dept;
    }

    //abstarct method for other kinds of users to define by themselves
    public abstract boolean key(door a);

}
