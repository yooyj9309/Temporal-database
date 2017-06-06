package myPackage;

import java.util.ArrayList;

public class Entity extends Node{
	
	private ArrayList<Node> lists=new ArrayList<Node>();
	
	public Entity(String name)
	{
		this.name=name;
		this.isTemporal=false;
	}
	public ArrayList<Node> getArrayList() {
		return this.lists;
	}
	public void addList(int n,Node node)
	{
		lists.add(n,node);
	}
	public void addList(Node node) {
		lists.add(node);
	}
}
