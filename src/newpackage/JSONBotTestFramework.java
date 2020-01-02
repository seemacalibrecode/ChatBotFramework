package newpackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import Locators.EvaLocators;
import TestData.MyClass1;

/*This is a Java program using Selenium WebDriver to automatically test a ChatBot's Working. We need to provide an JSON file 
 * containing both the Question and its corresponding expected answer from the ChatBot in it. The program will ask the bot the 
 * question from the JSON file and then compare the Chatbot's answer with the answer in the JSON file and then give the
 * result accordingly .
 */

public class JSONBotTestFramework {
	
	private static XSSFWorkbook book;  /*Create a XSSFWORKBOOK variable to access our excel workbook*/
	private static WebDriverWait wait;  /*Create a wait variable which will be used to wait until the required web element is loaded*/

	public static void main(String[] args) throws InterruptedException {
		  
		/*Since we are using Chrome we need to use the ChromeDriver along with Selenium WebDriver*/
		
		System.setProperty(EvaLocators.selenium_driver,EvaLocators.chromedriver_location);
		WebDriver driver = new ChromeDriver();  /*create a new instance of ChromeDriver*/
		
		driver.get(EvaLocators.baseUrl);   /*Open the webpage using Selenium WebDriver*/
		wait = new WebDriverWait(driver, 10);  /*Wait until webpage is loaded*/
	    
	    driver.manage().window().maximize();  /*Use Selenium WebDriver to maximise the Chrome Window*/
	    
	    /*Code to Wait until the Yes Button is loaded and click it */
	    
	    WebDriverWait wait1 = new WebDriverWait(driver, 10);
	    wait1.until(ExpectedConditions.or(
	    ExpectedConditions.visibilityOfElementLocated(By.xpath(EvaLocators.Yes_Button))
	    ));
	    
	    WebElement actionBtn=driver.findElement(By.xpath(EvaLocators.Yes_Button));
		actionBtn.click();
		
		/*Code to Wait until the Sign-In Button is loaded and click it */
        
		WebDriverWait wait2 = new WebDriverWait(driver, 10);
		wait2.until(ExpectedConditions.or(
		ExpectedConditions.visibilityOfElementLocated(By.xpath(EvaLocators.Sign_In_email))
		));	    
	    
        WebElement actionBtn5 = driver.findElement(By.xpath(EvaLocators.Sign_In_email));
        actionBtn5.click();
        
        /*Code to Wait until the Email-id Textarea is loaded and enter the email id */
		
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.or(
		ExpectedConditions.visibilityOfElementLocated(By.xpath(EvaLocators.email_id))
		));	    
		
	 	WebElement actionBtn2=driver.findElement(By.xpath(EvaLocators.email_id));
	    actionBtn2.sendKeys(MyClass1.email_id);
	    /*Code to press "ENTER" Keyboard button */
	    actionBtn2.sendKeys(Keys.RETURN);
	    
	    /*Code to Wait until the Password Textarea is loaded and enter the password */
	 	
	    WebDriverWait wait4 = new WebDriverWait(driver, 10);
	    wait4.until(ExpectedConditions.or(
	    ExpectedConditions.visibilityOfElementLocated(By.id(EvaLocators.password))
	    ));	  
		
	    WebElement actionBtn3=driver.findElement(By.id(EvaLocators.password));
	    actionBtn3.sendKeys(MyClass1.password);
	    actionBtn3.sendKeys(Keys.RETURN);
	    
	    /*Code to Wait until the Sign-In Button is loaded and click it */
		
		WebDriverWait wait5= new WebDriverWait(driver, 10);
	    wait5.until(ExpectedConditions.or(
	    ExpectedConditions.visibilityOfElementLocated(By.id(EvaLocators.sign_in))
	    ));	  
		
		WebElement actionBtn4=driver.findElement(By.id(EvaLocators.sign_in));
	    actionBtn4.click();
		
		
		
		try {   /*Opening the Json file that contains the question and answer for the chatbot*/
			    
			    File excel=new File("employees.txt");
			    /*Parsing the data from the file into JSON format*/
			    JSONParser parser=new JSONParser();
			    /*Store the parsed data from the file into a JSON Array*/
		        org.json.simple.JSONArray array= (org.json.simple.JSONArray) parser.parse(new FileReader("F:employees.txt"));
		        /*Get the size of the JSON Array*/
		        int size=array.size();
		        /*For loop is used so that all the questions in the JSON file will be asked to the Chatbot*/
		        for(int q=0;q<size;q++)
		        {   /*Get the first JSON Object from the JSON Array*/
		        	org.json.simple.JSONObject rec = (org.json.simple.JSONObject)array.get(q);
		        	/*Get the value of the "Question" key from the JSON Object*/
		        	String s2=(String)rec.get("Question");
		        	
		        	/*Code to wait until the textarea of the webpage is loaded and then send the Question from the excel sheet*/
		        	
		        	WebDriverWait wait6= new WebDriverWait(driver, 10);
		        	wait6.until(ExpectedConditions.or(
		        	ExpectedConditions.visibilityOfElementLocated(By.xpath(EvaLocators.textarea))
		            ));	  
			
				    WebElement actionBtn6=driver.findElement(By.xpath(EvaLocators.textarea));
				    actionBtn6.sendKeys(s2);
				    actionBtn6.sendKeys(Keys.RETURN);
				    Thread.sleep(10000);
				    
				    /*Getting the reply of the bot for the question asked. The chatbubble of the bot's reply has the same HTML code so store 
					 * all the replies in a List . 
					 */
				    
				    List<WebElement> allChats = driver.findElements(By.xpath(EvaLocators.chat_bubble));
				    /*Get the size of the List and then Use GetText() to get the String of the Last messasge sent by the chatbot*/
				    String kk = allChats.get(allChats.size() -1).getText();
				    /*Get the value of the "Answer" key from the JSON Object*/
				    String k1=(String)rec.get("Answer");
				    /*If the chatbot's reply matches with the value of "Answer" key , then print "Pass" , else print "No"*/
			        if(k1.equals(kk))
			        {
					   System.out.print("Pass");
			           System.out.print("\n");
			        }
				    else
					{
					   System.out.print("No");
					   System.out.print("\n");
					
					}
				
		         }
			 }
	

		/*Catch block to handle exceptions if File is not found or if there is no appropriate input/output or if there is any
		 * Parsing exception*/
		catch (FileNotFoundException fe) 
		{ fe.printStackTrace(); } 
        catch (IOException ie)
		{ ie.printStackTrace(); } 
		catch (org.json.simple.parser.ParseException pe) 
		{
			pe.printStackTrace();
		} 
		 
		/*Code to close Webdriver and Quit from the Internet Browser*/
		
		driver.close();
		driver.quit();
		 
}
}
