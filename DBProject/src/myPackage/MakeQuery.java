package myPackage;

import java.util.ArrayList;
//This is class for making query about all situation(Current,Historical,Nested Historical)
public class MakeQuery {

	//This is a method for making query
	public String makeQuery(String jsonTString)
	{
		String showQuery = new String();
		
		ArrayList<Relation> setRelation = new ArrayList<Relation>();
		ArrayList<Relation> setRelation1 = new ArrayList<Relation>();
		ArrayList<Relation> setRelation2 = new ArrayList<Relation>();

		ArrayList<Node> ERclass = new ER_Maker().getER(jsonTString); 
		//Call each algorithm(Current Nested Historical)
		Algorithm_Nested a = new Algorithm_Nested();
		Algorithm_General b = new Algorithm_General();
		Algorithm_Historical c = new Algorithm_Historical();

		setRelation = a.makingNested(ERclass);
		setRelation1 = b.makingGeneral(ERclass);
		setRelation2 = c.makingHistorical(ERclass);
		//Generate Current Query
		showQuery += "<h2 style=" + "'font-family:verdana'" + ">Generate Current Query<h2>";
		for (Relation relation : setRelation1) {
			showQuery += "create table "+relation.getName()+"(<br>";
			for (String key : relation.getKeyLists()) {
				showQuery += key+" varchar(255),<br>";
			}
			for (String attr : relation.getAttLists()) {
				showQuery += attr+" varchar(255),<br>";
			}
			showQuery += "primary key(";
			for (String key : relation.getKeyLists()) {
				showQuery += key+",";
			}
			showQuery=showQuery.substring(0,showQuery.length()-1);
			showQuery+="));<br><br>";
		}
		showQuery += "<h2 style=" + "'font-family:verdana'" + ">Generate Historical Query<h2>";
		
		//Generate Historical Query
		for (Relation relation : setRelation2) {
			showQuery += "create table "+relation.getName()+"(<br>";
			for (String key : relation.getKeyLists()) {
				showQuery += key+" varchar(255),<br>";
			}
			for (String attr : relation.getAttLists()) {
				showQuery += attr+" varchar(255),<br>";
			}
			showQuery += "primary key(";
			for (String key : relation.getKeyLists()) {
				showQuery += key+",";
			}
			showQuery=showQuery.substring(0,showQuery.length()-1);
			showQuery+="));<br><br>";
		}
		showQuery += "<h2 style=" + "'font-family:verdana'" + ">Generate Nested Relation Query<h2>";
		
		//Generate Historical Nested Query
		for(Relation relation:setRelation)
		{
			for (Relation attr : relation.getNestedGroup()) {//first layer group
				showQuery+="create type "+attr.getName()+" as object(<br>" ;
				for (String key : attr.getKeyLists()) {
					showQuery+=key+" varchar(255),<br>";
				}
				for (String att : attr.getAttLists()) {
					showQuery+=att+" varchar(255),<br>";
				}
				showQuery += "primary key(";
				for (String key : attr.getKeyLists()) {
					showQuery+=key+",";
				}
				showQuery=showQuery.substring(0,showQuery.length()-1);
				showQuery+="));<br><br>";
				showQuery+="create type "+attr.getName()+" is table of "+attr.getName()+";<br><br>";
				if(attr.getNestedGroup().size()!=0)
				{
					for (Relation tmp : attr.getNestedGroup()) {
						showQuery+="create type "+tmp.getName()+" as object(<br>" ;
						for (String key : tmp.getKeyLists()) {
							showQuery+=key+" varchar(255),<br>";
						}
						for (String att : tmp.getAttLists()) {
							showQuery+=att+" varchar(255),<br>";
						}
						showQuery += "primary key(";
						for (String key : tmp.getKeyLists()) {
							showQuery+=key+",";
						}
						showQuery=showQuery.substring(0,showQuery.length()-1);
						showQuery+="));<br><br>";
						showQuery+="create type "+tmp.getName()+" is table of "+tmp.getName()+";<br><br>";
						}
				}
			}
			showQuery+="create table "+relation.getName()+" (<br>" ;
			for (String key : relation.getKeyLists()) {
				showQuery+=key+" varchar(255),<br>";
			}
			for (String att : relation.getAttLists()) {
				showQuery+=att+" varchar(255),<br>";
			}
			
			for (Relation attr : relation.getNestedGroup()) {//first group
				showQuery+=attr.getName()+" "+attr.getName()+"<br>";
				if(attr.getNestedGroup().size()!=0)
				{
					for (Relation tmp : attr.getNestedGroup()) {
						showQuery+=tmp.getName()+" "+tmp.getName()+"<br>";			
						}
				}
			}
			showQuery += "primary key(";
			for (String key : relation.getKeyLists()) {
				showQuery+=key+",";
			}
			showQuery=showQuery.substring(0,showQuery.length()-1);
			showQuery+="))";
			
			if(relation.getNestedGroup().size()!=0){
			showQuery+=" nested table ";
			for (Relation attr : relation.getNestedGroup()) {//first layer group
				showQuery+=attr.getName()+", ";
				if(attr.getNestedGroup().size()!=0)
				{
					for (Relation tmp : attr.getNestedGroup()) {
						showQuery+=tmp.getName()+", ";			
						}
				}
			}
			showQuery=showQuery.substring(0,showQuery.length()-1);
			showQuery+=relation.getName()+" N";
			}
			showQuery+=";<br><br>";
		}
	return showQuery;
	}
}
