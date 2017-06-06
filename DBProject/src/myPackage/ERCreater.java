package myPackage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ERCreater
 */
@WebServlet("/ERCreater")
public class ERCreater extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ERCreater() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public boolean isValid(String jsonString)
    {
    	Boolean isValid=false;
    	//System.out.println(jsonString);
		return isValid;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String ctrl="true";
		RequestDispatcher dispatcher=null;
        //out.println(request.getParameter("ERJson"));
        String jsonString = request.getParameter("ERJson");
        String target = "main.jsp";
  
       String valid = new ValidER(jsonString).makeErrorMsg();
       
       if(valid.trim().equals(""))
       {
    	   request.setAttribute("diagram", jsonString);
    	   request.setAttribute("isValid", true);
    	   dispatcher = request.getRequestDispatcher(target);
    	   dispatcher.forward(request, response);
       }
       else
       {
    	   request.setAttribute("error", valid);
    	   request.setAttribute("diagram", jsonString);
    	   request.setAttribute("isValid", false);
    	   dispatcher = request.getRequestDispatcher(target);
    	   dispatcher.forward(request, response);
       }
    	   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
