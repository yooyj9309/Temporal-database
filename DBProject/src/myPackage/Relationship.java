package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

public class Relationship extends Node {

	private ArrayList<Node> lists = new ArrayList<Node>();// attribute 넣을 곳
	private HashMap<Integer,String> multi_Lists = new HashMap<Integer,String>();
	// relationship에 관련된 entity가 multi 인지 아닌지 판단

	public Relationship(String name) {
		this.name = name;
		this.isTemporal = false;

	}

	public void addList(int n, Node node) {
		lists.add(n, node);
	}
	public void addList(Node node) {
		lists.add(node);
	}
	public ArrayList<Node> getArrayList() {
		return this.lists;
	}

	public HashMap<Integer,String> getMulti_Lists() {
		return multi_Lists;
	}

	public void addMultiList(int key, String value)
	{
		this.multi_Lists.put(key,value);
	}
	public void setMulti_Lists(HashMap<Integer,String> multi_Lists) {
		this.multi_Lists = multi_Lists;
	}

	
}
