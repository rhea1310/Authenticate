import java.util.*;
import java.io.*;
import java.security.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
import java.text.*;

/**
 * @author Rhea
 * Generated program
 */
public class Module{

	private String algo="MD5";
	private ArrayList<User> users=new ArrayList<User>();
	
	public static void main(String[] args)throws IOException, ParseException, FileNotFoundException {
		
		Module m=new Module();
		m.start();

	}

	/**
	 * @throws IOException handles Input Output Exceptions
	 * @throws ParseException handles Parse Exceptions
	 * @throws FileNotFoundException Handles File Not Found Exceptions
	 * This function starts the Authentication Module
	 */
	void start() throws IOException, ParseException, FileNotFoundException
	{
		read();
		Scanner keyboard = new Scanner(System.in);
		int inp;
		System.out.println("Welcome to the COMP2396 Authentication system!\n" + 
				"1. Authenticate user\n" + 
				"2. Add user record\n" + 
				"3. Edit user record\n" + 
				"4. Reset user password\n" + 
				"What would you like to perform?");
		do
		{
			System.out.print("Please enter your command (1-4, or 0 to terminate the system): ");
			inp=keyboard.nextInt();
			switch(inp)
			{
			case 1:
				authenticate();
				break;
			case 2:
				add();
				break;
			case 3: 
				edit();
				break;
			case 4: 
				adminreset();
				break;
			}
			
		}while(inp!=0);
		write();
		
	}
	
	/**
	 *  This function adds new users to the system
	 */
	void add()
	{
		User u=new User();
		String password;
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter your username: ");
		u.setUsername(input.next());
		
		password=setpassword(1);
		u.setHashp(u.gethash(password,algo));
		System.out.print("Please enter your full name: ");
		input.nextLine();
		u.setName(input.nextLine());
		System.out.print("Please enter your email address: ");
		u.setEmailid(input.next());
		System.out.print("Please enter your Phone number: ");
		u.setPhoneno(input.next());
		users.add(u);
		System.out.println("Record added successfully!");
}

	/**
	 * @param l Represents which funtion this is being called from- 1: add() 2:edit() 3: adminreset()
	 * @return returns the password
	 * This function gets the acceptable password from the user
	 */
	String setpassword(int l)
	{
		String st;
		if(l==1)
			st="your password";
		else if(l==2)
			st="your new password";
		else
			st="the password";
		String password;
		Scanner input= new Scanner(System.in);
		boolean check=false;
		do {
		boolean f;
		do
		{
			f=true;
			System.out.print("Please enter "+st+": ");
			password=input.next();
			boolean cap=false,small=false,digit=false;
			int i=0;
			while(i<password.length() && (!cap || !small || !digit))
			{
				char ch=password.charAt(i);
				if(Character.isLowerCase(ch))
					small=true;
				else if(Character.isUpperCase(ch))
					cap=true;
				else if(Character.isDigit(ch))
					digit=true;
				i++;
			}
			if(!cap || !small || !digit)
			{
				System.out.println("Your password has to fulfil: at least 1 small letter, 1 capital letter,\n" + 
						"1 digit!");
				f=false;
			}
		}while(!f);

		System.out.print("Please re-enter "+st+": ");
		String p2=input.next();
		if(p2.equals(password))
			check=true;
		else
			System.out.print("Password not match! ");
		
		}while(!check);
		return password;
	}
	
	/**
	 * @return returns user logged in
	 * This function logs in the user by getting correct username and password
	 */
	User authenticate()
	{
		Scanner input = new Scanner(System.in);
		User u=new User(),v=null;
		while(v==null || v.getFaillogc()<3) {
		System.out.print("Please enter your username: ");
		u.setUsername(input.next());
		System.out.print("Please enter your password: ");
		u.setHashp(u.gethash(input.next(),algo));
		for(int i=0; i<users.size(); i++)
		{
			if(users.get(i).getUsername().equals(u.getUsername()))
			{
				v=users.get(i);
				break;
			}			
		}
		
		if(v==null)
		{
			System.out.println("Login failed! ");
			continue;
		}
		if(v.isLocked()==true)
		{
			System.out.println("Your account has been locked! ");
			return null;
		}
		if(v.getHashp().equals(u.getHashp()))
		{
			System.out.println("Login success! Hello "+u.getUsername()+"!");
			String d = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			v.setDate(d);
			v.setFaillogc(0);
			break;
		}
		v.setFaillogc(v.getFaillogc()+1);
		if(v.getFaillogc()<3)
		{
			System.out.println("Login failed! ");
		}
		}
		if(v.getFaillogc()>=3)
		{
			System.out.println("Login failed! Your account has been locked!");
			v.setLocked(true);
			return null;
		}
		
		return v;
	}
	
