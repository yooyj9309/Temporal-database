package myPackage;

import java.util.ArrayList;
import java.util.Iterator;
//This class is for making general current database schema.

public class Algorithm_General {

   private ArrayList<Relation> R=new ArrayList<Relation>();//the variable to return a set of relation
   
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
// output : a set of relation to include key and normal attributes(Current)
   @SuppressWarnings("null")
   public ArrayList<Relation> makingGeneral(ArrayList<Node> D)
   {
      if(D!=null&&D.size()!=0)
      {
         for(Node node:D)
         {   
        	// If the node is entity,
            if(isEntity(node))
            {   
               Relation relation=new Relation();
               relation.setName(node.name);

               if(node.isTemporal)//If the node is Entity and temporal,
               {
                  relation.addAttLists("Start");
               }
               for(Node tmpNode:getAttributes(node)) // Get the node's a set of attributes
               {
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     
                     if(attNode.getIsKey()){// If the attribute is key,
                        relation.addKeyLists(attNode.name);
                     }
                     else{
                        relation.addAttLists(attNode.name);
                     }
                  }
               }
             
               for(Node tmpNode2:getAttributes(node))
               {
                  if(isAttribute(tmpNode2))
                  {
                     Attribute attNode = (Attribute)tmpNode2;
                     if(attNode.isTemporal&&!attNode.getIsMulti())//Single Temporal attribute
                     {
                        relation.addAttLists(attNode.name+"_Start");
                        System.out.println(attNode.getName()+"_Start");
                     }
                     else if(attNode.getIsMulti())//Multi-value attribute
                     {
                        relation.subAttLists(attNode.name);
                     }
                  }
               }
               R.add(relation);//Insert the relation into the relation set.  
               
               
               for(Node tmpNode:getAttributes(node))
               {   
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     boolean tmp = false;
                     if(!attNode.isTemporal&&attNode.getIsMulti())//if attribute is Non temporal multi-value,
                     {
                        Relation relation1=new Relation();
                        if(!tmp){
                        relation1.setName(node.name+"_"+attNode.getName());                      
                        tmp=true;
                        }
                           if(attNode.getTypeOfMulti().equals("1"))//The attribute is a 1:m relationship 
                           {
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation1.addAttLists(attNode2.name);
                                    }
                                 }
                              }
                              relation1.addKeyLists(attNode.name);
                           }
                           else 
                           {
                              for(Node tmpNode2:getAttributes(node))
                              {
                                 if(isAttribute(tmpNode2))
                                 {
                                    Attribute attNode2 = (Attribute)tmpNode2;
                                    if(attNode2.getIsKey()){
                                       relation1.addKeyLists(attNode2.name);
                                    }
                                 }
                              }
                              relation1.addKeyLists(attNode.name);
                           }
                           R.add(relation1);
                     }
                  }
               }//Non temporal multivalued attribute
           
               for(Node tmpNode:getAttributes(node))
               {
                  boolean tmp1 = false;
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     
                     if(attNode.isTemporal&&attNode.getIsMulti())// temporal multi-valued attribute
                     {
                        Relation relation2=new Relation();
                        if(!tmp1){
                        relation2.setName(node.name+"_"+attNode.getName());
                        tmp1=true;
                        
                        }
                           if(attNode.getTypeOfMulti().equals("1"))
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
                              relation2.addKeyLists(attNode.name);                        
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
                              relation2.addKeyLists(attNode.name);                        
                           }
                           relation2.addAttLists(attNode.name+"_Start");
                           R.add(relation2);
                     }
                  }
               }// temporal multivalued attribute
               System.out.println();
            
            }
            //If the node's type is a relationship, 
            else if(isRelationship(node))
            {
               Relation relation4 = new Relation();
               ArrayList<String> relationshipKeys=new ArrayList<String>();//A variable to get the key values ​​of the entity 
               															  //associated with the relationship
               relation4.setName(node.name);
               
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
                              relation4.addKeyLists(attNode2.name);
                              relationshipKeys.add(attNode2.name);          
                           }
                           else if(attNode2.getIsKey())
                           {
                        	   relation4.addAttLists(attNode2.name);
                           }
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
                                    relation4.addKeyLists(attNode3.name);
                                    relationshipKeys.add(attNode3.name);
                                 }
                                 else if(attNode3.getIsKey())
                                 {
                                	 relation4.addAttLists(attNode3.name);
                                 }
                              }
                           }
                        }
                     }
                  }
               }          
               if(node.isTemporal)
               {             
                  relation4.addAttLists("Start");
               }
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
               
               for(Node tmpNode2:getAttributes(node))
               {
                  if(isAttribute(tmpNode2))
                  {
                     Attribute attNode = (Attribute)tmpNode2;
                     if(attNode.isTemporal&&!attNode.getIsMulti())//Single Temporal attribute
                     {
                        relation4.addAttLists(attNode.name+"_Start");
                      
                     }
                  }
               }//temporal single valued attribute
               R.add(relation4);
               
               
               for(Node tmpNode:getAttributes(node))
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
                        relation5.addKeyLists(attNode.name);
                        tmp=true;
                        }
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
               }//Non temporal multi-valued attribute

               
               for(Node tmpNode:getAttributes(node))
               {
                  boolean tmp1 = false;
                  if(isAttribute(tmpNode))
                  {
                     Attribute attNode = (Attribute)tmpNode;
                     
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
                              for(String tmp:relationshipKeys)
                              {
                                 relation6.addKeyLists(tmp);
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
                           relation6.addAttLists(attNode.getName()+"_Start");
                        R.add(relation6);
                     }
                  }
               }// temporal multivalued attribute
            }
         }
         
      }
      return R;
      
   }
   

}