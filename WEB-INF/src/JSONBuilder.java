import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSONBuilder extends HttpServlet{
	/*
	 * This servlet is for creating different output files from Session
	 * */
	public void service(HttpServletRequest req,  HttpServletResponse res)  throws ServletException, IOException {
	  	/*
		 * This is the try to make a special json-tree based on the TaskBean array
		 * So, here is a function that builds a tree according to the TaskBean's folder property 
		 * */
		try{
		PrintWriter out = res.getWriter();
	  	TaskBean[] tsk_list = SessionIO.sesGetTaskList(req);
	    String path = req.getServletPath();
	    String actionName = path.substring(1, path.lastIndexOf("."));
		if (actionName.equals("tree")){
		    int i=0;
		    String[] arr_str = new String[3];
		    arr_str[0]="";
		    arr_str[1]="";
		    arr_str[2]="";
		    String output="";
			while (tsk_list.length>i){
				output = "{";
				output += " id: '" +tsk_list[i].getId()+ "',";
				output += " header: '" +tsk_list[i].getHeader()+ "',";
				output += " description: '" +tsk_list[i].getDescription()+ "',";
				output += " created: " +tsk_list[i].getCreated()+ ",";
				output += " dateExpire: " +tsk_list[i].getDateExpire()+ ",";
				output += " folder: " +tsk_list[i].getFolder();
				output += "}";
				if (arr_str[tsk_list[i].getFolder()].equals("")){
					arr_str[tsk_list[i].getFolder()]+=output;
				}
				else{
					arr_str[tsk_list[i].getFolder()]+=","+output;
				}
				i++;
				}
			
			output = "{ identifier: 'id', label: 'header', items: [{ id:'f0', header:'Main Tasks', type:'folder' ,'children': [" + arr_str[0] + "]},{ id:'f1', header:'Home Tasks', type:'folder' ,'children': [" + arr_str[1] + "]},{ id:'f2', header:'Sport Tasks', type:'folder' ,'children': [" + arr_str[2] + "]}]}";
			out.println(output);
		}
		else if (actionName.equals("exp")){
			String output="";
			int i=0;
			System.out.println(SessionIO.getCurrentDate());
			while (tsk_list.length>i){
				if (tsk_list[i].getDateExpire()<SessionIO.getCurrentDate()){
					if (!output.equals(""))  output+=","; 
					output += "{";
					output += " id: '" +tsk_list[i].getId()+ "',";
					output += " header: '" +tsk_list[i].getHeader()+ "',";
					output += " description: '" +tsk_list[i].getDescription()+ "',";
					output += " created: '" +SessionIO.convertMillisToDate(tsk_list[i].getCreated())+ "',";
					output += " dateExpire: '" +SessionIO.convertMillisToDate(tsk_list[i].getDateExpire())+ "',";
					output += " folder: '" +getFolderName(tsk_list[i].getFolder());
					output += "'}";
					}
				i++;
				}
			
			output = "{ identifier: 'id', label: 'header', items: ["+ output +"]}";
			out.println(output);
		}
		else if (actionName.equals("nonexp")){
			String output="";
			int i=0;
			while (tsk_list.length>i){
				if (tsk_list[i].getDateExpire()>=SessionIO.getCurrentDate()){
					if (!output.equals(""))  output+=","; 
					output += "{";
					output += " id: '" +tsk_list[i].getId()+ "',";
					output += " header: '" +tsk_list[i].getHeader()+ "',";
					output += " description: '" +tsk_list[i].getDescription()+ "',";
					output += " created: '" +SessionIO.convertMillisToDate(tsk_list[i].getCreated())+ "',";
					output += " dateExpire: '" +SessionIO.convertMillisToDate(tsk_list[i].getDateExpire())+ "',";
					output += " folder: '" +getFolderName(tsk_list[i].getFolder());
					output += "'}";
					}
				i++;
				}
			
			output = "{ identifier: 'id', label: 'header', items: ["+ output +"]}";
			out.println(output);
		}
		}
		catch(ServletException e){
	  		System.out.println("Servlet Exception occured: " + e.getMessage());
	  	}
	  	catch(IOException ex2){
	  		System.out.println("IOException: " + ex2.getMessage());
	  	}
	  	catch(Exception ex){
	  		System.out.println("Exception raised: " + ex.getMessage());
	  	}
		
	}
	private static String getFolderName(byte folder){
		String out="";
		switch(folder){
		case 0:
			out = "Main Tasks";
			break;
		case 1:
			out = "Home Tasks";
			break;
		case 2:
			out = "Sport Tasks";
			break;
		default:
			out = "No such Folder";
			break;
		}
		return out;
	}
	
	public static String printHTMLTable(HttpServletRequest req,  HttpServletResponse res, String mode) throws ServletException, IOException {
		/*
		 * One of the first tries to return data from the session object
		 * This one prints out the pure HTML code
		 * */
		try{
		TaskBean[] tsk_list = SessionIO.sesGetTaskList(req);
		String out_list="<table style='width:100%'>";
		int i=0;
		if (SessionIO.sesGetTaskList(req)!=null){
		if (mode.equals("exp")){
			while(tsk_list.length>i){
				if(tsk_list[i].getDateExpire()<SessionIO.getCurrentDate()){
					out_list += "<tr><td>"+tsk_list[i].getId() + "</td><td>" + tsk_list[i].getHeader()+ "</td><td>" + tsk_list[i].getDescription() + "</td><td>" + SessionIO.convertMillisToDate(tsk_list[i].getCreated())+ "</td><td>" + SessionIO.convertMillisToDate(tsk_list[i].getDateExpire()) +"</td><td>"+tsk_list[i].getFolder()+"</td></tr>";
					}
				i++;
				}
		}
		else if (mode.equals("nonexp")){
			while(tsk_list.length>i){
				if(tsk_list[i].getDateExpire()>SessionIO.getCurrentDate()){
					out_list += "<tr><td>"+tsk_list[i].getId() + "</td><td>" + tsk_list[i].getHeader()+ "</td><td>" + tsk_list[i].getDescription() + "</td><td>" + SessionIO.convertMillisToDate(tsk_list[i].getCreated())+ "</td><td>" + SessionIO.convertMillisToDate(tsk_list[i].getDateExpire()) +"</td><td>"+tsk_list[i].getFolder()+"</td></tr>";
				}i++;
				}
			}
		}
	out_list += "</table>";
	return out_list;}
		catch(ServletException e){
	  		System.out.println("Servlet Exception occured: " + e.getMessage());
	  		return null;
	  	}
	  	catch(IOException ex2){
	  		System.out.println("IOException: " + ex2.getMessage());
	  		return null;
	  	}
	}


 public static void getJSONAuto(HttpServletRequest req,  HttpServletResponse res){
	 try{
	  	PrintWriter out = res.getWriter();
	  	TaskBean[] tsk_list = SessionIO.sesGetTaskList(req);
	    String path = req.getServletPath();
	    String actionName = path.substring(1, path.lastIndexOf("."));
		JSONObject json = new JSONObject();
		if (tsk_list!=null){
			int i=0;
		List tsk = new ArrayList();
		while (tsk_list.length>i){
			tsk.add(tsk_list[i]);
			i++;
			}
		JSONArray jsonArray = JSONArray.fromObject(tsk);
		out.println("{ identifier: 'id', label: 'header', items: " + jsonArray+ "}"); }
		}
	  	catch(ServletException e){
	  		System.out.println("Servlet Exception occured: " + e.getMessage());
	  	}
	  	catch(IOException ex2){
	  		System.out.println("IOException: " + ex2.getMessage());
	  	}
 }
 public static String getFolderList(){
	 /*
	  * This is not for demo.
	  * It should be a function that retrieves folder list from the TaskBean array - it's simple.
	  * */
	 return "";
 }
}
