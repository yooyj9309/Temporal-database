package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ValidER {

	private boolean isValid = false;
	private String jsonString = "hollo";
	private JSONObject json = null;

	private HashMap<String, String> keyText = new HashMap<String, String>();
	private HashMap<String, String> keyType = new HashMap<String, String>();

	public JSONArray nodeArr = new JSONArray();
	public JSONArray linkArr = new JSONArray();

	public ValidER(String jsonString) {
		// TODO Auto-generated constructor stub
		this.jsonString = jsonString;
	}

	public void makeJson() {
		JSONParser parser = new JSONParser();
		try {
			json = ((JSONObject) parser.parse(jsonString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nodeArr = (JSONArray) json.get("nodeDataArray");
		linkArr = (JSONArray) json.get("linkDataArray");
		// JSONArray

	}
//This method checks whether this ER-Diagram is correct or not.
//Input : X
//Output : String errorMsg -> if no error, the errorMsg == "" else according to situation, make errorMsg.
	public String makeErrorMsg() {
		
		JSONObject fromNode = null;
		JSONObject linkNode = null;
		JSONObject toNode = null;

		String from = "";
		String fromType = "";

		String errorMsg = "";
		
		String linkFrom = "";
		String linkTo = "";
		String linkType = "";
		String attrType = "";

		String to = "";
		String toType = "";

		int hasKey = 0;
		int entityNum=0;
		boolean validCondition = true;
		makeJson();
		ArrayList<String> compareName = new ArrayList<String>();
		for (int i = 0; i < nodeArr.size(); i++) {
			JSONObject node = ((JSONObject) nodeArr.get(i));
			String nodeName = node.get("text").toString().trim();
			//No node's name
			if (nodeName.equals("") || nodeName.equalsIgnoreCase("relationship") || nodeName.equalsIgnoreCase("entity")
					|| nodeName.equalsIgnoreCase("attribute")) {
				errorMsg += "Error : Please input node's name " + " " + "<br>";
			} 
			if (node.get("type").toString().trim().equalsIgnoreCase("e")
					|| node.get("type").toString().trim().equalsIgnoreCase("e")) {
				compareName.add(nodeName);
			}
			keyText.put(node.get("key").toString(), node.get("text").toString());
			keyType.put(node.get("key").toString(), node.get("type").toString());
		}
		
		// linkData traverse for getting lot of information
		for (int i = 0; i < linkArr.size(); i++) {
			JSONObject link = ((JSONObject) linkArr.get(i));
			linkType = link.get("type").toString().trim(); 
			if (link.get("to") == null)
				errorMsg += "Error : No link (to)!<br>";
			if (link.get("from") == null)
				errorMsg += "Error : No link (from)!<br>";
		}
		
		if ((nodeArr.size() == 0)) {//if the ER-diagram doesn't have node,
			errorMsg += "Error : Can't find any node";
			if ((linkArr.size() == 0))//No link & No node 
				errorMsg = "Error : Can't find any node and link";
		} else if ((linkArr.size() == 0)) {
			errorMsg += "Error : Can't find any link";
		} else {
			  for (int f = 0; f < nodeArr.size(); f++)
		         {
		            fromNode = (JSONObject) nodeArr.get(f);
		            from = fromNode.get("key").toString().trim();
		            fromType = fromNode.get("type").toString().trim();
		            if(fromType.equalsIgnoreCase("E"))
		            	entityNum++;
		            for (int l = 0; l < linkArr.size(); l++) {
		               linkNode = (JSONObject) linkArr.get(l);
		               linkType = linkNode.get("type").toString().trim();
		               if(linkNode.get("attriType")!=null)
		                  attrType = linkNode.get("attriType").toString().trim();

		               if (linkNode.get("to") == null) {
		                  validCondition=false;
		               } else
		                  linkTo = linkNode.get("to").toString().trim();

		               if (linkNode.get("from") == null) {
		                  validCondition=false;
		               } else
		                  linkFrom = linkNode.get("from").toString().trim();

		               if (linkType.equals("r")) {
		                  if (linkNode.get("multi") == null || linkNode.get("multi").toString().trim().equals("")) 
		                     validCondition=false;
		               }

		               if (validCondition&&linkFrom.equals(from))
		               {
		                  for (int t = 0; t < nodeArr.size(); t++) {
		                     toNode = (JSONObject) nodeArr.get(t);
		                     to = toNode.get("key").toString().trim();
		                     toType = toNode.get("type").toString().trim();

		                     if (linkTo.equals(to))
		                     {
		                        if (fromType.equalsIgnoreCase("e") && toType.equalsIgnoreCase("a")) {
		                      
		                           if (attrType.trim().equals("k"))// Check whether the relation has a key or not
		                              hasKey++;
		                           else if (linkType.equals("r"))// 
		                              errorMsg += "Error : This link is not correct! [ " + fromNode.get("text").toString() + " - "+ toNode.get("text") + "]<br>";
		                        } 
		                        else if (fromType.equalsIgnoreCase("a") && toType.equalsIgnoreCase("e")) {
		                        
			                           if (attrType.trim().equals("k"))// Check whether the relation has a key or not
			                              hasKey++;
			                           else if (linkType.equals("r"))// 
			                              errorMsg += "Error : This link is not correct! [ " + fromNode.get("text").toString() + " - "+ toNode.get("text") + "]<br>";
			                        }
		                        else if (fromType.equalsIgnoreCase("a") && toType.equalsIgnoreCase("a"))
		                           errorMsg += "Error : Please remove link! [ " + fromNode.get("text").toString() + " - "+ toNode.get("text").toString() + "]<br>";
		                          else if (fromType.equalsIgnoreCase("e") && toType.equalsIgnoreCase("e"))
		                           errorMsg += "Error : Please remove link! [ " + fromNode.get("text").toString() + " - "+ toNode.get("text").toString() + "]<br>";
		                          else if((fromType.equalsIgnoreCase("e")&&toType.equalsIgnoreCase("r"))||(fromType.equalsIgnoreCase("r")&&toType.equalsIgnoreCase("e"))){
		                           if(!linkType.equals("r"))
		                           {
		                              errorMsg+="Error : Please link correctly! [ "+keyText.get(from)+" - "+keyText.get(to)+"<br>";
		                           }
		                          }
		                          else if(fromType.equalsIgnoreCase("r")&&toType.equalsIgnoreCase("a"))
		                          {
		                             if(linkType.equalsIgnoreCase("r"))
		                                errorMsg += "Error : This link is not correct! [ " + fromNode.get("text").toString() + " - "+ toNode.get("text") + "]<br>";
		                          }
		                          else if(fromType.equalsIgnoreCase("a")&&toType.equalsIgnoreCase("r"))
		                          {
		                             if(linkType.equalsIgnoreCase("r"))
		                                errorMsg += "Error : This link is not correct! [ " + fromNode.get("text").toString() + " - "+ toNode.get("text") + "]<br>";    
		                          }
		                     }
		                  }
		               }
		            }	            
		         }
			 System.out.println("Entity number : "+entityNum + "HasKey number : "+ hasKey);
			  if ((hasKey<entityNum)||(hasKey==0&&entityNum==0)){
	               errorMsg += "Error : Can't find any KEY!<br>";
	            }
		      }
		      return errorMsg;

		   }
		}