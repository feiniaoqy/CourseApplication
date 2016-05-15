package entity;
/**
 * user model
 * @author asus
 *
 */
public class Student {

	private int id;				/**用户id**/
	private String name;			/**用户名称**/
	private String studyNum;		/**学号**/
	private String telNum;			/**用户电话号码**/
	private String MACAddress;  	/**mac地址**/
	private boolean arrivalState;

	public Student(int id, String name, String studyNum, String MACAddress, String telNum,  boolean arrivalState) {
		this.id = id;
		this.name = name;
		this.studyNum = studyNum;
		this.MACAddress = MACAddress;
		this.telNum = telNum;

		this.arrivalState = arrivalState;
	}

	/**签到状态**/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(String studyNum) {
		this.studyNum = studyNum;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getMACAddress() {
		return MACAddress;
	}

	public void setMACAddress(String MACAddress) {
		this.MACAddress = MACAddress;
	}

	public boolean isArrivalState() {
		return arrivalState;
	}

	public void setArrivalState(boolean arrivalState) {
		this.arrivalState = arrivalState;
	}
}
