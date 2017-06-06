package myPackage;
// Attribute.getTypeOfMulti()==0 -> 1:1 relationship, 
//"             1 -> 1:m relationship,
//"             2 -> m:1 relationship,
//"             3 -> m:m relationship
public class Attribute extends Node {

	private Boolean isKey = false;
	private Boolean isMulti = false;
	private String typeOfMulti = "0";

	public Attribute(String name, boolean isKey, boolean isMulti) {

		// TODO Auto-generated constructor stub

		this.name = name;
		this.isTemporal = false;
		this.isKey = isKey;
		this.isMulti = isMulti;

	}

	public Attribute(String name) {

		this.name = name;

	}

	public Boolean getIsKey() {

		return isKey;

	}

	public void setIsKey(Boolean isKey) {

		this.isKey = isKey;

	}

	public Boolean getIsMulti() {

		return isMulti;

	}

	public void setIsMulti(Boolean isMulti) {

		this.isMulti = isMulti;

	}

	public String getTypeOfMulti() {
		return typeOfMulti;
	}

	public void setTypeOfMulti(String typeOfMulti) {
		this.typeOfMulti = typeOfMulti;
	}

}