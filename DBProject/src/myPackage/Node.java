package myPackage;

public class Node {
   protected Boolean isTemporal=false;
   protected String name;
   protected int key;
   
   public Boolean getIsTemporal() {
      return isTemporal;
   }

   public void setIsTemporal(Boolean isTemporal) {
      this.isTemporal = isTemporal;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getKey() {
	   return key;
   }
   
   public void setKey(int key) {
	   this.key = key;
   }
}