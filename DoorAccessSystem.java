import java.util.*;
import java.io.File;
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DoorAccessSystem
{
    public static void main(String[] args) {
        boolean vaildId=false;  //whether the id is vaild
        boolean vaildDoor=false; //whether the Door id is vaild
        boolean enter=false; //whether user could enter the door
        boolean admin=false; //whether is the admin log in the system
        String id;
        String doorId;

        String dept="";
        String name="";
        String roll="";

        int option=0;
        int userIndex=-1;
        int doorIndex=0;
        String Stime="";
        String Etime="";

        //read the doorList into the system
        ArrayList<door> doorList = new ArrayList<door>();
        File file = new File("door.dat");
        Scanner inputFile = null;
        try{
            inputFile = new Scanner(file);
            while(inputFile.hasNext())
            {
                String line = inputFile.nextLine();
                String [] strarray = line.split("\t");
                door a = new door(strarray[0],strarray[1],strarray[2],strarray[3],strarray[4],strarray[5]); 
                doorList.add(a);

            }

        }
        catch(Exception e)
        {
            System.err.println("Error: "+ e.getMessage());
        }
        finally{
            if(inputFile != null)
            {
                inputFile.close();
            }
        }

        //read the userList into the system
        ArrayList<user> userList = new ArrayList<user>();
        File file1 = new File("user.dat");
        Scanner inputFile1 = null;
        try{
            inputFile1 = new Scanner(file1);
            while(inputFile1.hasNext())
            {
                String line = inputFile1.nextLine();
                String [] strarray = line.split("\t");
                if(strarray[0].equals("Administrator"))
                {
                    user a = new Administrator(strarray[1],strarray[2],"");
                    userList.add(a);
                }else if(strarray[0].equals("Student"))
                {
                    user a = new Student(strarray[1],strarray[2],strarray[3]);
                    userList.add(a);
                }else if(strarray[0].equals("Staff"))
                {
                    user a = new Staff(strarray[1],strarray[2],strarray[3]);
                    userList.add(a);
                }else if(strarray[0].equals("Others"))
                {
                    user a = new Others(strarray[1],strarray[2],strarray[3]);
                    userList.add(a);
                }
            }
        }
        catch(Exception e)
        {
            System.err.println("Error: "+ e.getMessage());
        }
        finally{
            if(inputFile1 != null)
            {
                inputFile1.close();
            }
        }

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Welcome to door access system.");

        do{
            System.out.print("Please enter a user id...or type -1 to exit: "); 
            id = keyboard.nextLine();
            
            //define which kind of user has been entered
            for(int i=0;i<userList.size();i++)
            {
                //define if it is not a admin, and get the department and name of the user at the same time
                if(userList.get(i).getId().equals(id) && id.equals("admin")==false ) 
                {
                    vaildId=true;
                    dept = userList.get(i).getDept();
                    name = userList.get(i).getName();
                    if(userList.get(i) instanceof Student)
                    {
                        roll = "Student";
                    }else if(userList.get(i) instanceof Staff)
                    {
                        roll = "Staff";
                    }else if(userList.get(i) instanceof Others)
                    {
                        roll = "Others";
                    }
                    userIndex = i;
                    break;
                }else if(userList.get(i).getId().equals(id)==false && id.equals("admin")==false)
                {
                    vaildId=false;
                }
                
                //if it is a admin, only get the name
                if(id.equals("admin")==true)
                {
                    admin=true;
                    name = userList.get(i).getName();
                    break;
                }else
                {
                    admin=false;
                }
            }
            
            //make the user type another id if the id is invaild
            if(vaildId==false && admin==false)
            {
                System.out.println("Invaild ID.");
            }

        } while(vaildId==false && admin==false);

        //if the id is correct go into the system
        if(vaildId==true && admin==false)
        {
            System.out.println("Hello, "+dept+" "+roll+": "+name);
            do{
                System.out.println("Menu: ");
                System.out.println("\t1\tAttempt to open a door");
                System.out.println("\t2\tQuery door opening time");
                System.out.println("\t3\tSwitch user");
                System.out.println("\t-1\tQuit");
                System.out.print("Please enter a choice or type -1 to exit: "); 
                option = keyboard.nextInt();
                keyboard.nextLine();
                if(option==1)
                {
                    //check if it is a vaild door id
                    do{
                        System.out.print("Please enter a door id: "); 
                        doorId = keyboard.nextLine();
                        
                        for(int i=0;i<doorList.size();i++)
                        {
                            if(doorList.get(i).getId().equals(doorId))
                            {
                                vaildDoor=true;
                                //check if the current user could enter the door
                                enter = userList.get(userIndex).key(doorList.get(i));
                                break;
                            }else
                            {
                                vaildDoor=false;
                            }
                        }

                        if(vaildDoor==false)
                        {
                            System.out.println("Invaild Door ID.");
                        }

                    }while(vaildDoor==false);

                    if(vaildDoor==true)
                    {
                        if(enter==true)
                        {
                            //record who accessed the door and when
                            System.out.println("The Door has been open.");
                            PrintWriter outputFile = null;
                            try{
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                outputFile = new PrintWriter("DoorAccessRecord.txt");       
                                outputFile.println("Door:\t"+doorList.get(doorIndex).getId()+"\tUser:\t"+userList.get(userIndex).getName()+"\tTime:\t"+sdf.format(new Date()));
                            }
                            catch (Exception e)
                            {
                                System.err.println("Error: " +  e.getMessage());
                            }
                            finally{
                                if(outputFile != null)
                                {
                                    outputFile.close();
                                }
                            }
                        }else if(enter==false)
                        {
                            System.out.println("You can not open this door!");
                        }
                    }

                }else if(option==2)
                {
                    do{
                        System.out.print("Please enter a door id: "); 
                        doorId = keyboard.nextLine();

                        for(int i=0;i<doorList.size();i++)
                        {
                            if(doorList.get(i).getId().equals(doorId))
                            {
                                vaildDoor=true;
                                Stime = doorList.get(i).getStime();
                                Etime = doorList.get(i).getEtime();
                                enter = userList.get(userIndex).key(doorList.get(i));
                                doorIndex = i;
                                break;
                            }else
                            {
                                vaildDoor=false;
                            }
                        }

                        if(vaildDoor==true)
                        {
                            System.out.println("The opening time is from "+Stime+" to "+Etime);
                            System.out.print("If you want to open it, enter 1, if not enter 0:");
                            int open = keyboard.nextInt();
                            keyboard.nextLine();
                            if(open==1)
                            {
                                //check if the user could open it
                                if(enter==true)
                                {
                                    System.out.println("The Door has been open.");
                                    //record who accessed the door and when
                                    PrintWriter outputFile = null;
                                    try{
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                        outputFile = new PrintWriter("DoorAccessRecord.txt");       
                                        outputFile.println("Door:\t"+doorList.get(doorIndex).getId()+"\tUser:\t"+userList.get(userIndex).getName()+"\tTime:\t"+sdf.format(new Date()));
                                    }
                                    catch (Exception e)
                                    {
                                        System.err.println("Error: " +  e.getMessage());
                                    }
                                    finally{
                                        if(outputFile != null)
                                        {
                                            outputFile.close();
                                        }
                                    }

                                }else if(enter==false)
                                {
                                    System.out.println("You can not open this door!");
                                }

                            }

                        }else if(vaildDoor==false)
                        {
                            System.out.println("Invaild Door ID.");
                        }
                    }while(vaildDoor==false);
                }else if(option==3)
                {
                    //reset the boolean of user id
                    vaildId=false;
                    do{
                        System.out.print("Please enter a user id...or type -1 to exit: "); 
                        id = keyboard.nextLine();
                        for(int i=0;i<userList.size();i++)
                        {
                            if(userList.get(i).getId().equals(id) && id.equals("admin")==false ) 
                            {
                                vaildId=true;
                                dept = userList.get(i).getDept();
                                name = userList.get(i).getName();
                                roll = userList.get(i).getRoll();
                                userIndex = i;
                                break;
                            }else if(userList.get(i).getId().equals(id)==false && id.equals("admin")==false)
                            {
                                vaildId=false;
                            }

                            if(id.equals("admin")==true)
                            {
                                admin=true;
                                name = userList.get(i).getName();
                                break;
                            }else
                            {
                                admin=false;
                            }
                        }

                        if(vaildId==false && admin==false)
                        {
                            System.out.println("Invaild ID.");
                        }

                    } while(vaildId==false && admin==false);

                    if(vaildId==true){
                        System.out.println("Hello, "+dept+" "+roll+":"+name);
                    }

                    if(admin==true)
                    {
                        break;
                    }
                }
                
                 if(option!=-1 && option!=1 && option!=2 && option!=3)
                {
                    System.out.println("Invaild option");
                }
                
            }while(option!=-1);
        }

        //the admin system
        if(admin==true && vaildId==false)
        {
            System.out.println("Hello, Adminstrator : "+name);
            do{
                System.out.println("Menu: ");
                System.out.println("\t1\tView a door");
                System.out.println("\t2\tView a user");
                System.out.println("\t-1\tQuit");
                System.out.print("Please enter a choice or type -1 to exit: "); 
                option = keyboard.nextInt();
                keyboard.nextLine();
                if(option==1)
                {
                    // check if the door id is vaild
                    do{
                        System.out.print("Please enter a door id: "); 
                        doorId = keyboard.nextLine();

                        for(int i=0;i<doorList.size();i++)
                        {
                            if(doorList.get(i).getId().equals(doorId))
                            {
                                vaildDoor=true;
                                break;
                            }else
                            {
                                vaildDoor=false;
                            }
                        }

                        if(vaildDoor==false && doorId.equals("-1")==false)
                        {
                            System.out.println("Invaild Door ID.");
                        }

                    }while(vaildDoor==false);

                    if(vaildDoor==true)
                    {
                        System.out.println("Here is the Access Record of Doors:");

                        //read the door access record
                        File file2 = new File("DoorAccessRecord.txt");
                        Scanner inputFile2 = null; 
                        try{
                            inputFile2= new Scanner(file2);
                            while(inputFile2.hasNext())
                            {       
                                System.out.println(inputFile2.nextLine());   
                            }
                        }
                        catch (Exception e)
                        {
                            System.err.println("Error: " +  e.getMessage());
                        }
                        finally{

                            if(inputFile2 != null)
                            {
                                inputFile2.close();
                            }
                        }

                    }    

                }else if(option==2)
                {
                    //check if the user id is vaild
                    do{
                        System.out.print("Please enter a user id: "); 
                        id = keyboard.nextLine();
                        for(int i=0;i<userList.size();i++)
                        {
                            if(userList.get(i).getId().equals(id)) 
                            {
                                vaildId=true;
                                userIndex = i;
                                break;
                            }else
                            {
                                vaildId=false;
                            }
                        }

                        if(vaildId==false && id.equals("-1")==false)
                        {
                            System.out.println("Invaild input");
                        } 

                    } while(vaildId==false);

                    
                    if(vaildId==true)
                    {
                        for(int i = 0; i < doorList.size();i++){
                            enter = userList.get(userIndex).key(doorList.get(i));
                            
                            //print out all the doors that could be open by the user
                            if(enter==true){
                                System.out.println(doorList.get(i).getId());
                            }
                        }
                    }

                }else if(option==-1)
                {
                    break;
                }

                if(option!=-1 && option!=1 && option!=2)
                {
                    System.out.println("Invaild option");
                }

            }while(option!=-1);

        }

    }
}
