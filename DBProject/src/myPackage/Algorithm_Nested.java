package myPackage;

import java.util.ArrayList;

//This class is for making nested historical database schema.
public class Algorithm_Nested {

	private ArrayList<Relation> R = new ArrayList<Relation>();// the variable to
																// return a set
																// of relation

	public boolean isEntity(Node d) {
		if (d.getClass().getSimpleName().equalsIgnoreCase("Entity"))
			return true;
		else
			return false;
	}

	public boolean isAttribute(Node d) {
		if (d.getClass().getSimpleName().equalsIgnoreCase("Attribute"))
			return true;
		else
			return false;
	}

	public boolean isRelationship(Node d) {
		if (d.getClass().getSimpleName().equalsIgnoreCase("Relationship"))
			return true;
		else
			return false;
	}

	public ArrayList<Node> getAttributes(Node node) {
		if (node.getClass().getSimpleName().equalsIgnoreCase("Entity"))
			return ((Entity) node).getArrayList();
		else if (node.getClass().getSimpleName().equalsIgnoreCase("Relationship"))
			return ((Relationship) node).getArrayList();
		else
			return null;
	}

	public ArrayList<String> getKey(Node node) {
		ArrayList<String> key = new ArrayList<String>();
		if (isEntity(node)) {
			for (Node tmpNode : ((Entity) node).getArrayList()) {
				if (isAttribute(tmpNode)) {
					Attribute attNode = (Attribute) tmpNode;
					if (attNode.getIsKey()) {
						key.add(attNode.name);
					}
				}
			}
		} else {
			for (Node tmpNode : ((Relationship) node).getArrayList()) {
				if (isAttribute(tmpNode)) {
					Attribute attNode = (Attribute) tmpNode;
					if (attNode.getIsKey()) {
						key.add(attNode.name);
					}
				}
			}
		}
		return key;
	}

	// Method to get Non Temporal Single attributes
	public ArrayList<String> get_NTS_Attribute(Node node) {
		ArrayList<String> Att = new ArrayList<String>();
		if (isEntity(node)) {
			for (Node tmpNode : ((Entity) node).getArrayList()) {
				if (isAttribute(tmpNode)) {
					Attribute attNode = (Attribute) tmpNode;
					if (!attNode.getIsKey() && !attNode.getIsMulti() && !attNode.isTemporal)
						Att.add(attNode.name);
				}
			}
		} else if (isRelationship(node)) {
			for (Node tmpNode : ((Relationship) node).getArrayList()) {
				if (isAttribute(tmpNode)) {
					Attribute attNode = (Attribute) tmpNode;
					if (!attNode.getIsKey() && !attNode.getIsMulti() && !attNode.isTemporal)
						Att.add(attNode.name);
				}
			}
		}
		return Att;
	}

