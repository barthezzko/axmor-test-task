import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 

public class SessionIO extends HttpServlet {
  public void service(HttpServletRequest req,  HttpServletResponse res) throws ServletException, IOException {
    HttpSession session = req.getSession();
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String path = req.getServletPath();
    String actionName = path.substring(1, path.lastIndexOf("."));
    if (actionName.equals("insert")){
    	TaskBean tsk = new TaskBean();
    	tsk.setId(getTaskLength(req));
    	tsk.setHeader(req.getParameter("header_"));
    	tsk.setDescription(req.getParameter("descr"));
    	tsk.setCreated(getCurrentDate());
    	tsk.setDateExpire(Long.parseLong(req.getParameter("expire")));
    	tsk.setFolder(Byte.parseByte(req.getParameter("folder")));
    	sesInsertTask(req, tsk);
		out.println("Record has been added");
    }
    else if (actionName.equals("delete")){
    	deleteTask(req, Integer.parseInt(req.getParameter("id")));
    	out.println("Record has been deleted");
    }
    else if (actionName.equals("modify")){
    	updateTask(req, Integer.parseInt(req.getParameter("id")), req.getParameter("header_"), req.getParameter("descr"), Long.parseLong(req.getParameter("expire")), Byte.parseByte(req.getParameter("folder")));
    	out.println("Record has been modified");
    }
    else if (actionName.equals("treeview")){
    	out.println(JSONBuilder.printHTMLTable(req, res, "tree"));
    }
    else if (actionName.equals("nonexp")){
    	out.println(JSONBuilder.printHTMLTable(req, res, "nonexp"));
    	}	
    else if (actionName.equals("exp")){
    	out.println(JSONBuilder.printHTMLTable(req, res, "exp"));
    }
    else if (actionName.equals("info")){
        out.println("Session ID: " + session.getId()+ "<br />");
        out.println("Creation Time: " + session.getCreationTime());
        out.println("(" + convertMillisToDate(session.getCreationTime()) + ")"+ "<br />");
        out.println("Last Accessed Time: " + session.getLastAccessedTime());
        out.println("(" + convertMillisToDate(session.getLastAccessedTime()) + ")"+ "<br />");
        out.println("Session Inactive Interval: " + session.getMaxInactiveInterval()+ " seconds");
        out.println("<br /> Today is "+convertMillisToDate(getCurrentDate()));
    }
    else if(actionName.equals("close")){
    	session.invalidate();
    	out.println("Session closed");
    }
    out.close();
   }
  
  public static TaskBean[] sesGetTaskList(HttpServletRequest req) throws ServletException, IOException {
	  /*
	   * This returns the array of tasks from the Session
	   * */
	    try{
		    HttpSession session = req.getSession();
		    if (session.getAttribute("TaskList")!= null){
			    TaskBean[] arr = (TaskBean[]) (session.getAttribute("TaskList"));
			    return arr;
		    }
		    else
		    return null;
	    }
	    catch(Exception ex){
	    	System.out.println("sesGetTaskList\n" + ex.getMessage());
	    	return null;
	    }
		
	  }
  public void sesInsertTask(HttpServletRequest req, TaskBean insTask) throws ServletException, IOException {
	    /*
	     * This gets the Task item and adds it to the Task array into the Session scope
	     * */
	  	try{
	  	TaskBean[] arr = sesGetTaskList(req);
	    if (sesGetTaskList(req)!=null){  	
	    	int cntr=0;
		  	TaskBean new_arr[] = new TaskBean[arr.length+1];
	  	  	while (arr.length>cntr){
		    	new_arr[cntr]=arr[cntr];
		    	cntr++;
		    	}
	    new_arr[cntr]=insTask;
	    storeAttInSession(req, "TaskList", new_arr);
	    }
	    else{
	    	TaskBean[] new_arr = {insTask};
	    	storeAttInSession(req, "TaskList", new_arr);
		    }
	  	}catch(Exception ex){
	  		System.out.println("sesInsertTask\n" + ex.getMessage());
	  	}
	    
  }
  public void storeAttInSession(HttpServletRequest req, String att_name, Object val) throws ServletException, IOException {
	  	HttpSession session = req.getSession();
	    session.setAttribute(att_name, val);
	    /*
	     * This gets attribute with its value and stores it into Session scope
	     * */
	  }
  public int getTaskLength(HttpServletRequest req) throws ServletException, IOException{
	 return (sesGetTaskList(req)==null) ?  0: sesGetTaskList(req).length;
	 /*
	  * This finds out the tasks number quantity
	  * The reduced form of if-else statement
	  * */
	  
  }
  public void deleteTask(HttpServletRequest req, int id) throws ServletException, IOException{
	  try {
		  /*
		   * Task deleting with full Task array re-index
		   * */
	  TaskBean[] old_arr = sesGetTaskList(req);
	  switch (old_arr.length){
	  case 0:
		  break;
	  case 1:
		  //req.removeAttribute("TaskList");
		  storeAttInSession(req, "TaskList", null);
	  	  break;
	  default:
		  TaskBean new_arr[] = new TaskBean[old_arr.length-1];
		  int counter=0;
		  for (int i=0; i<old_arr.length;i++){
			  if (i != id){
				  TaskBean bean = new TaskBean();
				  bean.setId(counter);
				  bean.setHeader(old_arr[i].getHeader());
				  bean.setDescription(old_arr[i].getDescription());
				  bean.setDateExpire(old_arr[i].getDateExpire());
				  bean.setCreated(old_arr[i].getCreated());
				  bean.setFolder(old_arr[i].getFolder());
				  new_arr[counter] = bean;
				  bean = null;
				  counter++;
				  
			  }
		  }
		  System.out.println ("ok");
		  storeAttInSession(req, "TaskList", new_arr);
		  break;
	  }
	 }catch(Exception ex){
		  System.out.println("deleteTask\n" + ex.toString());
	  }
	  
  }
  public void updateTask(HttpServletRequest req, int id, String header, String descr, long exp, byte fold) throws ServletException, IOException{
	  
	  deleteTask(req, id);
	  TaskBean insTask = new TaskBean();
	  insTask.setId(getTaskLength(req));
	  insTask.setHeader(header);
	  insTask.setDescription(descr);
	  insTask.setDateExpire(exp);
	  insTask.setCreated(getCurrentDate());
	  insTask.setFolder(fold);
	  sesInsertTask(req, insTask);
	  /*
	   * I don't think this way is the most optimal 
	   * Instead of editing the Task object in Session we delete non-satisfying task and add a proper one
	   * But for the demo purposes it's enough
	   * */
	  
  }
  public static long getCurrentDate(){
		 return  new java.util.Date().getTime(); 
	 }
	 
  public static String convertMillisToDate(long millis_date){
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 Date resultdate = new Date(millis_date);
		 return sdf.format(resultdate); 
		 } 
} 