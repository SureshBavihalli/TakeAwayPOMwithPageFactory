package configsAndTestData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



public class ConfigMain {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public String env;
	

	public String browserName;
	public String screenShotOnPass;
	public String screenShotOnFail;
	
	public String suite;
	
	public int wait30;
	public int wait60;
	public int wait90;
	public int wait120;
	
	
	
	
	public  String getScreenShotOnPass() {
		return screenShotOnPass;
	}
	public  void setScreenShotOnPass(String screenShotOnPass) {
		this.screenShotOnPass = screenShotOnPass;
	}
	public  String getScreenShotOnFail() {
		return screenShotOnFail;
	}
	public void setScreenShotOnFail(String screenShotOnFail) {
		this.screenShotOnFail = screenShotOnFail;
	}
	
	public int getWait30() {
		return wait30;
	}
	public void setWait30(int wait30) {
		this.wait30 = wait30;
	}
	public int getWait60() {
		return wait60;
	}
	public void setWait60(int wait60) {
		this.wait60 = wait60;
	}
	public int getWait90() {
		return wait90;
	}
	public void setWait90(int wait90) {
		this.wait90 = wait90;
	}
	public int getWait120() {
		return wait120;
	}
	public void setWait120(int wait120) {
		this.wait120 = wait120;
	}
	
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	
	
	}