	// Check whether this node is Temporal attribute or not.
	public boolean hasTA(Node node) {
		boolean result = false;
		if (isEntity(node)) {
			for (Node tmpNode : ((Entity) node).getArrayList()) {
				if (isAttribute(tmpNode)) {
					Attribute attNode = (Attribute) tmpNode;
					if (attNode.isTemporal) {
						result = true;
						break;
					}
				}
			}
		} else if (isRelationship(node)) {
			for (Node tmpNode : ((Relationship) node).getArrayList()) {
				if (isAttribute(tmpNode)) {
					Attribute attNode = (Attribute) tmpNode;
					if (attNode.isTemporal) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	// This is a method to make general current database.
	// input : a set of ER_Diagram's node
	// output : a set of relation to include key and normal attributes(Nested)

	public ArrayList<Relation> makingNested(ArrayList<Node> D) {
		if (D != null && D.size() != 0) {
			for (Node node : D) {
				if (isEntity(node)) {
					Relation relation = new Relation();
					relation.setName("");
					if (node.isTemporal) { // If a node is a temporal,
						relation.setName(node.name);
						relation.setKeyLists(getKey(node));
						relation.setAttLists(get_NTS_Attribute(node));// <K U S>
						relation.addAttLists("Start");
						relation.addAttLists("End");

						// Non temporal and multi-valued attribute
						for (Node tmpNode : ((Entity) node).getArrayList()) {
							if (isAttribute(tmpNode)) {
								Attribute attNode = (Attribute) tmpNode;
								if (!attNode.isTemporal && attNode.getIsMulti()) {
									Relation nestedRel = new Relation();
									nestedRel.setName(node.name + "_" + attNode.getName());
									nestedRel.addKeyLists(attNode.name);
									relation.addNestedLists(nestedRel);
								}
							}
						}
					}
					// If a node has some temporal attributes
					else if (hasTA(node)) {
						relation.setName(node.name);
						relation.setKeyLists(getKey(node));
					}

					else {
						relation.setName(node.name);
						relation.setKeyLists(getKey(node));
						relation.setAttLists(get_NTS_Attribute(node));// <K U S>

					} // Non temporal and multi-valued attribute
					for (Node tmpNode : ((Entity) node).getArrayList()) {
						if (isAttribute(tmpNode)) {
							Attribute attNode = (Attribute) tmpNode;
							if (!attNode.isTemporal && attNode.getIsMulti()) {
								Relation nestedRel = new Relation();
								nestedRel.setName(node.name + "_" + attNode.getName());
								nestedRel.addKeyLists(attNode.name);
								relation.addNestedLists(nestedRel);
							}
						}
					}

					for (Node tmpNode2 : ((Entity) node).getArrayList()) {
						if (isAttribute(tmpNode2)) {
							Attribute attNode = (Attribute) tmpNode2;
							Relation nestedRel2 = new Relation();
							nestedRel2.setName(node.name + "_" + attNode.getName());
							if (attNode.isTemporal)// If any attribute is
													// temporal,
							{
								nestedRel2.addKeyLists(attNode.name + "_Start");
								nestedRel2.addAttLists(attNode.name + "_End");
								// If a attribute is 1:1 or m:1 relationship
								if (attNode.getTypeOfMulti().equals("0") || attNode.getTypeOfMulti().equals("2")) {
									if (!attNode.getIsKey())
										nestedRel2.addAttLists(attNode.name);

								} else {
									if (!attNode.getIsKey())
										nestedRel2.addKeyLists(attNode.name);
								}
								relation.addNestedLists(nestedRel2);
							}

						}
					}
					R.add(relation);
				}
				// If a node is a relationship type,
				else if (isRelationship(node)) {
					Relation relation1 = new Relation();
					ArrayList<String> relationshipKeys = new ArrayList<String>();// P
					relation1.setName("");

					Relationship relNode = (Relationship) node;

					for (Node tmpNode : relNode.getArrayList()) {
						if (isEntity(tmpNode)) {
							String multi = relNode.getMulti_Lists().get(tmpNode.getKey());
							for (Node tmpNode2 : getAttributes(tmpNode)) {
								if (isAttribute(tmpNode2)) {
									Attribute attNode2 = (Attribute) tmpNode2;
									if (attNode2.getIsKey() && multi.equalsIgnoreCase("m")) {
										relationshipKeys.add(attNode2.name);
									} else if (attNode2.getIsKey())
										relation1.addAttLists(attNode2.name);
								}
							}
						} else if (isRelationship(tmpNode)) {
							String multi = relNode.getMulti_Lists().get(tmpNode.getKey());
							for (Node tmpNode2 : getAttributes(tmpNode)) {
								if (isEntity(tmpNode2)) {
									for (Node tmpNode3 : getAttributes(tmpNode2)) {
										if (isAttribute(tmpNode3)) {
											Attribute attNode3 = (Attribute) tmpNode3;
											if (attNode3.getIsKey() && multi.equalsIgnoreCase("m")) {
												relationshipKeys.add(attNode3.name);
											} else if (attNode3.getIsKey())
												relation1.addAttLists(attNode3.name);
										}
									}
								}
							}
						}
					}
					if (relNode.isTemporal) {
						relation1.setName(node.name);
						relation1.setKeyLists(relationshipKeys);
						Relation g1 = new Relation();
						g1.setName(relation1.getName());
						g1.addKeyLists("Start");

						g1.setAttLists(get_NTS_Attribute(relNode));
						g1.addAttLists("End");

						// Non temporal mutli value attribute
						for (Node tmpNode : relNode.getArrayList()) {
							if (isAttribute(tmpNode)) {
								Attribute attNode = (Attribute) tmpNode;
								if (!attNode.isTemporal && attNode.getIsMulti()) {
									Relation g2 = new Relation();
									g2.setName(g1.getName() + "_" + attNode.name);
									g2.addKeyLists(attNode.name);
									g1.addNestedLists(g2);
								}
							}
						}
						relation1.addNestedLists(g1);
					}
					// If a relationship has temporal attribute,
					else if (hasTA(relNode)) {
						relation1.setKeyLists(relationshipKeys);
						relation1.setKeyLists(getKey(relNode));
					} else {
						ArrayList<String> rkey = getKey(node);
						for (int i = 0; i < relationshipKeys.size(); i++) {
							rkey.add(relationshipKeys.get(i));
						}
						relation1.setName(node.name);
						relation1.setKeyLists(rkey);
						relation1.setAttLists(get_NTS_Attribute(node));// <K U
																		// S>
					} // Non temporal relationship, Non temporal multi-value
						// attribute
					for (Node tmpNode : ((Relationship) node).getArrayList()) {
						if (isAttribute(tmpNode)) {
							Attribute attNode = (Attribute) tmpNode;
							if (!attNode.isTemporal && attNode.getIsMulti() && !node.isTemporal) {
								relation1.addAttLists(attNode.name);
							}
						}
					}

					for (Node tmpNode3 : ((Relationship) node).getArrayList()) {
						if (isAttribute(tmpNode3)) {
							Attribute attNode = (Attribute) tmpNode3;
							Relation g1 = new Relation();
							g1.setName(attNode.name);
							if (attNode.isTemporal) {
								relation1.setName(node.name);
								g1.addKeyLists(attNode.name + "_Start");
								g1.addAttLists(attNode.name + "_End");
								if (attNode.getTypeOfMulti().equals("0") || attNode.getTypeOfMulti().equals("2")) {
									System.out.println(attNode.getTypeOfMulti());
									if (!attNode.getIsKey())
										g1.addAttLists(attNode.name);
								} else {
									System.out.println(attNode.getTypeOfMulti());
									if (!attNode.getIsKey())
										g1.addKeyLists(attNode.name);
								}
								relation1.addNestedLists(g1);
							}

						}
					}
					R.add(relation1);
				}

			}
		}
		return R;

	}

}
