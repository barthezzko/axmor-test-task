function getAJAX(urls, id, out_mode) { 
if (out_mode==undefined){
	out_mode=false;
}
  dojo.xhrGet( { 
	url: urls, 
	preventCache:true,
	sync:false,
	handleAs: "text",
	timeout: 5000,
	load: function(response, ioArgs) {
	   if (out_mode == true){
			dojo.byId(id).innerHTML += "<br />" + response;
			reloadTree();
		}
		else{
			dojo.byId(id).innerHTML = response;
			
		}
	  return response;
	},

	error: function(response, ioArgs) {
	  console.error("HTTP status code: ", ioArgs.xhr.status);
	  return response;
	  }
	});
  }

function getAJAXold(URL, id, out_mode)
{
/*
Here is the most common and "pure" AJAX using way
The AJAX objects are created and accessed directly
*/

if (out_mode==undefined){
	out_mode=false;
}
var xml=GetXmlHttpObject();
if (xml==null)
  {
  alert ("Your browser does not support AJAX!");
  return;
  }
var url=URL;
//url=url+"&sid="+Math.random();
xml.onreadystatechange=function() {
    if (xml.readyState == 4) {
        if (out_mode == true){
			document.getElementById(id).innerHTML += "<br />" + xml.responseText;
			reloadTree();
		}
		else{
			document.getElementById(id).innerHTML = xml.responseText;
		}
    }
};
xml.open("POST",url,true);
xml.send(null);
}

//creating object
function GetXmlHttpObject()
{
if (window.XMLHttpRequest)
  {
  // code for IE7+, Firefox, Chrome, Opera, Safari
  return new XMLHttpRequest();
  }
if (window.ActiveXObject)
  {
  // code for IE6, IE5
  return new ActiveXObject("Microsoft.XMLHTTP");
  }
return null;
}


function formSubmit(formid, msgboxid){
  dojo.xhrPost({
  form: formid,
  handleAs: "text",
  handle: function(data,args){
   if(typeof data == "error"){
    console.warn("error!",args);
   }else{
    console.log(data);
   }
   dojo.byId(msgboxid).innerHTML = data;
   reloadTree();
  }
 });
};

function reloadTree(){
	try{
		var expStore = new dojo.data.ItemFileReadStore({url:"exp.json", id:"expStore", jsId:"expStore"}, document.createElement("div"));
		if (dijit.byId('grid1')) {dijit.byId('grid1').destroy();
		}
		grid1 = new dojox.grid.DataGrid({
				query: { id: '*' },
				store: expStore,
				structure: layout,
				id: 'grid1',
				rowsPerPage: 20
			}, 'grid1');
		dojo.byId("tab2").appendChild(grid1.domNode);
		grid1.startup();
		var nonexpStore = new dojo.data.ItemFileReadStore({url:"nonexp.json", id:"expStore", jsId:"expStore"}, document.createElement("div"));
		if (dijit.byId('grid2')) {dijit.byId('grid2').destroy();
		}
		grid2 = new dojox.grid.DataGrid({
				query: { id: '*' },
				store: nonexpStore,
				structure: layout,
				id: 'grid2',
				rowsPerPage: 20
			}, 'grid2');
		dojo.byId("tab3").appendChild(grid2.domNode);
		grid2.startup();
		
		var treeStore = new dojo.data.ItemFileReadStore({url:"tree.json", id:"treeStore", jsId:"treeStore"}, document.createElement("div"));
		if (dijit.byId("tree1")) {dijit.byId("tree1").destroy()}
		var tree1 = new dijit.Tree({store:treeStore,childrenAttr:["children"],id:"tree1"}, document.createElement("div"));
		//Deleted the existing tree, we create a new one with needed store and other attributes
		//Appending the tree
		dojo.byId("tab1").appendChild(tree1.domNode);
		
		//Connect the method to the onClick event of the tree control
		dojo.connect(dijit.byId("tree1"), 'onClick', function(item) {
			if (treeStore.getValue(item, "type") == undefined){
			selectItem(treeStore.getValue(item, "id"), treeStore.getValue(item, "header"),treeStore.getValue(item, "description"),treeStore.getValue(item, "dateExpire"),treeStore.getValue(item, "created"),treeStore.getValue(item, "folder"));
				
			}
			else{initMenu(false);
				clearFields();
				
			}
		});
		//Clearing divs
		document.getElementById('selected_info').innerHTML='';
		document.getElementById('selected_header').innerHTML='';
		initMenu(false);
	}
	catch(err){
		alert(err.Description);
	}
}

function onError(error, request){
	alert(error);
}

