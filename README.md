# axmor-test-task

That's great to look back how it all started. :) That's one of the first pet-projects I've been working on.
In 2010 I was looking for the first company to work and lucky me, Axmor was hiring.
Big thanks to Andrey Kanonirov to interviewing/hiring me, Evgeniy Konchikov for managing, and Inna Pukhova and Alexander Poplavskiy for helping me with first steps.   

## Task description
[Task description](https://github.com/barthezzko/axmor-test-task/blob/master/task.md)

## Disclaimer
Don't use that as a reference "how-to" - that's, obviously, not the way to go. 

# All below is the untouched version of instructions that the project was shipped with:

## Installation guide
1) U need a Tomcat Web server installed (my version is Apache Tomcat/6.0.28 with JVM 1.6.0_20-b02).
2) Go to http://localhost:8080/manager/html/list in your browser
3) U should upload the WAR file attached to this e-mail into the Deploy Section. There are two ways to deploy a new project. Please choose the second one by clicking the "Browse" near the "WAR file to deploy" label.
4) The web archive file (WAR) will be uploaded to the server and automatically set up.
5) Go http://localhost:8080/baytsurov/main.jsp

## Description
This is a Java Servlet application with DoJo library using.
There is no need to store a local copy of Dojo. That's why the WAR is a tiny file. But U need to have an external Internet connection. I used the Google store: http://ajax.googleapis.com/ajax/libs/dojo/1.5
This has a single main page (main.jsp)
There are four tabs used for viewing the data retrieved from the server. First is tree view - here is a folder structure with its contents.
The second is for observe expected tasks. Third is for unexpected. The forth and the last is for collecting information like console. Its optinal. But I used it during the development process.

There are 4-6 icons displayed above the TabContainer Dojo widget. Their functions are: refresh data, add a task, edit selected task, delete selected, get session information and the last - close current session with all data deleting.
Right side copies the functions of described icons. But there three additional links - these links are for creating demo tasks in folders named in their captions.
I used a couple of Dojo widgets in my application.
Server side is made of servlets. Data is stored in TaskBean objects stored in session scope - I think it's not necessary to describe it.
I've decided to extend the list of task attributes by adding the description.

## Problems not solved to this moment
IE caching makes unavailable data updating using the dojo.xhr(). I tried to set preventCache to true, but nothing changed. I'm still trying.
