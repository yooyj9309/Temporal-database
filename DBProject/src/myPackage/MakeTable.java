package myPackage;

import java.util.ArrayList;

public class MakeTable {
	//This is class for making table about all situation(Current,Historical,Nested Historical)
	public String makeTable(String jsonTString) {

		String showTable = new String();
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
		showTable += "<h2 style=" + "'font-family:verdana'" + ">Generate Current Database Schema<h2>";
		
		//Generate Current Tables
		for (Relation relation : setRelation1) {
			if(relation!=null&&!relation.getName().equals("null")&&!relation.getName().trim().equals("")){
			showTable += "<table><caption>" + "Relation : " + relation.getName() + "</caption><tr>";
			if(relation.getKeyLists().size()!=0){
				showTable+="<td><u>";
			for (String key : relation.getKeyLists()) {
				showTable += key + " |";
			}
			showTable=showTable.substring(0,showTable.length()-1);
				showTable+="</u></td>";
			}
			for (String attr : relation.getAttLists()) {
				showTable += "<td>" + attr + "</td>";
			}
			showTable += "</tr></table><br><br>";
			}
			}

		showTable += "<h2 style=" + "'font-family:verdana'" + ">Generate Historical Database Schema<h2>";
		
		//Generate Historical Tables
		for (Relation relation : setRelation2) {
			if(relation!=null&&!relation.getName().equals("null")&&!relation.getName().trim().equals("")){
			showTable += "<table><caption>" + "Relation : " + relation.getName() + "_H</caption><tr>";
			if(relation.getKeyLists().size()!=0){
				showTable+="<td><u>";
			for (String key : relation.getKeyLists()) {
				showTable += key + " |";
			}
			showTable=showTable.substring(0,showTable.length()-1);
			showTable+="</u></td>";
			}
			for (String attr : relation.getAttLists()) {
				showTable += "<td>" + attr + "</td>";
			}
			showTable += "</tr></table><br><br>";
			}
		}

		showTable += "<h2 style=" + "'font-family:verdana'"
				+ ">Generate Nested Relations for Historical Database Schema<h2>";
		//Generate Nested Historical Tables
		for (Relation relation : setRelation) {
			if(relation!=null&&!relation.getName().equals("null")&&!relation.getName().trim().equals("")){
			showTable += "<br><table><caption>" + "Relation : " + relation.getName() + "_N</caption><tr>";
			if(relation.getKeyLists().size()!=0){
				showTable+="<td rowspan='3'><u>";
			for (String key : relation.getKeyLists()) {
				showTable += key + " |";
			}
			showTable=showTable.substring(0,showTable.length()-1);
			showTable+="</u></td>";
			}
			for (String attr : relation.getAttLists()) {
				showTable += "<td  rowspan='3'>" + attr + "</td>";
			}

			for (Relation attr : relation.getNestedGroup()) {
				if (attr.getNestedGroup().size() != 0)
					showTable += "<td colspan='"
							+ (1 + attr.getAttLists().size() + attr.getNestedGroup().size())
							+ "'>" +attr.getName()  + "</td>";
				else
					showTable += "<td colspan='" + (1 + attr.getAttLists().size()) + "'>"
							+ attr.getName()+"" + "</td>";
				
				System.out.println("Number : "+(attr.getKeyLists().size() + attr.getAttLists().size() + attr.getNestedGroup().size()));
			}
			showTable += "</tr><tr>";
			for (Relation attr : relation.getNestedGroup()) {
				if (attr.getNestedGroup().size() == 0) {
					if(attr.getKeyLists().size()!=0){
						showTable+="<td rowspan='2'><u>";
					for (String att : attr.getKeyLists()) {
						showTable += att + " |";
					}
					showTable=showTable.substring(0,showTable.length()-1);
						showTable+="</u></td>";
					}
					for (String att : attr.getAttLists()) {
						showTable += "<td rowspan='2'>" + att + "</td>";
					}
				}
			}
			for (Relation attr : relation.getNestedGroup()) {
				if (attr.getNestedGroup().size() != 0) {
					if(attr.getKeyLists().size()!=0){
						showTable+="<td rowspan='2'><u>";
					for (String att : attr.getKeyLists()) {
						showTable += att + " |";
					}
					showTable=showTable.substring(0,showTable.length()-1);
						showTable+="</u></td>";
					}
					for (String att : attr.getAttLists()) {
						showTable += "<td rowspan='2'>" + att + "</td>";
					}
					for (Relation tmp : attr.getNestedGroup()) {
						showTable += "<td>" + tmp.getName()  +"</td>";
					}
					showTable += "</tr><tr>";
					for (Relation tmp : attr.getNestedGroup()) {
						if(tmp.getKeyLists().size()!=0){
							showTable+="<td rowspan='2'><u>";
						for (String att : tmp.getKeyLists()) {
							showTable +=  att + " |";
						}
						showTable=showTable.substring(0,showTable.length()-1);
						showTable+="</u></td>";
						}
					}
				}
			}
			showTable += "</tr></table>";
		}
		}
		return showTable;
	}

}
