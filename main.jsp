<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>ToDo Manager | Main</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
			@import "http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/tundra/tundra.css";
			@import "http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/resources/dojo.css";
			@import "http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojox/grid/resources/Grid.css";
			@import "http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojox/grid/resources/tundraGrid.css";
		
</style>
<link rel="stylesheet" href="style.css" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js"></script>
<script src="js/service.js"></script>
</head>
<script type="text/javascript">
			var djConfig = {
			        parseOnLoad: true,
			        isDebug: true,
			        locale: 'en-us',
			       	};
		
           dojo.require("dojo.parser");
           dojo.require("dijit.layout.SplitContainer");
           dojo.require("dijit.TitlePane");
           dojo.require("dijit.layout.ContentPane");
           dojo.require("dijit.layout.TabContainer");
           dojo.require("dijit.form.Button");
           dojo.require("dijit.form.TextBox");
		   dojo.require("dijit.Dialog");
		   dojo.require("dojo.data.ItemFileReadStore");
	       dojo.require("dijit.Tree");
	       dojo.require("dojo._base.xhr");
	       dojo.require("dojox.grid.DataGrid");
	       dojo.require("dijit.dijit");
		   dojo.require("dojo.data.ItemFileWriteStore");
		   dojo.require("dojo.number");
		   dojo.require("dijit.form.DateTextBox");
		   dojo.require("dijit.form.ComboBox");
		   dojo.require("dojo.date");
		   dojo.require("dojo.date.locale");
		   dojo.require("dijit.Calendar");
		   dojo.require("dojox.form.DropDownSelect");
           		   
		   dojo.addOnLoad( function(){
		
				dojo.parser.parse()
			} );
	       var layout= [
	    				{ field: "id", width: "20px", name: "ID" },
	    				{ field: "header", width: "100px", name: "Header" },
	    				{ field: "description", width: "100px", name: "Description" },
	    				{ field: "dateExpire", width: "100px", name: "Expire Date" },
	    				{ field: "created", width: "100px", name: "Created Date" },
	    				{ field: "folder", width: "auto", name: "Folder" }
	    			];
	    	       
		       
     </script>
<body>
<div id="header">
  <div class="logo">This uses DoJo, Java Servlets with JSP, Ajax<br />
    <img class="img" src="img/logo.jpg" alt="Logo" /></div>
  <div class="menu"><div id="ses_info"></div></div>