	/**
	 * This functions edits the specified user record
	 */
	void edit()
	{
		Scanner input=new Scanner(System.in);
		User u=authenticate();
		if(u!=null)
		{
			String p=setpassword(2);
			u.setHashp(u.gethash(p, algo));
			System.out.print("Please enter your new full name: ");
			u.setName(input.nextLine());
			System.out.print("Please enter your new email address: ");
			u.setEmailid(input.next());
			System.out.println("Record update successfully!");
			
		}
	}
	
	/**
	 * This function resets the password of a user by authenticating the admin account
	 */
	void adminreset()
	{
		User x=users.get(0);
		Scanner input= new Scanner(System.in);
		if(x.getHashp().equals("000"))
		{
			System.out.println("Administrator account not exist, please create the administrator account\n" + 
					"by setting up a password for it.");
			
			String p=setpassword(3);
			x.setHashp(x.gethash(p, algo));
			System.out.println("Administrator account created successfully!");
			return;
		}
		else
		{
			boolean checkcor=true;
			do {
			System.out.print("Please enter the password of administrator: ");
			String p=input.next();
			if(x.gethash(p, algo).equals(x.getHashp())!=true)
			{
				System.out.println("Incorrect password!"); //message for wrong password
				checkcor=false;
			}				
		}while(!checkcor);		
	}
		User u=users.get(0);
		String d = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		u.setDate(d);
		String username;
		System.out.print("Please enter the user account need to reset: ");
		username=input.next();
		for(int i=0;i<users.size();i++)
		{
			u=users.get(i);
			if(u.getUsername().equals(username))
				break;
		}
		String p=setpassword(2);
		u.setHashp(u.gethash(p,algo));
		System.out.println("Password update successfully!");
	}
	
	




/**
 * @throws FileNotFoundException Handles File Not Found Exception
 * This function writes the updated records onto the file
 */
@SuppressWarnings("unchecked")
void write() throws FileNotFoundException
{
	File file=new File("./User.txt");
	PrintStream ps=new PrintStream(file);
	JSONObject obj=new JSONObject();
	JSONArray arr=new JSONArray();
	for(int i=0; i<users.size();i++)
	{
		JSONObject tempobj=new JSONObject();
		User u=users.get(i);
		tempobj.put("username",u.getUsername());
		tempobj.put("hash_password",u.getHashp());
		tempobj.put("Full Name",u.getName());
		tempobj.put("Email",u.getEmailid());
		tempobj.put("Phone number",u.getPhoneno());
		tempobj.put("Fail count",u.getFaillogc());
		tempobj.put("Last Login Date",u.getDate());
		tempobj.put("Account locked",u.isLocked());
		arr.add(tempobj);
	}
	obj.put("user_array",arr);
	ps.println(obj.toJSONString());
	ps.close();
	
}

/**
 * @throws IOException Handles Input Output Exceptions
 * @throws ParseException Handles Parse Exceptions
 * This functions reads the user data from the file
 */
void read() throws IOException, ParseException
{
	File file =new File("./User.txt");
	try {
		JSONParser p= new JSONParser();
		JSONObject obj= (JSONObject)p.parse(new FileReader(file));
		JSONArray arr=(JSONArray) obj.get("user_array");
		Iterator <Object> itr=arr.iterator();
		while(itr.hasNext()==true)
		{
			JSONObject temp=(JSONObject)itr.next();
			User u=new User();
			u.setUsername(temp.get("username").toString());
			
			u.setHashp(temp.get("hash_password").toString());
			u.setName(temp.get("Full Name").toString());
			u.setEmailid(temp.get("Email").toString());
			u.setPhoneno(temp.get("Phone number").toString());
			u.setFaillogc(Integer.parseInt((temp.get("Fail count").toString())));
			u.setDate(temp.get("Last Login Date").toString());
			u.setLocked(Boolean.parseBoolean(temp.get("Account locked").toString()));
			users.add(u);
		}
	}
	catch(FileNotFoundException e)
	{
		file.createNewFile();
		User u=new User();
		u.setUsername("administrator");
		u.setName("Administrator");
		u.setEmailid("admin@cs.hku.hk");
		u.setPhoneno("12345678");
		u.setHashp("000");
		users.add(u);
	}
}

}

