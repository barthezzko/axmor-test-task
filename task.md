## Test task: To-Do – Ajax – Dojo – Tomcat
The task is to create a &quot;ToDo List&quot; application using Dojo Ajax toolkit library (http://dojotoolkit.org/).
The application must be run on Tomcat Web server ( http://tomcat.apache.org/ ). Use latest stable versions
of products.
The application should allow viewing and managing personal to-do items.
To-Do item has the following fields from the user’s point of view:
- id (automatically assigned)
- time of entry (automatically assigned)
- expiration time. (entered by the user)
- description (entered by the user) (up to 128 chars)
Note that initially the application must have several To-Do items pre-populated (just for demo purposes).
 
**The application must support the following functionality:**
- The application should have an installation instruction.
- The application should have a single main page that shows the Tree with the folders (allows organizing
To-Do items like folders in MS Outlook or another mail client). When a user selects a particular folder it
shows the list of To-Do items created in this folder.
- For demo purposes the folder structure must not be editable. I.e. the initial folders are created with the
initial To-Do items.
- Application should use Dojo widgets for UI controls. At least the following widgets must be leveraged:
Tree, ContentPage, TabContainer, SplitContainer, Grid, Dialog (e.g. for editing a To-Do entry), Form
(with Button, SelectBox, Date picker, TextBox widgets in it).
- Non-expired items should be displayed in a normal font face. The expired items should be displayed in
bold.
- The application must store data on the server-side. For demo purposes, use memory-based data storage
in the session scope (i.e. no database must be required: all changes are discarded when user’s session is
invalidated).
- The application should allow adding/editing/removing new to-do items.
- The application should retrieve and store the data using Ajax calls.
- All UI updates must be performed without page reloading.

Benefits (optional):
1. Use the Spring library ( http://www.springframework.org/ ) on the server side. This library allows
organizing application as a set of well-defined simple Java objects and provides a configuration
layer for these objects. At least the following Spring modules should be used:
o Spring Core (IoC container);
o Spring MVC (for creating web controllers).
2. Use ANT to build the code and create a WAR file for deployment. If anything is to be configured
before compiling/packaging, then specify these parameters in an ANT property file.
3. Extra benefit. Use several advanced features of the Grid widget like spanned columns, sub-rows,
inline editing.
4. Extra benefit. Try implementing some type of sorting for the Grid.
Implementation of any benefits is optional. An applicant is supposed to properly develop the main task
and start working on the benefits only if he\she is sure that the work has been done with good quality and
according to the criteria specified in the beginning of this document.