</div>
<div id="main">
  <div id="content">
  <div id='menu_' name='menu_'></div>
  <br />
  <div class="tundra" id="mainTabContainer" dojoType="dijit.layout.TabContainer" style="width:560px;height:300px" >
  	
           <div id="tab1" dojoType="dijit.layout.ContentPane" title="Tree View"  selected="true" closable="false">
		
	  <div dojoType="dojo.data.ItemFileReadStore" url="tree.json" jsid="treeStore" id="treeStore"></div> 
      <div dojoType="dojo.data.ItemFileReadStore" url="exp.json" jsid="expStore" id="expStore"></div> 
      <div dojoType="dojo.data.ItemFileReadStore" url="nonexp.json" jsid="nonexpStore" id="nonexpStore"></div> 
      
          <div dojoType="dijit.Tree" store="treeStore" labelAttr="sname" label="" id="tree1" name="tree1">
	        <script type="dojo/method" event="onClick" args="item">
	 			if (treeStore.getValue(item, "type") == undefined){
				selectItem(treeStore.getValue(item, "id"), treeStore.getValue(item, "header"),treeStore.getValue(item, "description"),treeStore.getValue(item, "dateExpire"),treeStore.getValue(item, "created"),treeStore.getValue(item, "folder"));
  			}else{initMenu(false);
				clearFields();
				
			}
  			</script>
	      </div>
	    </div>
      <div id="tab2" dojoType="dijit.layout.ContentPane" title="Expired Tasks" closable="false">
      <table dojoType="dojox.grid.DataGrid" jsid="grid1" id="grid1" 
			store="expStore" query="{id:'*' }" rowsPerPage="20" structure="layout" rowSelector="20px" height="250px">
		</table>
	   </div>
      <div id="tab3" dojoType="dijit.layout.ContentPane" title="Non-Expired Tasks" closable="false">
	         <table dojoType="dojox.grid.DataGrid" jsid="grid2" id="grid2" 
			store="nonexpStore" query="{id:'*' }" rowsPerPage="20" structure="layout" rowSelector="20px" height="250px">
		</table>
	      </div>
	  <div id="tab4" dojoType="dijit.layout.ContentPane" title="Console" closable="false">
      <div id='console'>Console output</div>
      </div>
      </div>
    <br />
     <div style="width:550px" class="navhead" id="selected_header"></div>
	    <div style="width:552px" class="navitem" id="selected_info"></div>
    </div>
  <div id="nav">
  <div dojoType="dijit.layout.SplitContainer"
  orientation="vertical"
  sizerWidth="7"
  activeSizing="true"
  style="border: 1px solid #bfbfbf; float: left; margin-right: 30px;  width: 190px; height: 350px;" class="tundra">
    <div dojoType="dijit.layout.ContentPane" sizeMin="10" sizeShare="50">
           <ul>
           <li><a href="javascript:reloadTree();">Refresh tree</a></li>
           <li><a href="javascript:getAJAX('insert.ses?header_=qwerty&descr=blabladescr&folder=0&expire=11111111','console',true);">Create demo task Folder1</a></li>
		   <li><a href="javascript:getAJAX('insert.ses?header_=qwerty&descr=blabladescr&folder=1&expire=11111111','console',true);">Create demo task Folder2</a></li>
		   <li><a href="javascript:getAJAX('insert.ses?header_=qwerty&descr=blabladescr&folder=2&expire=11111111','console',true);">Create demo task Folder3</a></li>
		   
		   <li><a href="javascript:getAJAX('close.ses','console', true);">Close current session</a></li>
		   <li><a href="javascript:initInsert();">Create new task</a></li>
		   <li><a href="javascript:getAJAX('info.ses','ses_info');">Refresh info</a></li>
		   </ul>
    </div>
    <br />
	    <div class="tundra" dojoType="dijit.layout.ContentPane" sizeMin="10" sizeShare="50">
	    Some text
	    </div>
	</div>   
	    
  </div>
  
  <div class="footer"></div>
  </div>
<div id="footer"> Copyright &copy; 2010 <a href="mailto:barthezzko@gmail.com">Baytsurov Mikhail</a> <br />
This is a test task for Axmor</div>
<div dojoType="dijit.Dialog" id="dialog_div" title="rrere" class="tundra" style="border:1px solid #ddd;">
			<form method="post" action="insert.ses" id="myform" name="myform">
				Header<br/>
				<input type="text" trim="true" dojoType="dijit.form.TextBox" value="" name="header_" id="header_"/><br/>
				Description<br/>
				<input type="text" trim="true" dojoType="dijit.form.TextBox" value="" name="descr" id="descr"/><br/>
				Expiration time<br/>
				<div id="calen" dojoType="dijit.Calendar" onChange="toMillis(arguments[0]);"> </div>    
				Folder<br/>
				<select dojoType="dijit.form.ComboBox" id="folderlist" name="folderlist" onChange="dojo.byId('folder').value = getFolderId(arguments[0]);">
		            <option>
		                Main Tasks
		            </option>
		            <option selected>
		                Home Tasks
		            </option>
		            <option>
		                Sport Tasks
		            </option>
       			</select>
       			<input type="hidden"  value="" name="expire" id="expire"/>
				<input type="hidden" trim="true"  value="" name="id" id="id"/>
				<input type="hidden" trim="true"  value="" name="folder" id="folder"/>
				
				<button dojoType="dijit.form.Button" type="button" id="send_butt" onClick="formSubmit('myform','console'); dijit.byId('dialog_div').hide(); reloadTree();"></button>
				
			</form>
		</div>
		<script type="text/javascript">
		initMenu(false);
		</script>
</body>
</html>
