<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>NUS_Web</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
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
<style type="text/css">
/* CSS for the traditional context menu */
#contextMenu {
	z-index: 300;
	position: absolute;
	left: 5px;
	border: 1px solid #444;
	background-color: #F5F5F5;
	display: none;
	box-shadow: 0 0 10px rgba(0, 0, 0, .4);
	font-size: 12px;
	font-family: sans-serif;
	font-weight: bold;
}

#contextMenu ul {
	list-style: none;
	top: 0;
	left: 0;
	margin: 0;
	padding: 0;
}

#contextMenu li a {
	position: relative;
	min-width: 60px;
	color: #444;
	display: inline-block;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#contextMenu li:hover {
	background: #CEDFF2;
	color: #EEE;
}

#contextMenu li ul li {
	display: none;
}

#contextMenu li ul li a {
	position: relative;
	min-width: 60px;
	padding: 6px;
	text-decoration: none;
	cursor: pointer;
}

#contextMenu li:hover ul li {
	display: block;
	margin-left: 0px;
	margin-top: 0px;
}
</style>
</head>

<body onload="init()">

	<div id="sample">

		<div style="width: 100%; white-space: nowrap;">

			<span
				style="display: inline-block; vertical-align: top; padding: 5px; width: 82.5%">
				<div id="myDiagramDiv"
					style="border: solid 1px black; height: 620px"></div>
				<div id="contextMenu">
					<ul>
						<li id="isTemp" class="hasSubMenu"><a href="#" target="_self">Type</a>
							<ul class="subMenu" id="colorSubMenu">
								<li style="background: crimson;"
									onclick="cxcommand(event, 'isTemp')" name="true"><a
									href="#" target="_self" name="true">Temporal</a></li>
								<li style="background: chartreuse;"
									onclick="cxcommand(event, 'isTemp')" name="fasle"><a
									href="#" target="_self" name="false">None</a></li>

							</ul></li>
					</ul>
				</div>
			</span> <span
				style="display: inline-block; vertical-align: top; padding: 7px; width: 250px">
				<div style="height: 620px; vertical-align: top">
					<table>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Create" id="CreateButton" value="Create"
								onclick="location.href='main.jsp'" disabled="disabled"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning" name="Load"
								id="LoadButton" value="Load" onclick="openChild()"
								disabled="disabled"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<a id="down" href="" download="">
								<td><Input type="submit" class="btn btn-warning"
									name="Save" id="SavedButton" value="Save" onclick="save()"
									style="font-size: 50px; width: 230px; height: 90px"></td>
							</a>
						</tr>
						<tr>
							<form ACTION="http://localhost:8080/DBProject/ERCreater" method="POST">
								<input type="hidden" id="ERJson" name="ERJson" value="" /> <input
									type="submit" id="submit" style="display: none;" />
							</form>
							<td><Input type="submit" class="btn btn-warning"
								name="Validate" id="ValidateButton" value="Validate"
								disabled="disabled" onclick="validClick()"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Annotate" id="AnnotateButton" value="Back"
								onclick="window.history.back();"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<form ACTION="http://localhost:8080/DBProject/Translate" method="POST">
								<input type="hidden" id="ERJson3" name="ERJson3" value="" /> 
								<input type="submit" id="submit_tran" style="display: none;" />
							</form>
							<td><Input type="submit" class="btn btn-warning"
								name="Translate" id="TranslateButton" value="Translate"
								onclick="translateClick()"
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
			style="display: inline-block; vertical-align: top; padding: 5px; width: 80%">
		</Span>

		<div id="invisible">
			<div>
				<button id="SaveButton" onclick="save()">Save</button>
				Diagram Model saved in JSON format:
			</div>

			<textarea id="mySavedModel" style="width: 100%; height: 300px">
  			<% 
  				request.setCharacterEncoding("euc-kr");
  				String a = request.getParameter("diagram");
  				System.out.println(a);
  			%>
  			<%= a %>
    </textarea>
    		<textarea id="mySavedModel2" style="width: 100%; height: 300px"
				name="ERJson">{ "class": "go.GraphLinksModel","linkFromPortIdProperty": "fromPort","linkToPortIdProperty": "toPort","nodeDataArray": [],"linkDataArray": []}</textarea>
		</div>
	</div>
	</Span>
