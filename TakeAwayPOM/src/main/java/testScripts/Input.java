package testScripts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import configsAndTestData.ConfigLoader;
import configsAndTestData.ConfigMain;
import configsAndTestData.Environment;
import configsAndTestData.TestData;
import pageFactory.BaseClass;

import pageFactory.LoginPage;

import pageFactory.Utility;

public class Input {

	LoginPage lp;
	BaseClass bc;
	Logger log;
	//Config and test data files---------------------------------
	public static ConfigMain config;
	public static Environment envConfig;
	public static TestData testData;
	//ConfigMain data---------------------------------------------
	
	public static  String suite;
	public static String browserName;
	public static  String screenShotOnPass;
	public static  String screenShotOnFail;
	public static int wait30;
	public static int wait60;
	public static int wait90;
	public static int wait120;
	public static int interval;
	
	//Environment data---------------------------------------------
	public static String url;
	public static String sa1userName;
	public static String sa1password;
	
	//test data------------------
	public static String newUserName;
	public static String newUserEmailID;
	public static String newUserEmailID2;
	public static String newUserPassword;
	public static String existingUserEmailID;
	public static String existingUserPassword;
	public static String existingAddress;
	public static String emailSubjectForAccActivation;  
	public static String confirmationMsgAfterActivation;
	public static String msgUsingSameActivatedLink;  
	public static String msgAddingExsitingUser;
	public static String accountCreationPopUpMsg;
	public static String NoFieldsEntered;
	public static String wrongEmail;
	public static String whenNoPwdProvided;
	public static String invalidPwd;
	public static double iDEALTrandsactionFee;
	public static double PayPalTrandsactionFee;
	public static double VVVCadeaukaartTransactionFee;
	public static double CHIPKNIPTrandsactionFee;
    public static String iDEALorderCancellationMessage;
    
	@BeforeSuite(alwaysRun=true)
	public void loadEnvConfig() throws ParseException, InterruptedException, IOException {
		log = Logger.getLogger("devpinoyLogger");
		log.info("Before Suite is called");
		
		//Common Data-------------------------------------------------------------
		config = (ConfigMain) new ConfigLoader().load("ConfigMain");
		envConfig = (Environment) new ConfigLoader().load(config.env);
		log.info("Running scripts on "+config.env);
		
	
		suite = config.suite;
		browserName = config.browserName;
		screenShotOnPass = config.screenShotOnPass;
		screenShotOnFail = config.screenShotOnFail;
		
		wait30 = config.wait30;
		wait60 = config.wait60;
		wait90 = config.wait90;
		wait120 = config.wait120;
		
		
		
		//Environment data-------------------------------------------------------------
		url=envConfig.url;
		existingUserEmailID= envConfig.existingUserEmailID;
		existingUserPassword= envConfig.existingUserPassword;
		existingAddress= envConfig.existingAddress;
		
		//Test data-------------------------------------------------------------
		
		
		testData = (TestData) new ConfigLoader().load("TestData");
		
		 newUserName=testData.newUserName;
		 newUserEmailID=testData.newUserEmailID;
		 newUserEmailID2=testData.newUserEmailID2;
		 newUserPassword = testData.newUserPassword;
		 emailSubjectForAccActivation= testData.emailSubjectForAccActivation;  
		 confirmationMsgAfterActivation= testData.confirmationMsgAfterActivation;
		 msgUsingSameActivatedLink= testData.msgUsingSameActivatedLink;  
		 msgAddingExsitingUser= testData.msgAddingExsitingUser;
		 accountCreationPopUpMsg= testData.accountCreationPopUpMsg;
		 
		 NoFieldsEntered= testData.NoFieldsEntered;
		 wrongEmail= testData.wrongEmail;
		 whenNoPwdProvided= testData.whenNoPwdProvided;
		 invalidPwd= testData.invalidPwd;
		
		 iDEALTrandsactionFee=testData.iDEALTrandsactionFee;
		 PayPalTrandsactionFee=testData.PayPalTrandsactionFee;
		 VVVCadeaukaartTransactionFee=testData.VVVCadeaukaartTransactionFee;
		 CHIPKNIPTrandsactionFee=testData.CHIPKNIPTrandsactionFee;
		 
		 iDEALorderCancellationMessage = testData.iDEALorderCancellationMessage;
		 log.info("Test Data is loaded");
		
		
		
	}
	
	

	
}
