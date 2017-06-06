package myPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

/**
 * Servlet implementation class Translate
 */
@WebServlet("/Translate")
public class Translate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Translate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    PrintWriter out = response.getWriter();
	    RequestDispatcher dispatcher=null;
	    String jsonTString = request.getParameter("ERJson3");
	    String query = new String();
	    String table = new String();
	    
	    MakeTable mt = new MakeTable();
	    MakeQuery mk = new MakeQuery();
	    
	    table=mt.makeTable(jsonTString).replace(" &#7488;", "");
	    query =mk.makeQuery(jsonTString).replace(" &#7488;", "");
	    
	    request.setAttribute("table", table);
	    request.setAttribute("query", query);
	    
	    dispatcher = request.getRequestDispatcher("translateResult.jsp");
 	   	dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
