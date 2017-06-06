package myPackage;

import java.util.ArrayList;

import org.json.simple.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ER_Maker {

	private ArrayList<Node> ER = new ArrayList<Node>();
	private ArrayList<Attribute> attArr = new ArrayList<Attribute>();
	private JSONObject json = null;

	public ArrayList<Node> getER(String txt) {
		JSONParser parser = new JSONParser();
		try {
			json = (JSONObject) parser.parse(txt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray nodeArr = (JSONArray) json.get("nodeDataArray");
		JSONArray linkArr = (JSONArray) json.get("linkDataArray");
		// JSONArray

		// first, make instance for entity node and relationship node
		for (int i = 0; i < nodeArr.size(); i++) {
			String name = ((JSONObject) nodeArr.get(i)).get("text").toString();
			if (name.contains("&#7488;"))
				name.substring(0, name.length() - 8);
			if (((JSONObject) nodeArr.get(i)).get("type").toString().compareTo("E") == 0) {
				Entity temp = new Entity(name);
				temp.setKey(Integer.parseInt(((JSONObject) nodeArr.get(i)).get("key").toString()));
				if (((JSONObject) nodeArr.get(i)).get("isTemp").toString().compareTo("true") == 0)
					temp.setIsTemporal(true);
				ER.add(temp);
			} else if (((JSONObject) nodeArr.get(i)).get("type").toString().compareTo("R") == 0) {
				Relationship temp = new Relationship(name);
				temp.setKey(Integer.parseInt(((JSONObject) nodeArr.get(i)).get("key").toString()));
				if (((JSONObject) nodeArr.get(i)).get("isTemp").toString().compareTo("true") == 0)
					temp.setIsTemporal(true);
				ER.add(temp);
			} else if (((JSONObject) nodeArr.get(i)).get("type").toString().compareTo("A") == 0) {
				Attribute temp = new Attribute(name);
				temp.setKey(Integer.parseInt(((JSONObject) nodeArr.get(i)).get("key").toString()));
				if (((JSONObject) nodeArr.get(i)).get("isTemp").toString().compareTo("true") == 0)
					temp.setIsTemporal(true);
				attArr.add(temp);
			} else {
				// exception
			}
		}
		// linkData traverse for getting lot of information
		for (int i = 0; i < linkArr.size(); i++) {
			Node from = null, to = null;
			for (int j = 0; j < ER.size(); j++) {				
				if ((ER.get(j)).getKey() == Integer.parseInt(((JSONObject) linkArr.get(i)).get("from").toString()))
					from = ER.get(j);
				if ((ER.get(j)).getKey() == Integer.parseInt(((JSONObject) linkArr.get(i)).get("to").toString()))
					to = ER.get(j);
			}
			for (int j = 0; j < attArr.size(); j++) {
				if ((attArr.get(j)).getKey() == Integer.parseInt(((JSONObject) linkArr.get(i)).get("from").toString()))
					from = attArr.get(j);
				if ((attArr.get(j)).getKey() == Integer.parseInt(((JSONObject) linkArr.get(i)).get("to").toString()))
					to = attArr.get(j);
			}
			// We knew that which node is from and which node is to
			if (from != null && to != null) {
				// 'from' save 'to'			
				if (from.getClass().getName().compareTo("myPackage.Relationship") == 0) { // relationship type
					((Relationship) from).addList(to);
					if (((JSONObject) linkArr.get(i)).get("attriType") != null) {
						if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("m") == 0) { 
							((Attribute) to).setIsMulti(true);
						}
						else if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("k") == 0) {
							((Attribute) to).setIsKey(true);
						}
					}
					if (((JSONObject) linkArr.get(i)).get("type") != null)
					{
						if ((((JSONObject) linkArr.get(i)).get("type").toString()).compareTo("r") == 0) { 
							if((((JSONObject) linkArr.get(i)).get("to")!=null))
							{
								((Relationship)from).addMultiList( Integer.parseInt(((JSONObject) linkArr.get(i)).get("to").toString()),((JSONObject) linkArr.get(i)).get("multi").toString());
							}
							
						}
					}
				}
				if (to.getClass().getName().compareTo("myPackage.Relationship") == 0) { // relationship type
					if (((JSONObject) linkArr.get(i)).get("attriType") != null) {
						if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("m") == 0) { 
							((Attribute) from).setIsMulti(true);
						}
						else if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("k") == 0) {
							((Attribute) from).setIsKey(true);
						}
					}
					if (((JSONObject) linkArr.get(i)).get("type") != null)
					{
						if ((((JSONObject) linkArr.get(i)).get("type").toString()).compareTo("r") == 0) { 
							if((((JSONObject) linkArr.get(i)).get("from")!=null))
							{
								((Relationship)to).addMultiList(Integer.parseInt(((JSONObject) linkArr.get(i)).get("from").toString()),((JSONObject) linkArr.get(i)).get("multi").toString());
							}
							
						}
					}
				} 
				else if (from.getClass().getName().compareTo("myPackage.Entity") == 0) {
					((Entity) from).addList(to);
					// add attribute
					if (((JSONObject) linkArr.get(i)).get("attriType") != null) {
						if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("m") == 0) {
							((Attribute) to).setIsMulti(true);
						} else if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("k") == 0) {
							((Attribute) to).setIsKey(true);
						}	
					}
					if (((JSONObject) linkArr.get(i)).get("nToN") != null) {		
							((Attribute) to).setTypeOfMulti(((JSONObject) linkArr.get(i)).get("nToN").toString());	
					}
				} 
				else if (to.getClass().getName().compareTo("myPackage.Entity") == 0) {
				
					if (((JSONObject) linkArr.get(i)).get("attriType") != null) {
						if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("m") == 0) {
							((Attribute) from).setIsMulti(true);
						} else if ((((JSONObject) linkArr.get(i)).get("attriType").toString()).compareTo("k") == 0) {
							((Attribute) from).setIsKey(true);
						}
					}
					if (((JSONObject) linkArr.get(i)).get("nToN") != null) {		
						((Attribute) from).setTypeOfMulti(((JSONObject) linkArr.get(i)).get("nToN").toString());	
				}
				}
				else
				{
					//error
				}
				// 'to' save 'from'
				if (to.getClass().getName().compareTo("myPackage.Relationship") == 0) {
					((Relationship) to).addList(from);
				} else if (to.getClass().getName().compareTo("myPackage.Entity") == 0) {
					((Entity) to).addList(from);
				} else {
					// error
				}
			}
		}

		return ER;
	}
}