function getFolderName(folderId){
/*
Getting folder name by its ID
*/
	out="";
	switch(folderId){
	case 0:
		out = "Main Tasks";
		break;
	case 1:
		out = "Home Tasks";
		break;
	case 2:
		out = "Sport Tasks";
		break;
	default:
		out = "No such Folder";
		break;
	}
	return out;
}
function getFolderId(folderName){
/*
Getting folder ID by the name selected in combobox
*/
		switch(folderName){
		case "Main Tasks":
			out = 0
			break;
		case "Home Tasks":
			out = 1;
			break;
		case "Sport Tasks":
			out = 2;
			break;
		default:
			out = -1;
			break;
		}
		return out;
}
function selectItem(id, header, descr, exp, cre, folder){
try{
	output = '<b>Header:</b> '+ header + '<br />';
	output += '<b>Description:</b> ' + descr + '<br />';
	output += '<b>Expiration date:</b> ' + toDate(exp) + '<br />';
	output += '<b>Date created:</b> ' + toDate(cre) + '<br />';
	document.getElementById('selected_info').innerHTML = output + '<b>Folder:</b> '+ getFolderName(folder) + '<br />';
	output= "<a href='javascript:deleteItem(" + id + ");'>Delete</a> | <a href='javascript:modifyItem();'>Modify</a>";
	document.getElementById('selected_header').innerHTML = output;

	//filling the form controls
	dojo.byId("header_").value=header;
	dojo.byId("descr").value=descr;
	dojo.byId("expire").value=exp;
	dojo.byId("folder").value=folder;
	dojo.byId("id").value=id;
	dijit.byId("send_butt").attr("label", "Modify"); 
	dijit.byId("dialog_div").attr("title", "Modify"); 
	dijit.byId("calen").attr("value", toDate(exp))
	dijit.byId("folderlist").attr("value", getFolderName(folder));
	//
	document.myform.action="modify.ses";
	initMenu(true, id);
	}
catch(err){
	alert(err.Description);
	}			
}
function deleteItem(id){
	document.getElementById('selected_header').innerHTML='';
	document.getElementById('selected_info').innerHTML='';
	initMenu(false);
	getAJAX('delete.ses?id='+id,'console', true);
}
function modifyItem(){
	dijit.byId('dialog_div').show();
}
function clearFields(){
	dojo.byId("header_").value="";
	dojo.byId("descr").value="";
	dojo.byId("expire").value="";
	dojo.byId("folder").value="0";
	dijit.byId("folderlist").attr("value", getFolderName(0));
}
function initInsert(){
	clearFields();
	dijit.byId("send_butt").attr("label", "Add task"); 
	dijit.byId("dialog_div").attr("title", "Adding a task"); 
	document.myform.action="insert.ses";
	dijit.byId('dialog_div').show();
	document.getElementById('selected_info').innerHTML='';
	document.getElementById('selected_header').innerHTML='';
	initMenu(false);
}
function initModify(){
	document.myform.action="modify.ses";
	dijit.byId('dialog_div').show();
}
function initMenu(full, id){
	document.getElementById('menu_').innerHTML="<a href='javascript:reloadTree();'><img src='img/update.jpg' alt='Update'></a>";
	document.getElementById('menu_').innerHTML+="<a href=\"javascript:initInsert();\"><img src='img/add.jpg' alt='Add Item'></a>";
	if (full==true){
		document.getElementById('menu_').innerHTML+="<a href=\"javascript:initModify();\"><img src='img/config.jpg' /></a>";
		document.getElementById('menu_').innerHTML+="<a href=\"javascript:if(confirm('Delete selected task?')){deleteItem("+id+")}\"><img src='img/delete.jpg' /></a>";
	}
	document.getElementById('menu_').innerHTML+="<a href=\"javascript:getAJAX('info.ses','ses_info');\"><img src='img/info.jpg' /></a>";
	document.getElementById('menu_').innerHTML+="<a href=\"javascript:if(confirm('Close session?')){getAJAX('close.ses','console',true)};\"><img src='img/stop.jpg' /></a>";
}

function toMillis(Date1){
	/*
	Interesting fact: in DoJo the month numbers start with 1 and end with 12
	But in JS from 0 to 11! That's why -1 in month value below:
	*/
	try{
	var dat = new Date(dojo.date.locale.format(Date1, {selector:'date', zulu:false, datePattern:'yyyy'}),dojo.date.locale.format(Date1, {selector:'date',zulu:false, datePattern:'MM'})-1, dojo.date.locale.format(Date1, {selector:'date',zulu:false, datePattern:'dd'}),0,0,0);
	dojo.byId('expire').value= Date.parse(dat);
}catch(err){
	alert(err.Description);
	}
}
function toDate(millis){
	var dat = new Date();
	dat.setTime(millis);
	return dat;
	
}
