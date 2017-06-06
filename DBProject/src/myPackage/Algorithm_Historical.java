package myPackage;

import java.util.ArrayList;
import java.util.Iterator;
//This class is for making general historical database schema.
public class Algorithm_Historical {

   private ArrayList<Relation> R=new ArrayList<Relation>();
   
   public boolean isEntity(Node d)
   {
      if(d.getClass().getSimpleName().equalsIgnoreCase("Entity"))
         return true;
      else
         return false;
   }
   public boolean isAttribute(Node d)
   {
      if(d.getClass().getSimpleName().equalsIgnoreCase("Attribute"))
         return true;
      else
         return false;
   }
   public boolean isRelationship(Node d)
   {
      if(d.getClass().getSimpleName().equalsIgnoreCase("Relationship"))
         return true;
      else
         return false;
   }
 //Method to get a node's attributes
   public ArrayList<Node> getAttributes(Node node)
   {
      if(node.getClass().getSimpleName().equalsIgnoreCase("Entity"))
         return ((Entity)node).getArrayList();
      else if(node.getClass().getSimpleName().equalsIgnoreCase("Relationship"))
         return((Relationship)node).getArrayList();
      else
         return null;
   }
 //This is a method to make general current database.
// input : a set of ER_Diagram's node
// output : a set of relation to include key and normal attributes(historical).
   @SuppressWarnings("null")
   public ArrayList<Relation> makingHistorical(ArrayList<Node> D)
   {
      if(D!=null&&D.size()!=0)
      {
         for(Node node:D)
         {   
            if(isEntity(node))
            {           
               if(node.isTemporal)
               {
            	  Relation relation=new Relation();
                  relation.setName(node.name);
                  relation.addAttLists("Start");
                  relation.addAttLists("End");
                  
               for(Node tmpNode:getAttributes(node))
               {
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     
                     if(attNode.getIsKey()){
                        relation.addKeyLists(attNode.name);
                     }
                     else if(!attNode.isTemporal&&!attNode.getIsMulti()){
                        relation.addAttLists(attNode.name);
                     }
                  }
               }
               R.add(relation);
            }
               
            // temporal multi-valued attribute
               for(Node tmpNode:getAttributes(node))
               {          
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     boolean tmp1 = false;
                     if(attNode.isTemporal&&attNode.getIsMulti())
                     {
                        Relation relation2=new Relation();
                        if(!tmp1){
                        relation2.setName(node.name+"_"+attNode.name);
                        tmp1=true;
                        }
                        relation2.addKeyLists(attNode.name);                        
                           if(attNode.getTypeOfMulti().equals("1")) //The attribute is a 1:m relationship
                           {
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation2.addAttLists(attNode2.name);
                                    }
                                 }
                              }
                              
                           }
                           else{
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation2.addKeyLists(attNode2.name);
                                    }
                                 }
                              }
                           }
                           relation2.addKeyLists(attNode.name+"_Start");
                           relation2.addAttLists(attNode.name+"_End");
                           R.add(relation2);
                     }
                  }
               }
               
               //Temporal Single value attribute
               for(Node tmpNode:getAttributes(node))
               {
                  
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     boolean tmp1 = false;
                     if(attNode.isTemporal&&!attNode.getIsMulti())
                     {
                        Relation relation3=new Relation();
                        if(!tmp1){
                        relation3.setName(node.name+"_"+attNode.name);
                        tmp1=true;
                        }
                        relation3.addAttLists(attNode.name);                        
                           if(attNode.getTypeOfMulti().equals("1")) //The attribute is a 1:m relationship
                           {
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation3.addAttLists(attNode2.name);
                                    }
                                 }
                              }
                              
                           }
                           else{
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation3.addKeyLists(attNode2.name);
                                    }
                                 }
                              }
                           }
                           relation3.addKeyLists(attNode.name+"_Start");
                           relation3.addAttLists(attNode.name+"_End");
                           R.add(relation3);
                     }
                  }
               }
            }
            //If the node is a relationship type,
            else if(isRelationship(node))
            {
               Relation relation4 = new Relation();
               ArrayList<String> relationshipKeys=new ArrayList<String>();
               relation4.setName("");
               
               Relationship relNode=(Relationship)node;
               for(Node tmpNode:relNode.getArrayList())
               {   
                  if(isEntity(tmpNode))
                  {
                	  String multi = relNode.getMulti_Lists().get(tmpNode.getKey());
                     for(Node tmpNode2:getAttributes(tmpNode))
                     {
                        if(isAttribute(tmpNode2))
                        {
                           Attribute attNode2 = (Attribute)tmpNode2;
                           if(attNode2.getIsKey()&&multi.equalsIgnoreCase("m")){
                              //relation4.addKeyLists(attNode2.name);
                              relationshipKeys.add(attNode2.name);
                           }
                           else if(attNode2.getIsKey())
                        	   relation4.addAttLists(attNode2.name);
                        }
                     }
                  }
                  else if(isRelationship(tmpNode))
                  {
                	  String multi = relNode.getMulti_Lists().get(tmpNode.getKey());
                     for(Node tmpNode2:getAttributes(tmpNode))
                     {
                        if(isEntity(tmpNode2))
                        {
                           for(Node tmpNode3:getAttributes(tmpNode2))
                           {   
                              if(isAttribute(tmpNode3))
                              {
                                 Attribute attNode3 = (Attribute)tmpNode3;
                                 if(attNode3.getIsKey()&&multi.equalsIgnoreCase("m")){
                                   // relation4.addKeyLists(attNode3.name);
                                    relationshipKeys.add(attNode3.name);
                                 }
                                 else if(attNode3.getIsKey())
                              	   relation4.addAttLists(attNode3.name);
                              }
                           }
                        }
                     }
                  }
               }
             //A variable to get the key values ​​of the entity associated with the relationship
               if(node.isTemporal)
               {
            	  relation4.setName(node.name);
            	  relation4.setKeyLists(relationshipKeys);
                  relation4.addKeyLists("Start");
                  relation4.addAttLists("End");
               
               for(Node tmpNode:getAttributes(node))
               {
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     
                     if(attNode.getIsKey()){
                        relation4.addKeyLists(attNode.name);
                     }
                     else if(!attNode.isTemporal&&!attNode.getIsMulti()){
                        relation4.addAttLists(attNode.name);
                     }
                  }
               }
            }
               /*////Single Temporal attribute
               for(Node tmpNode2:getAttributes(node))
               {
                  if(isAttribute(tmpNode2))
                  {
                     Attribute attNode = (Attribute)tmpNode2;
                     if(attNode.isTemporal&&!attNode.getIsMulti())
                     {
                        relation4.addKeyLists(attNode.name+"_Start");
                        relation4.addAttLists(attNode.name+"_End");
                     }
                     else if(attNode.getIsMulti())
                     {
                        relation4.subAttLists(attNode.name);
                     }
                  }
               }*/
               R.add(relation4);
    
             //Non temporal multivalued attribute
              /* for(Node tmpNode:getAttributes(node))
               {
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     boolean tmp = false;      
                     if(!attNode.isTemporal&&attNode.getIsMulti())
                     {
                        
                        Relation relation5=new Relation();
                        if(!tmp){
                        relation5.setName(node.name+"_"+attNode.name);
                        tmp=true;                   
                        }
                        relation5.addKeyLists(attNode.name);
                        
                           if(attNode.getTypeOfMulti().equals("1"))
                           {
                              relation5.setAttLists(relationshipKeys);
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation5.addAttLists(attNode2.name);
                                    }
                                 }
                              }
                              
                           }
                           else
                           {
                              for(String tmp2:relationshipKeys)
                              {
                                 relation5.addKeyLists(tmp2);
                              }
                              
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation5.addKeyLists(attNode2.name);
                                    }
                                 }
                              }
                           }
                           R.add(relation5);
                     }
                  }
               }*/
            
               // temporal multi-valued attribute
               for(Node tmpNode:getAttributes(node))
               {
                  
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     boolean tmp1 = false;
                     if(attNode.isTemporal&&attNode.getIsMulti())
                     {
                        Relation relation6=new Relation();
                        if(!tmp1){
                        relation6.setName(node.name+"_"+attNode.name);
                        
                        tmp1=true;
                        }
                        relation6.addKeyLists(attNode.getName());   
                           if(attNode.getTypeOfMulti().equals("1"))
                           {
                              relation6.setAttLists(relationshipKeys);
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation6.addAttLists(attNode2.getName());
                                    }
                                 }
                              }
                              
                              
                           }
                           else{
                              for(String tmp2:relationshipKeys)
                              {
                                 relation6.addKeyLists(tmp2);
                              }
                              
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation6.addKeyLists(attNode2.getName());
                                    }
                                 }
                              }
                        
                              
                           }
                           relation6.addKeyLists(attNode.getName()+"_Start");
                           relation6.addAttLists(attNode.getName()+"_End");
                        R.add(relation6);
                     }
                  }
               }
               
            // temporal Single-valued attribute
               for(Node tmpNode:getAttributes(node))
               {
                  
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     boolean tmp1 = false;
                     if(attNode.isTemporal&&!attNode.getIsMulti())
                     {
                        Relation relation7=new Relation();
                        if(!tmp1){
                        relation7.setName(node.name+"_"+attNode.name);
                        
                        tmp1=true;
                        }
                        relation7.addAttLists(attNode.getName());   
                           if(attNode.getTypeOfMulti().equals("1"))
                           {
                              relation7.setAttLists(relationshipKeys);
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation7.addAttLists(attNode2.getName());
                                    }
                                 }
                              }         
                           }
                           else{
                              for(String tmp2:relationshipKeys)
                              {
                                 relation7.addKeyLists(tmp2);
                              }
                              
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation7.addKeyLists(attNode2.getName());
                                    }
                                 }
                              }                
                           }
                           relation7.addKeyLists(attNode.getName()+"_Start");
                           relation7.addAttLists(attNode.getName()+"_End");
                        R.add(relation7);
                     }
                  }
               }
            }
         }
         
      }
      return R;
      
   }
   

}