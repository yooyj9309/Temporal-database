package myPackage;

import java.util.ArrayList;
//This is class for saving relation's information.
public class Relation {

	private String name; //Relation's name
	private ArrayList<String> keyLists= new ArrayList<String>(); //Relation's keys
	private ArrayList<String> attLists= new ArrayList<String>(); //Relation's attributes
	private ArrayList<Relation> nestedGroup = new ArrayList<Relation>(); //Relation's nested group
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getKeyLists() {
		return keyLists;
	}
	public void setKeyLists(ArrayList<String> lists) {
		for(String str:lists)
		{
			if(!lists.contains("str"))
			this.keyLists.add(str);
		}
	}
	public void addKeyLists(String keyLists) {
		this.keyLists.add(keyLists);
	}
	public void subKeyLists(String keyLists) {
		this.keyLists.remove(keyLists);
	}
	
	public void setAttLists(ArrayList<String> lists)
	{
		for(String str:lists)
		{
			if(!lists.contains("str"))
			this.attLists.add(str);
		}
	}
	public ArrayList<String> getAttLists() {
		return attLists;
	}
	
	public void addAttLists(String attLists) {
		this.attLists.add(attLists);
	}
	public void subAttLists(String keyLists) {
		this.attLists.remove(keyLists);
	}
	
	public ArrayList<Relation> getNestedGroup() {
		return nestedGroup;
	}
	public void setNestedGroup(ArrayList<Relation> nestedGroup) {
		this.nestedGroup = nestedGroup;
	}
	public void addNestedLists(Relation attLists) {
		this.nestedGroup.add(attLists);
	}
}
