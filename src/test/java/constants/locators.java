package constants;

public class locators {

	
	public static final String url = "https://demoqa.com/";
	public static final String newWindow = "//button[@id='windowButton']";
	
	//Forms Page
	public static final String forms = "//h5[text()='value']";
	public static final String practiceform = "//span[text()='Practice Form']";
	public static final String firstName = "//*[@id='firstName']";
	public static final String lastName  = "//*[@id='lastName']";
	public static final String email = "//*[@id='userEmail']";
	public static final String maleRadioBtn = "//*[@id='gender-radio-1']";
	public static final String number = "//*[@id='userNumber']";
	public static final String date = "//*[@id='dateOfBirthInput']";
	public static final String subjects = "//*[@id='subjectsContainer']";
	public static final String hobby = "//*[@id='hobbies-checkbox-1']";
	public static final String upload = "//*[@id='uploadPicture']";
	public static final String address = "//*[@id='currentAddress']";
	public static final String state = "(//div[normalize-space(@class)='css-tlfecz-indicatorContainer'])[1]";
	public static final String city = "(//div[normalize-space(@class)='css-tlfecz-indicatorContainer'])[2]";
	public static final String submit = "//*[@id='submit']";

	public static String brokenFullName ="//input[@id='userName_old']";

	public static String brokenEmail ="//input[@id='userEmail_old']";

	//public static String brokenAddress ="//textarea[@id='currentAddress_old']";

	//public static String brokenSubmit ="//button[@id='submit_old']";
	
	
	public static String demoqaTextBoxUrl =
	        "https://demoqa.com/text-box";

	public static String userName =
	        "//input[@id='userName']";

	public static String userEmail =
	        "//input[@id='userEmail']";

	public static String currentAddress =
	        "//textarea[@id='currentAddress']";

	public static String submitButton =
	        "//button[@id='submit']";

	public static String outputName =
	        "//p[@id='name']";
	
//	public static String brokenUserName =
//	        "//input[@id='userName_old']";
//
//	public static String brokenUserEmail =
//	        "//input[@name='email_old']";
//
//	public static String brokenAddress =
//	        "//textarea[@placeholder='Current Address Old']";
//
//	public static String brokenSubmit =
//	        "//button[@class='btn_old']";
	
	
	
	
	
	//final validations
	public static String validUserName =
	        "//input[@id='userName']";

	public static String validUserEmail =
	        "//input[@id='userEmail']";

	public static String validAddress =
	        "//textarea[@id='currentAddress']";

	public static String validSubmit =
	        "//button[@id='submit']";
	
	public static String brokenUserName =
	        "//input[@id='userName_old']";

	public static String brokenUserEmail =
	        "//input[@name='email_old']";

	public static String brokenAddress =
	        "//textarea[@placeholder='Current Address Old']";

	public static String brokenSubmit =
	        "//button[@class='btn_old']";

	public static String impossibleLocator =
	        "//button[@class='random_999999']";
	
	
	
	
}
