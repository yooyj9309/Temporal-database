<!DOCTYPE html>
<%@ page import="java.util.*"%>
<html>
<head>
<title>NUS_Web</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel='stylesheet' href='contextmenu.css' />
<link rel='stylesheet' href='contextmenu2.css' />
<link rel='stylesheet' href='contextmenu3.css' />
<style>
th, td {
	padding: 5px;
	text-align: left;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<script src="go.js"></script>
<script src="go-debug.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

</head>
<body onload="init()">

	<div id="sample">
		<div style="width: 100%; white-space: nowrap;">
			<span
				style="display: inline-block; vertical-align: top; padding: 5px; width: 280px">
				<div id="myPaletteDiv"
					style="border: solid 1px black; height: 620px"></div>
			</span> <span
				style="display: inline-block; vertical-align: top; padding: 5px; width: 64%">
				<div id="myDiagramDiv"
					style="border: solid 1px black; height: 620px"></div>
				<div id="contextMenu">
					<ul>
						<li id="NtoN" style="background: chartreuse;"
							onclick="cxcommand(event)" name="one"><a href="#"
							target="_self" name="one">one</a></li>
						<li id="NtoN" style="background: chartreuse;"
							onclick="cxcommand(event)" name="many"><a href="#"
							target="_self" name="many">many</a></li>

					</ul>

				</div>
				<div id="contextMenu2">
					<ul>
						<li id="linkType" onclick="cxcommand(event)" name="1to1"><a
							href="#" target="_self" name="1to1">1 to 1</a></li>
						<li id="linkType" onclick="cxcommand(event)" name="1toM"><a
							href="#" target="_self" name="1toM">1 to M</a></li>
						<li id="linkType" onclick="cxcommand(event)" name="Mto1"><a
							href="#" target="_self" name="Mto1">M to 1</a></li>
						<li id="linkType" onclick="cxcommand(event)" name="MtoM"><a
							href="#" target="_self" name="MtoM">M to M</a></li>
				
					</ul>
				</div>

				<div id="contextMenu3">
					<ul>
					</ul>
				</div>
				<div id="default"></div>

			</span> <span
				style="display: inline-block; vertical-align: top; padding: 7px; width: 250px">
				<div style="height: 620px; vertical-align: top">
					<table>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Create" id="CreateButton" value="Create"
								onclick="location.href='main.jsp'"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning" name="Load"
								id="LoadButton" value="Load" onclick="openChild()"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<a id="down" href="" download="">
								<td><Input type="submit" class="btn btn-warning"
									name="Save" id="SavedButton" value="Save" onclick="save()"
									disabled="disabled"
									style="font-size: 50px; width: 230px; height: 90px"></td>
							</a>
						</tr>
						<tr>
							<form ACTION="http://localhost:8080/DBProject/ERCreater" 
							method="POST">
								<input type="hidden" id="ERJson" name="ERJson" value="" /> 
								<input type="submit" id="submit" style="display: none;" />
							</form>
							<td><Input type="submit" class="btn btn-warning"
								name="Validate" id="ValidateButton" value="Validate"
								disabled="disabled" onclick="validClick()"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<form ACTION="http://localhost:8080/DBProject/annotate.jsp"
								method="POST">
								<input type="hidden" id="ERJson2" name="diagram" value="" /> 
								<input type="submit" id="submit_anno" style="display: none;" />
							</form>
							<td><Input type="submit" class="btn btn-warning"
								name="Annotate" id="AnnotateButton" value="Annotate"
								disabled="disabled" onClick="annotateClick()"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Translate" id="TranslateButton" value="Translate"
								disabled="disabled"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Query" id="QueryButton" value="Query" disabled="disabled"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
					</table>
				</div>

			</span>
		</div>
		<Span
			style="display: inline-block; vertical-align: top; padding: 5px; width: 83%">
			<div>

				<div
					style="overflow: scroll; border: solid 1px black; height: 100px">
					<span style="color: red; font-size: 20px">${error}</span>
				</div>

			</div>
		</Span>

		<div id="invisible">
			<div>
				<button id="SaveButton">Save</button>
				Diagram Model saved in JSON format:
			</div>
			<textarea id="mySavedModel" style="width: 100%; height: 300px">
			<% 
				String diagram = (String)(request.getAttribute("diagram"));
				if(diagram != null) { %>
					${diagram}
			<%  } else { %>{ "class": "go.GraphLinksModel","linkFromPortIdProperty": "fromPort","linkToPortIdProperty": "toPort","nodeDataArray": [],"linkDataArray": []}<% } %>
    		</textarea>
		</div>
		<Span
			style="display: inline-block; vertical-align: top; padding: 5px;">
			<textarea id="mySavedModel2" style="width: 100%; height: 300px"
				name="ERJson">{ "class": "go.GraphLinksModel","linkFromPortIdProperty": "fromPort","linkToPortIdProperty": "toPort","nodeDataArray": [],"linkDataArray": []}</textarea>
	</div>
	</Span>
</body>
<script>
	$(document).ready(function(){
	//jQuery methods go here ...
    $("#invisible").hide();
	$("#mySavedModel2").hide();
	});

	var openWin;
	var initial = true;
	var defaultJson = '{ "class": "go.GraphLinksModel","linkFromPortIdProperty": "fromPort","linkToPortIdProperty": "toPort","nodeDataArray": [],"linkDataArray": []}';
	
	function init() {

		var str = document.getElementById("mySavedModel2").innerHTML;
		
		//check whether it is after validate or not
		
		
		if (str == defaultJson && <%= (request.getAttribute("diagram")==null) %>) {
			document.getElementById("SavedButton").disabled = true;
			document.getElementById("ValidateButton").disabled = true;
		} else {
			document.getElementById("SavedButton").disabled = false;
			document.getElementById("ValidateButton").disabled = false;
		}
		
		//check whether there is error or not
		if(<%= request.getAttribute("isValid") %>) {
			document.getElementById("AnnotateButton").disabled = false;
		}
		//if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
		var $ = go.GraphObject.make; // for conciseness in defining templates
		myDiagram = $(go.Diagram, "myDiagramDiv", // must name or refer to the DIV HTML element
		{
			grid : $(go.Panel, "Grid", $(go.Shape, "LineH", {
				stroke : "lightgray",
				strokeWidth : 0.5
			}), $(go.Shape, "LineH", {
				stroke : "gray",
				strokeWidth : 0.5,
				interval : 10
			}), $(go.Shape, "LineV", {
				stroke : "lightgray",
				strokeWidth : 0.5
			}), $(go.Shape, "LineV", {
				stroke : "gray",
				strokeWidth : 0.5,
				interval : 10
			})),
			allowDrop : true, // must be true to accept drops from the Palette
			"draggingTool.dragsLink" : true,
			"draggingTool.isGridSnapEnabled" : true,
			"linkingTool.isUnconnectedLinkValid" : false,
			"linkingTool.portGravity" : 20,
			"relinkingTool.isUnconnectedLinkValid" : true,
			"commandHandler.archetypeGroupData" : {
		            text : "Group",
		            isGroup : true,
		            color : "blue"
		         },
			"relinkingTool.portGravity" : 20,
			"relinkingTool.fromHandleArchetype" : $(go.Shape, "Diamond", {
				segmentIndex : 0,
				cursor : "pointer",
				desiredSize : new go.Size(8, 8),
				fill : "tomato",
				stroke : "darkred"
			}),
			"relinkingTool.toHandleArchetype" : $(go.Shape, "Diamond", {
				segmentIndex : -1,
				cursor : "pointer",
				desiredSize : new go.Size(8, 8),
				fill : "darkred",
				stroke : "tomato"
			}),
			"linkReshapingTool.handleArchetype" : $(go.Shape, "Diamond", {
				desiredSize : new go.Size(7, 7),
				fill : "lightblue",
				stroke : "deepskyblue"
			}),
			rotatingTool : $(TopRotatingTool), // defined below
			"rotatingTool.snapAngleMultiple" : 15,
			"rotatingTool.snapAngleEpsilon" : 15,
			"undoManager.isEnabled" : true,
			"ModelChanged": function(e) { if (e.isTransactionFinished) {
					saveDiagramProperties(); // do this first, before writing to JSON
					var current = myDiagram.model.toJson();
					if(initial==true) {
						initial = false;
					} else {
						if(current == <%= (String)(request.getAttribute("diagram"))%> ) {
						} else {
							document.getElementById("AnnotateButton").disabled = true;
						}
					}
					
					//check It is empty or not
					var count = 0;
					myDiagram.nodes.each(function(node){
						count++;
					});
					myDiagram.links.each(function(link){
						count++;
					});
					if (count == 0) {
						document.getElementById("SavedButton").disabled = true;
						document.getElementById("ValidateButton").disabled = true;
					} else {
						document.getElementById("SavedButton").disabled = false;
						document.getElementById("ValidateButton").disabled = false;
					}
					updateStates();
				}
			}

		});

		// when the document is modified, add a "*" to the title and enable the "Save" button
		myDiagram
				.addDiagramListener(
						"Modified",
						function(e) {
							var button = document.getElementById("SaveButton");
							if (button)
								button.disabled = !myDiagram.isModified;
							var idx = document.title.indexOf("*");
							if (myDiagram.isModified) {
								if (idx < 0) {
									document.title += "*";
									document.title = document.title.substr(0,
											idx);
									saveDiagramProperties(); // do this first, before writing to JSON
									document.getElementById("mySavedModel2").innerHTML = myDiagram.model
											.toJson();
									var str = document
											.getElementById("mySavedModel2").innerHTML;
									if (str === defaultJson) {
										document.getElementById("SavedButton").disabled = true;
										document.getElementById("ValidateButton").disabled = true;
									} else {
										document.getElementById("SavedButton").disabled = false;
										document.getElementById("ValidateButton").disabled = false;
									}
								} else if (idx >= 0)
									document.title = document.title.substr(0,
											idx);
							}
						});

		// Define a function for creating a "port" that is normally transparent.
		// The "name" is used as the GraphObject.portId, the "spot" is used to control how links connect
		// and where the port is positioned on the node, and the boolean "output" and "input" arguments
		// control whether the user can draw links from or to the port.
		function makePort(name, spot, output, input) {
			// the port is basically just a small transparent square
			return $(go.Shape, "Circle", {
				fill : null, // not seen, by default; set to a translucent gray by showSmallPorts, defined below
				stroke : null,
				desiredSize : new go.Size(7, 7),
				alignment : spot, // align the port on the main Shape
				alignmentFocus : spot, // just inside the Shape
				portId : name, // declare this object to be a "port"
				fromSpot : spot,
				toSpot : spot, // declare where links may connect at this port
				fromLinkable : output,
				toLinkable : input, // declare whether the user may draw links to/from here
				cursor : "pointer" // show a different cursor to indicate potential link point
			});
		}

		myDiagram.nodeTemplateMap.add("default",  // the default category
             
	             $(go.Node, "Spot",
	                  // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
	                  $(go.Panel, "Auto",
	                    $(go.Shape, "Rectangle",
	                      { fill: "white",
	                    	desiredSize : new go.Size(0, 70),
	                    	stroke: null },
	                      
	                      new go.Binding("figure", "figure"))

	             )));
		
		var nodeSelectionAdornmentTemplate = $(go.Adornment, "Auto", $(
				go.Shape, {
					fill : null,
					stroke : "deepskyblue",
					strokeWidth : 1.5,
					strokeDashArray : [ 4, 2 ]
				}), $(go.Placeholder));

		var nodeResizeAdornmentTemplate = $(go.Adornment, "Spot", {
			locationSpot : go.Spot.Right
		}, $(go.Placeholder), $(go.Shape, {
			alignment : go.Spot.TopLeft,
			cursor : "nw-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}), $(go.Shape, {
			alignment : go.Spot.Top,
			cursor : "n-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}), $(go.Shape, {
			alignment : go.Spot.TopRight,
			cursor : "ne-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}),

		$(go.Shape, {
			alignment : go.Spot.Left,
			cursor : "w-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}), $(go.Shape, {
			alignment : go.Spot.Right,
			cursor : "e-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}),

		$(go.Shape, {
			alignment : go.Spot.BottomLeft,
			cursor : "se-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}), $(go.Shape, {
			alignment : go.Spot.Bottom,
			cursor : "s-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}), $(go.Shape, {
			alignment : go.Spot.BottomRight,
			cursor : "sw-resize",
			desiredSize : new go.Size(6, 6),
			fill : "lightblue",
			stroke : "deepskyblue"
		}));

		var nodeRotateAdornmentTemplate = $(go.Adornment, {
			locationSpot : go.Spot.Center,
			locationObjectName : "CIRCLE"
		}, $(go.Shape, "Circle", {
			name : "CIRCLE",
			cursor : "pointer",
			desiredSize : new go.Size(7, 7),
			fill : "lightblue",
			stroke : "deepskyblue"
		}), $(go.Shape, {
			geometryString : "M3.5 7 L3.5 30",
			isGeometryPositioned : true,
			stroke : "deepskyblue",
			strokeWidth : 1.5,
			strokeDashArray : [ 4, 2 ]
		}));

		myDiagram.nodeTemplate = $(go.Node, "Spot", {
			locationSpot : go.Spot.Center
		}, new go.Binding("location", "loc", go.Point.parse)
				.makeTwoWay(go.Point.stringify), {
			selectable : true,
			selectionAdornmentTemplate : nodeSelectionAdornmentTemplate
		}, {
			resizable : true,
			resizeObjectName : "PANEL",
			resizeAdornmentTemplate : nodeResizeAdornmentTemplate
		}, {
			rotatable : true,
			rotateAdornmentTemplate : nodeRotateAdornmentTemplate
		}, new go.Binding("angle").makeTwoWay(),
		// the main object is a Panel that surrounds a TextBlock with a Shape
		$(go.Panel, "Auto", {
			name : "PANEL"
		}, new go.Binding("desiredSize", "size", go.Size.parse)
				.makeTwoWay(go.Size.stringify), $(go.Shape, "Rectangle", // default figure
		{
			portId : "", // the default port: if no spot on link data, use closest side
			fromLinkable : false,
			toLinkable : false,
			cursor : "pointer",
			fill : "white", // default color
			strokeWidth : 2
		}, new go.Binding("figure"), new go.Binding("fill")), $(go.TextBlock, {
			font : "bold 11pt Helvetica, Arial, sans-serif",
			margin : 8,
			maxSize : new go.Size(160, NaN),
			wrap : go.TextBlock.WrapFit,
			editable : true
		}, new go.Binding("text").makeTwoWay())),
		// four small named ports, one on each side:
		makePort("T", go.Spot.Top, true, true), makePort("L", go.Spot.Left,
				true, true), makePort("R", go.Spot.Right, true, true),
				makePort("B", go.Spot.Bottom, true, true), { // handle mouse enter/leave events to show/hide the ports
				//mouseEnter: function(e, node) { showSmallPorts(node, true); },
				//mouseLeave: function(e, node) { showSmallPorts(node, false); }
				});

		function showSmallPorts(node, show) {
			node.ports.each(function(port) {
				if (port.portId !== "") { // don't change the default port, which is the big shape
					port.fill = show ? "rgba(0,0,0,.3)" : null;
				}
			});
		}

		var linkSelectionAdornmentTemplate = $(go.Adornment, "Link", $(
				go.Shape,
				// isPanelMain declares that this Shape shares the Link.geometry
				{
					isPanelMain : true,
					fill : null,
					stroke : "deepskyblue",
					strokeWidth : 0
				}) // use selection object's strokeWidth
		);

		myDiagram.linkTemplate = $(go.Link, // the whole link panel
		{
			selectable : true,
			selectionAdornmentTemplate : linkSelectionAdornmentTemplate
		}, {
			relinkableFrom : true,
			relinkableTo : true,
			reshapable : true
		}, {
			routing : go.Link.AvoidsNodes,
			curve : go.Link.JumpOver,
			corner : 5,
			toShortLength : 4
		}, {
			contextMenu:$(go.Adornment)
		}, new go.Binding("points").makeTwoWay(), $(go.Shape, // the link path shape
		{
			isPanelMain : true,
			strokeWidth : 2
		}), $(go.Shape, // the "from" arrowhead
		new go.Binding("fromArrow", "fromArrow")), $(go.Shape, // the "to" arrowhead
		new go.Binding("toArrow", "toArrow")), $(go.TextBlock, // the label text
		{
			textAlign : "center",
			font : "bold 13pt helvetica, arial, sans-serif",
			stroke : "blue",
			margin : 8,
			segmentOffset : new go.Point(NaN, NaN),
			editable : true
		// enable in-place editing
		}, new go.Binding("text", "multi").makeTwoWay()), $(go.Panel, "Auto",
				new go.Binding("visible", "isSelected").ofObject(), $(go.Shape,
						"RoundedRectangle", // the link shape
						{
							fill : "#F8F8F8",
							stroke : null
						}), $(go.TextBlock, {
					minSize : new go.Size(NaN, NaN),
					editable : false
				}, new go.Binding("text").makeTwoWay())),
				$(go.TextBlock, // the "from"label
						{
							textAlign:"center",
							font:"bold 14px sans-serif",
							stroke: "#1967B3",
							segmentIndex: 0,
	                        segmentOffset: new go.Point(NaN, NaN),
	                       // segmentOrientation: go.Link.OrientUpright
	                      },
	                      new go.Binding("text", "text")),
	                    $(go.TextBlock,  // the "to" label
	                      {
	                        textAlign: "center",
	                        font: "bold 14px sans-serif",
	                        stroke: "#1967B3",
	                        segmentIndex: -1,
	                        segmentOffset: new go.Point(NaN, NaN),
	                       // segmentOrientation: go.Link.OrientUpright
	                      },
	                      new go.Binding("text", "toText"))
		);

	//start about contextMenu
		 // This is a dummy context menu for the whole Diagram:
      myDiagram.contextMenu = $(go.Adornment);

      // Override the ContextMenuTool.showContextMenu and hideContextMenu methods
      // in order to modify the HTML appropriately.
      var cxTool = myDiagram.toolManager.contextMenuTool;

      // This is the actual HTML context menu:
      var cxElement;
      var context = "contextMenu3";

      cxElement = document.getElementById(context);
      //cxElement = document.getElementById("contextMenu2");
      // We don't want the div acting as a context menu to have a (browser) context menu!
      cxElement.addEventListener("contextmenu", function(e) {
         this.focus();
         e.preventDefault();
         return false;
      }, false);

      cxElement.addEventListener("blur", function(e) {
         cxTool.stopTool();

         // maybe start another context menu
         if (cxTool.canStart()) {
            myDiagram.currentTool = cxTool;
            cxTool.doMouseUp();
         }

      }, false);
      cxElement.tabIndex = "1";

      // This is the override of ContextMenuTool.showContextMenu:
      // This does not not need to call the base method.
      cxTool.showContextMenu = function(contextmenu, obj) {
         var diagram = this.diagram;
         if (diagram === null)
            return;
         console.log(this.currentContextMenu);
         if (contextmenu !== this.currentContextMenu) {
            this.hideContextMenu();
         }
         var isE = 0;
         var isR = 0;
         var isA = 0;
         var context2 = "";
         myDiagram.selection.each(function(link) {
            if (link instanceof go.Link) { // ignore any selected Links and simple Parts
               // Examine and modify the data, not the Node directly.
               var data = link.data;
               var dt = data.to;
               var df = data.from;
               var type = data.type;
               console.log(type);
               if(type=="r"){    //Relationship to Relationship or Entity to Relationship
                  cxElement = document.getElementById("contextMenu");
                  }
               else if(type =="a"){      //Attribute menu
                  cxElement = document.getElementById("contextMenu2");
                  }
               else{    //nothing 
                  cxElement = document.getElementById("contextMenu3");
               }
               //console.log("type: " + data.type);
                 

            }
   
         });

         // cxElement = document.getElementById(context2);
         // We don't want the div acting as a context menu to have a (browser) context menu!
         cxElement.addEventListener("contextmenu", function(e) {
            this.focus();
            e.preventDefault();
            return false;
         }, false);

         // Hide any other existing context menu
         //if (contextmenu !== this.currentContextMenu) {
         //   this.hideContextMenu();
         //}

         // Show only the relevant buttons given the current state.
         var cmd = diagram.commandHandler;
         var objExists = obj !== null;

         document.getElementById("linkType").style.display = objExists ? "block": "none";

         // Now show the whole context menu element
         cxElement.style.display = "block";
         // we don't bother overriding positionContextMenu, we just do it here:
         var mousePt = diagram.lastInput.viewPoint;
         cxElement.style.left = mousePt.x + 250 + "px";
         cxElement.style.top = mousePt.y + "px";

         // Remember that there is now a context menu showing
         this.currentContextMenu = contextmenu;
      }

      // This is the corresponding override of ContextMenuTool.hideContextMenu:
      // This does not not need to call the base method.
      cxTool.hideContextMenu = function() {
         if (this.currentContextMenu === null)
            return;
         cxElement.style.display = "none";
         this.currentContextMenu = null;
         cxElement = document.getElementById("contextMenu3");
      }
	//end about contextMenu
		

		// initialize the Palette that is on the left side of the page
		myPalette = $(go.Palette, "myPaletteDiv", // must name or refer to the DIV HTML element
		{
			maxSelectionCount : 1,
			nodeTemplateMap : myDiagram.nodeTemplateMap, // share the templates used by myDiagram
			linkTemplate : // simplify the link template, just in this Palette
			$(go.Link, { // because the GridLayout.alignment is Location and the nodes have locationSpot == Spot.Center,
				// to line up the Link in the same manner we have to pretend the Link has the same location spot
				locationSpot : go.Spot.Center,
				selectionAdornmentTemplate : $(go.Adornment, "Link", {
					locationSpot : go.Spot.Center
				}, $(go.Shape, {
					isPanelMain : true,
					fill : null,
					stroke : "deepskyblue",
					strokeWidth : 0
				}), $(go.Shape, // the arrowhead
				{
					toArrow : "",
					stroke : null
				}))
			}, {
				routing : go.Link.AvoidsNodes,
				curve : go.Link.JumpOver,
				corner : 5,
				toShortLength : 4
			}, new go.Binding("points"), $(go.Shape, // the link path shape
			{
				isPanelMain : true,
				strokeWidth : 2
			}), $(go.Shape, // the "from" arrowhead
			new go.Binding("fromArrow", "fromArrow")), $(go.Shape, // the "to" arrowhead
			new go.Binding("toArrow", "toArrow"))),
			model : new go.GraphLinksModel([ // specify the contents of the Palette
				{
		            category: "default",
		            text : "",
		            figure : "Rectangle",
		            fill : "white",
		            size : "250 70",
		            isTemp : "false",
		            
		         },{
				text : "Entity",
				figure : "RoundedRectangle",
				fill : "lightyellow",
				size : "150 70",
				type : "E",
				isTemp : "false"
			}, {
				text : "Relationship",
				figure : "Diamond",
				fill : "lightskyblue",
				type : "R",
				isTemp : "false"
			}, {
				text : "Attribute",
				figure : "Ellipse",
				fill : "#00AD5F",
				type : "A",
				isTemp : "false"
			} ], [
					// the Palette also has a disconnected Link, which the user can drag-and-drop
					{
						points : new go.List(go.Point).addAll([
								new go.Point(0, 0), new go.Point(30, 0),
								new go.Point(30, 40), new go.Point(60, 40) ]),
						toArrow : "",
						fromArrow : "",
						
						attriType: "",   //default ""
		                type : "n",   // default "none"
		                multi : "",   //default ""
		                nToN : "0"    //default "0 (1 to 1)
					} ])
		});
	
		load(); // load an initial diagram from some JSON text
	}

	function TopRotatingTool() {
		go.RotatingTool.call(this);
	}
	go.Diagram.inherit(TopRotatingTool, go.RotatingTool);

	/** @override */
	TopRotatingTool.prototype.updateAdornments = function(part) {
		go.RotatingTool.prototype.updateAdornments.call(this, part);
		var adornment = part.findAdornment("Rotating");
		if (adornment !== null) {
			adornment.location = part.rotateObject
					.getDocumentPoint(new go.Spot(0.5, 0, 0, -30)); // above middle top
		}
	};
	RoundedRectangleate = function(newangle) {
		go.RotatingTool.prototype.rotate.call(this, newangle + 90);
	};
	// end of TopRotatingTool class
	
	// update the value and appearance of each node according to its type and input values
   function updateStates() {

      var oldskip = myDiagram.skipsUndoManager;
      myDiagram.skipsUndoManager = true;
      console.log("function for links");
      // do all "input" nodes first
      var isE = 0;
      var isR = 0;
      var isA = 0;
      var bugLink = null;
      myDiagram.links.each(function(link) {


               var dt = link.data.to;
               var df = link.data.from;
               var tmp = myDiagram.model.findNodeDataForKey(dt);
               var tmp2 = myDiagram.model.findNodeDataForKey(df);

               if (link.data.type != "n")
               {
                  if (dt == null || df == null) {

                     //console.log("kkk");
                     myDiagram.model.setDataProperty(link.data, "toArrow", "");
                     myDiagram.model.setDataProperty(link.data, "fromArrow", "");
                     myDiagram.model.setDataProperty(link.data, "to", null);
                     myDiagram.model.setDataProperty(link.data, "from", null);
                     
                     myDiagram.model.setDataProperty(link.data, "type", "n");
                     myDiagram.model.setDataProperty(link.data, "attriType", null);
                     myDiagram.model.setDataProperty(link.data, "nToN", "0");
                     myDiagram.model.setDataProperty(link.data, "multi", null);
                      
                     //myDiagram.model.setDataProperty(link.data, "nToN", "0");
                     myDiagram.model.setDataProperty(link.data, "text", null);
                     myDiagram.model.setDataProperty(link.data, "toText", null);
                     console.log("Type: " + link.data.type + " ,AttriType: " + link.data.attriType);  
                  }
               } 
               else //ë¸ë©ì¸ë°, ì°ê²°ì´ ëì´ìì¼ë©´ 
               {
                  if (dt != null && df != null) {
                     myDiagram.nodes.each(function(node) {

                        if (dt == node.data.key) {
                           if (node.data.type == "A") {   
                        	   myDiagram.model.setDataProperty(link.data, "toArrow", "Standard");
                               myDiagram.model.setDataProperty(link.data, "fromArrow", "Backward");
                               
                               myDiagram.model.setDataProperty(link.data, "type", "a");   //attribute랑 연결된 경우
                               myDiagram.model.setDataProperty(link.data, "attriType", "k");
                               myDiagram.model.setDataProperty(link.data, "nToN", "0");
                               myDiagram.model.setDataProperty(link.data, "multi", null);
                              
                           
                           }
                           else if(node.data.type=="R")
                                      isR++;
                                   else
                                      isE++;

                        }
                        if (df == node.data.key) {
                           if (node.data.type == "A") {  
                        	   myDiagram.model.setDataProperty(link.data, "toArrow", "Standard");
                               myDiagram.model.setDataProperty(link.data, "fromArrow", "Backward");
                               
                               myDiagram.model.setDataProperty(link.data, "type", "a");   //attribute랑 연결된 경우
                               myDiagram.model.setDataProperty(link.data, "attriType", "k");
                               myDiagram.model.setDataProperty(link.data, "nToN", "0");
                               myDiagram.model.setDataProperty(link.data, "multi", null);
                              
                           
                           }
                           else if(node.data.type=="R")
                                      isR++;
                                   else
                                      isE++;

                        }
                     });
                     if(tmp.type == "A" && tmp2.type == "A"){
                         myDiagram.model.setDataProperty(link.data, "toArrow", "");
                         myDiagram.model.setDataProperty(link.data, "fromArrow", "");
                         myDiagram.model.setDataProperty(link.data, "to", null);
                         myDiagram.model.setDataProperty(link.data, "from", null);
                         
                         myDiagram.model.setDataProperty(link.data, "type", "n");
                         myDiagram.model.setDataProperty(link.data, "attriType", null);
                         myDiagram.model.setDataProperty(link.data, "nToN", "0");
                         myDiagram.model.setDataProperty(link.data, "multi", null);
                       
                         bugLink = link;            
                   
                      }
                      if(tmp.type == "E" && tmp2.type == "E"){
                         myDiagram.model.setDataProperty(link.data, "toArrow", "");
                         myDiagram.model.setDataProperty(link.data, "fromArrow", "");
                         myDiagram.model.setDataProperty(link.data, "to", null);
                         myDiagram.model.setDataProperty(link.data, "from", null);
                         
                         myDiagram.model.setDataProperty(link.data, "type", "n");
                         myDiagram.model.setDataProperty(link.data, "attriType", null);
                         myDiagram.model.setDataProperty(link.data, "nToN", "0");
                         myDiagram.model.setDataProperty(link.data, "multi", null);
                         console.log(myDiagram.model.toJson());
                      
                         bugLink = link; 
                         
                      }
                  }
                  
                   if((isE == 1 && isR == 1) || isR ==2)     //Entity to Relationship
                     {   
                	   myDiagram.model.setDataProperty(link.data, "toArrow", "");
                       myDiagram.model.setDataProperty(link.data, "fromArrow", "");
                        myDiagram.model.setDataProperty(link.data, "type", "r");   //attributeë ì°ê²°ë ê²½ì°
                        myDiagram.model.setDataProperty(link.data, "attriType", null);
                        myDiagram.model.setDataProperty(link.data, "nToN", null);
                        myDiagram.model.setDataProperty(link.data, "multi", "1");   
                     }
               }
               isE = 0;
               isR = 0;
               isA = 0;
               
               
            });

      if(bugLink!= null)
          myDiagram.commandHandler.deleteSelection(bugLink);
      
      myDiagram.skipsUndoManager = oldskip;
      //myDiagram.commitTransaction("change");

   }

   // This is the general menu command handler, parameterized by the name of the command.
   function cxcommand(event, val) {
      if (val === undefined)
         val = event.currentTarget.id;
      var diagram = myDiagram;

      switch (val) {
      case "NtoN":
         var linkType = document.elementFromPoint(event.clientX, event.clientY).name;
         changeNtoN(diagram, linkType);
         break;
      case "linkType": {
         //var istemp = window.getComputedStyle(document.elementFromPoint(event.clientX, event.clientY).parentElement)["background-color"];
         var linkType = document.elementFromPoint(event.clientX,event.clientY).name;
         //var istemp = document.getElementById("test_id").getAttribute('name');

         changeType(diagram, linkType);
         break;
      }
      }
      diagram.currentTool.stopTool();
   }
   function changeNtoN(diagram, nToN) {

      diagram.startTransaction("change");
      diagram.selection.each(function(link) {
         if (link instanceof go.Link) { // ignore any selected Links and simple Parts

            var data = link.data;

            switch (nToN) {
            case "one":
               diagram.model.setDataProperty(data, "multi", "1");
               break;
            case "many":
               diagram.model.setDataProperty(data, "multi", "M");
               break;
            }
            console.log("multi: " + data.multi);
         }
      });
      diagram.commitTransaction("change");
   }
   function changeType(diagram, attriType) {
      // Always make changes in a transaction, except when initializing the diagram.
      diagram.startTransaction("change");
      diagram.selection.each(function(link) {
         if (link instanceof go.Link) { // ignore any selected Links and simple Parts
            // Examine and modify the data, not the Node directly.
            var data = link.data;

            //saveDiagramProperties();  
            //var jsontext = myDiagram.model.toJson();
            //window.alert(jsontext);

            // Call setDataProperty to support undo/redo as well as
            // automatically evaluating any relevant bindings.
            var tmp = diagram.model.findNodeDataForKey(data.to);
            var tmp2 = diagram.model.findNodeDataForKey(data.from);
            switch (attriType) {
            case "1to1":
                //diagram.model.setDataProperty(link.data, "nToN", "0");         
                //diagram.model.setDataProperty(link.data, "text", "1");
                //diagram.model.setDataProperty(link.data, "toText", "1");
                diagram.model.setDataProperty(data, "toArrow", "Standard");
                diagram.model.setDataProperty(data, "fromArrow", "Backward");               
                diagram.model.setDataProperty(data, "attriType", "k");
                diagram.model.setDataProperty(data, "nToN", "0");
                
                break;
                break;
             case "1toM":
                diagram.model.setDataProperty(link.data, "nToN", "1");
                if(tmp.type == "A"){
                   diagram.model.setDataProperty(data, "toArrow", "Standard");
                   diagram.model.setDataProperty(data, "fromArrow", "");   
                   //diagram.model.setDataProperty(link.data, "text", "M");
                   //diagram.model.setDataProperty(link.data, "toText", "1");
                }
                if(tmp2.type == "A"){
                   diagram.model.setDataProperty(data, "fromArrow", "Backward");
                   diagram.model.setDataProperty(data, "toArrow", "");   
                   //diagram.model.setDataProperty(link.data, "text", "1");
                   //diagram.model.setDataProperty(link.data, "toText", "M");
                }         
                diagram.model.setDataProperty(data, "attriType", "n");
                break;
             case "Mto1":
                diagram.model.setDataProperty(link.data, "nToN", "2");
                if(tmp.type == "A"){
                   diagram.model.setDataProperty(data, "fromArrow", "Backward");
                   diagram.model.setDataProperty(data, "toArrow", "DoubleFeathers");
                   //diagram.model.setDataProperty(link.data, "text", "1");
                   //diagram.model.setDataProperty(link.data, "toText", "M");
                }
                if(tmp2.type == "A"){
                   diagram.model.setDataProperty(data, "toArrow", "Standard");
                   diagram.model.setDataProperty(data, "fromArrow", "BackwardDoubleFeathers");
                   //diagram.model.setDataProperty(link.data, "text", "M");
                   //diagram.model.setDataProperty(link.data, "toText", "1");
                }
                diagram.model.setDataProperty(data, "attriType", "n");
                break;
             case "MtoM":
                 
                      if(tmp.type == "A"){
                         diagram.model.setDataProperty(data, "toArrow", "DoubleFeathers");
                         diagram.model.setDataProperty(data, "fromArrow", "");
                      }
                      if(tmp2.type == "A"){
                         diagram.model.setDataProperty(data, "toArrow", "");
                         diagram.model.setDataProperty(data, "fromArrow", "BackwardDoubleFeathers");
                      }         
                diagram.model.setDataProperty(link.data, "nToN", "3");
                //diagram.model.setDataProperty(link.data, "text", "M");
                //diagram.model.setDataProperty(link.data, "toText", "M");
                diagram.model.setDataProperty(data, "attriType", "m");
                break;
         

            }

         }
      });
      diagram.commitTransaction("change");
      //window.alert(jsontext);
   }

	// Show the diagram's model in JSON format that the user may edit
	function save() {
		saveDiagramProperties(); // do this first, before writing to JSON
		document.getElementById("mySavedModel2").value = myDiagram.model
				.toJson();
		myDiagram.isModified = false;
		var json = document.getElementById("mySavedModel2").value;
		//var data = "text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(json));
		var data = "text/json;charset=utf-8," + json;
		
		var today = new Date();
		var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		
		document.getElementById("down").href = 'data:' + data;
		document.getElementById("down").download = date + '.json';
		document.getElementById("down").click();
	}
	function load() {
		myDiagram.model = go.Model.fromJson(document
				.getElementById("mySavedModel").value);
		loadDiagramProperties(); // do this after the Model.modelData has been brought into memory
	}

	function saveDiagramProperties() {
		myDiagram.model.modelData.position = go.Point
				.stringify(myDiagram.position);
	}
	function loadDiagramProperties(e) {
		// set Diagram.initialPosition, not Diagram.position, to handle initialization side-effects
		var pos = myDiagram.model.modelData.position;
		if (pos)
			myDiagram.initialPosition = go.Point.parse(pos);
	}
	function openWin() {
		window
				.open(
						"http://localhost:8080/DBProject/load.html",
						"_blank",
						"toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=530");
	}
	function openChild() {
		window.name = "parentForm";
		openWin = window
				.open(
						"http://localhost:8080/DBProject/load.html",
						"childForm",
						"toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=530");
	}
	function validClick() {
		saveDiagramProperties();
		document.getElementById("mySavedModel2").value = myDiagram.model.toJson();
		document.getElementById("ERJson").value = document.getElementById("mySavedModel2").value;
		document.getElementById("submit").click();
	}
	function annotateClick() {
		saveDiagramProperties();
		document.getElementById("mySavedModel2").value = myDiagram.model.toJson();
		document.getElementById("ERJson2").value = document.getElementById("mySavedModel2").value;
		document.getElementById("submit_anno").click();
	}
</script>
</html>