public class TaskBean implements java.io.Serializable {
 private int id=0;
 private String Header="";
 private String Description="";
 private long DateExpire=0;
 private long Created=0;
 private byte Folder=0;
 /*
  * TaskBean object with private fields, public Set and Get methods and a constructor
  * Folder number is 256 max. Otherwise, the Folder type should be changed to "short" or "int"
  * Class was moved to a bean
  * */
 public TaskBean(){
 } 
 
 /*
  * get section
  * */
 public int getId(){
	 return (this.id);
 }
 public String getHeader(){
	 return (this.Header);
 }
 public String getDescription(){
	 return (this.Description);
 }
 public long getDateExpire(){
	 return (this.DateExpire);
 }
 public long getCreated(){
	 return (this.Created);
 }
 public byte getFolder(){
	 return (this.Folder);
 }
 /*
  * set section
  * */
 public void setId(int id_){
	 this.id = id_;
 }
 public void setHeader(String Header){
	 this.Header=Header;
 }
 public void setDescription(String Description){
	 this.Description=Description;
 }
 public void setDateExpire(long DateExpire){
	 this.DateExpire=DateExpire;
 }
 public void setCreated(long Created){
	 this.Created=Created;
 }
 
 public void setFolder(byte Folder){
	 this.Folder=Folder;
 }
 
 } 

