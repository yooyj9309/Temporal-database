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
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

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

<body>

	<div id="sample">

		<div style="width: 100%; white-space: nowrap;">

			<span
				style="display: inline-block; vertical-align: top; padding: 5px; width: 82.5%">
				<div id="myDiagramDiv"
					style="border: solid 1px black; height: 720px; overflow-y: scroll;">
					${table}</div>
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
							<form ACTION="http://localhost:8080/DBProject/ERCreater"
								method="POST">
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
								name="Annotate" id="AnnotateButton" value="Annotate"
								disabled="disabled"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Translate" id="TranslateButton" value="Back"
								onclick="window.history.back();"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
						<tr>
							<td><Input type="submit" class="btn btn-warning"
								name="Query" id="QueryButton" value="Query"
								onClick="queryClick()"
								style="font-size: 50px; width: 230px; height: 90px"></td>
						</tr>
					</table>
				</div>
				<div id="myQuery" style="width: 100%; height: 50px; display: none;">${query}</div>
			</span>
		</div>
	</div>
	<Span
		style="display: inline-block; vertical-align: top; padding: 5px; width: 80%">
	</Span>
</body>
<script>
function save() {
	var sql = document.getElementById("myQuery").innerText;
	var data = "text/plane;charset=utf-8," + sql;
	
	var today = new Date();
	var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
	
	document.getElementById("down").href = 'data:' + data;
	document.getElementById("down").download = date + '.sql';
	document.getElementById("down").click();
}
function queryClick() {
	document.getElementById("myDiagramDiv").innerHTML = document.getElementById("myQuery").innerHTML;
	document.getElementById("QueryButton").value = "Back";
	document.getElementById("TranslateButton").value = "Translate";
	document.getElementById("TranslateButton").disabled = "true";
	document.getElementById("QueryButton").onclick = function(){window.history.back();};
}
</script>
</html>