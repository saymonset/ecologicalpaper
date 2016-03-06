package demo;

/**
 * Created by Exadel Studio
 *
 */
public class NameBean {
	String userName;

	/**
	 * @return User Name
	 * @throws InterruptedException 
	 */
	
	public String DelayAct() throws InterruptedException {
		Thread.sleep(300);
		return null;
	}
	
	
	public String getUserName() {
		return userName;
	}

	/**
	 * @param User Name
	 */
	public void setUserName(String name) {
		userName = name;
	}
}