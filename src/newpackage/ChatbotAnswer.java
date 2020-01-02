package newpackage;

import TestData.MyClass1;
import Locators.EvaLocators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.xssf.usermodel.XSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;

/*This is a Java program using Selenium WebDriver to automate the answer storing for a ChatBot. An Excel file containing all the
 * questions to be asked must be available prior. The program will automatically ask the ChatBot the questions from the Excel file
 * and store the answer for it .
 */

public class ChatbotAnswer {
	
	  private static XSSFWorkbook book;   /*Create a XSSFWORKBOOK variable to access our excel workbook*/
	  private static WebDriverWait wait;  /*Create a wait variable which will be used to wait until the required web element is loaded*/

	  public static void main(String[] args) throws InterruptedException {
		  
		/*Since we are using Chrome we need to use the ChromeDriver along with Selenium WebDriver*/
		        
		System.setProperty(EvaLocators.selenium_driver,EvaLocators.chromedriver_location);
		WebDriver driver = new ChromeDriver();  /*create a new instance of ChromeDriver*/
		
		driver.get(EvaLocators.baseUrl);  /*Open the webpage using Selenium WebDriver*/
		wait = new WebDriverWait(driver, 10); /*Wait until webpage is loaded*/
	    
	    driver.manage().window().maximize(); /*Use Selenium WebDriver to maximise the Chrome Window*/
	    
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
		
		try { /*Opening the Excel file that contains the question and answer for the chatbot*/
			
			File excel=new File("Book3.xlsx");
			/*Put the data from the excel file in an input stream to be accessed by the program*/
			FileInputStream fis = new FileInputStream(excel); 
			book = new XSSFWorkbook(fis); 
			/*Get the first sheet of the opened workbook*/
			XSSFSheet sheet = book.getSheetAt(0); 
			/*Initialise row iterator to iterate through each row of the excel sheet*/
			Iterator<Row> itr = sheet.iterator(); 
			/*Continuing While loop as long as Row iterator has a next row*/
			while(itr.hasNext())
			{	
				/*Storing the current row of the excel sheet in a Row variable*/
				Row row = itr.next(); 
				/*Get the cell of the first coloumn of the current row*/
				Cell cell=row.getCell(0);
				/*Getting the String value of the data stored in the cell . The first coloum always contains the Question to ask the bot*/
				String s2=cell.getStringCellValue();
				
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
				
				/*Get all the cells of the current row and store it in a List*/
		    	List<Cell> cells = new ArrayList<Cell>();
		    	/*Get the cell of the second column of the current row and the add the chatbot's reply into the this cell*/
				Cell cell1=row.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cells.add(cell1);
				cell1.setCellValue(kk);
				/*Code to fill the text from the text stream to our excel file*/
				FileOutputStream fos = new FileOutputStream(excel);
				book.write(fos);
				/*Close the output stream*/
				fos.close();

			}
	     }
		
		/*Catch block to handle exceptions if File is not found or if there is no appropriate input or output*/
		catch (FileNotFoundException fe) 
		{ fe.printStackTrace(); } 
        catch (IOException ie)
		{ ie.printStackTrace(); } 
		
		/*Code to close Webdriver and Quit from the Internet Browser*/
		 
		driver.close();
		driver.quit();
		 
}
}