</body>
<script>
	$(document).ready(function() {
		// jQuery methods go here ...
		$("#invisible").hide();
		$("#mySavedModel2").hide();
	});

	function init() {

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
			allowDrop : false, // must be true to accept drops from the Palette
			"draggingTool.dragsLink" : false,
			"draggingTool.isGridSnapEnabled" : false,
			"linkingTool.isUnconnectedLinkValid" : false,
			"linkingTool.portGravity" : 20,
			"relinkingTool.isUnconnectedLinkValid" : false,
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
			"undoManager.isEnabled" : false

		});

		myDiagram.allowDelete = false;

		// when the document is modified, add a "*" to the title and enable the "Save" button
		myDiagram.addDiagramListener("Modified", function(e) {
			var button = document.getElementById("SaveButton");
			if (button)
				button.disabled = !myDiagram.isModified;
			var idx = document.title.indexOf("*");
			if (myDiagram.isModified) {
				if (idx < 0)
					document.title += "*";
			} else {
				if (idx >= 0)
					document.title = document.title.substr(0, idx);
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
		}, {
			contextMenu : $(go.Adornment)
		}, new go.Binding("location", "loc", go.Point.parse)
				.makeTwoWay(go.Point.stringify), {
			selectable : true,
			selectionAdornmentTemplate : nodeSelectionAdornmentTemplate
		}, {
			resizable : false,
			resizeObjectName : "PANEL",
			resizeAdornmentTemplate : nodeResizeAdornmentTemplate
		}, {
			rotatable : false,
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
			editable : false
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
				})
		// use selection object's strokeWidth
		);

		myDiagram.linkTemplate = $(go.Link, // the whole link panel
		{
			selectable : true,
			selectionAdornmentTemplate : linkSelectionAdornmentTemplate
		}, {
			relinkableFrom : false,
			relinkableTo : false,
			reshapable : false
		}, {
			routing : go.Link.AvoidsNodes,
			curve : go.Link.JumpOver,
			corner : 5,
			toShortLength : 4
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
			editable : false
		// enable in-place editing
		}, new go.Binding("text", "multi")), $(go.Panel, "Auto",
				new go.Binding("visible", "isSelected").ofObject(), $(go.Shape,
						"RoundedRectangle", // the link shape
						{
							fill : "#F8F8F8",
							stroke : null
						}), $(go.TextBlock, {
					minSize : new go.Size(NaN, NaN),
					editable : false
				}, new go.Binding("text").makeTwoWay())));

		////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////Top////////////////////////////////////
		// This is a dummy context menu for the whole Diagram:
		myDiagram.contextMenu = $(go.Adornment);

		// Override the ContextMenuTool.showContextMenu and hideContextMenu methods
		// in order to modify the HTML appropriately.
		var cxTool = myDiagram.toolManager.contextMenuTool;

		// This is the actual HTML context menu:
		var cxElement = document.getElementById("contextMenu");

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

			// Hide any other existing context menu
			if (contextmenu !== this.currentContextMenu) {
				this.hideContextMenu();
			}

			// Show only the relevant buttons given the current state.
			var cmd = diagram.commandHandler;
			var objExists = obj !== null;
			document.getElementById("isTemp").style.display = objExists ? "block"
					: "none";

			// Now show the whole context menu element
			cxElement.style.display = "block";
			// we don't bother overriding positionContextMenu, we just do it here:
			var mousePt = diagram.lastInput.viewPoint;
			cxElement.style.left = mousePt.x + "px";
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
		}
		//////////////////////////////////////Down///////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////

		load(); // load an initial diagram from some JSON text

		// initialize the Palette that is on the left side of the page
		

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

	/////////////////////////////////////////////////
	////////////////////////////////////////////////
	// This is the general menu command handler, parameterized by the name of the command.
	function cxcommand(event, val) {
		if (val === undefined)
			val = event.currentTarget.id;
		var diagram = myDiagram;
		switch (val) {
		case "isTemp": {
			//var istemp = window.getComputedStyle(document.elementFromPoint(event.clientX, event.clientY).parentElement)["background-color"];
			var istemp = document
					.elementFromPoint(event.clientX, event.clientY).name;
			//var istemp = document.getElementById("test_id").getAttribute('name');

			changeType(diagram, istemp);
			break;
		}
		}
		diagram.currentTool.stopTool();
	}

	function changeType(diagram, istemp) {
		// Always make changes in a transaction, except when initializing the diagram.
		diagram.startTransaction("change");
		diagram.selection.each(function(node) {
			if (node instanceof go.Node) { // ignore any selected Links and simple Parts
				// Examine and modify the data, not the Node directly.
				var data = node.data;
				var sub = data.text;
				var sub2 = sub.substring(0, sub.length - 2);

				// Call setDataProperty to support undo/redo as well as
				// automatically evaluating any relevant bindings.
				if (istemp == "true") {
					if (data.isTemp == "false")
						diagram.model.setDataProperty(data, "text", data.text
								+ " \u1D40");
				} else {
					if (data.isTemp == "true")
						diagram.model.setDataProperty(data, "text", sub2);

				}
				diagram.model.setDataProperty(data, "isTemp", istemp);
			}
		});
		diagram.commitTransaction("change");
	}

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
	function saveDiagramProperties() {
		myDiagram.model.modelData.position = go.Point
				.stringify(myDiagram.position);
	}
	//////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	function load() {
		myDiagram.model = go.Model.fromJson(document
				.getElementById("mySavedModel").value);
		loadDiagramProperties(); // do this after the Model.modelData has been brought into memory
	}
	function loadDiagramProperties(e) {
		// set Diagram.initialPosition, not Diagram.position, to handle initialization side-effects
		var pos = myDiagram.model.modelData.position;
		if (pos)
			myDiagram.initialPosition = go.Point.parse(pos);
	}
	function translateClick() {
		saveDiagramProperties();
		document.getElementById("mySavedModel2").value = myDiagram.model.toJson();
		document.getElementById("ERJson3").value = document.getElementById("mySavedModel2").value;
		document.getElementById("submit_tran").click();
	}
</script>
</html>