package AutomationProject2;

import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;

import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;

public class AutomationProject2 {

    public static void main(String[] args) throws InterruptedException, IOException, CsvValidationException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gular\\OneDrive\\Документы\\Eduction\\I Programmer\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        driver.manage().window().maximize();


        String [] csvCell;

        CSVReader csvReader = new CSVReader(new FileReader("C:\\Users\\gular\\IdeaProjects\\AutomationProjects\\src\\AutomationProject2\\MOCK_DATA.csv"));

        while ((csvCell = csvReader.readNext()) != null){

            driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
            driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
            driver.findElement(By.name("ctl00$MainContent$login_button")).click();

            driver.findElement(By.linkText("Order")).click();


            driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).clear();
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys("8");
            driver.findElement(By.xpath("//input[@value =\"Calculate\"]")).click();

            String actualAmount = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtTotal")).getAttribute("value");
            System.out.println(actualAmount);
            String expectedAmount = "800";
            assertTrue(actualAmount.contains(expectedAmount));


            driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).clear();
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys("20");
            driver.findElement(By.xpath("//input[@value =\"Calculate\"]")).click();

            String name = csvCell[0];
            String street = csvCell[1];
            String city = csvCell[2];
            String state = csvCell[3];
            String zip = csvCell[4];
            String product = "MyMoney";

            driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys(name);
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys(street);
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys(city);
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys(state);
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(zip);

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            //Selecting random radio button

            Random rand = new Random();
            int cardType = rand.nextInt(3);
            long cardNumber = 0;

            String cardName = "";

            if(cardType == 0){
                cardName = "Visa";
            }else if(cardType == 1){
                cardName = "MasterCard";
            }else if(cardType == 2){
                cardName = "American Express";
            }


            //Generating card number corresponding to the selected card type
            if(cardType == 0){
                driver.findElement(By.cssSelector("input[type='radio'][value='Visa']")).click();
                cardNumber = (long)(4000000000000000L + Math.random()*1000000000000000L);

            }else if(cardType == 1){
                driver.findElement(By.cssSelector("input[type='radio'][value='MasterCard']")).click();
                cardNumber = (long) (5000000000000000L + Math.random() * 1000000000000000L);
            }else if(cardType == 2){
                driver.findElement(By.cssSelector("input[type='radio'][value='American Express']")).click();
                cardNumber = (long)(300000000000000L + Math.random()*100000000000000L);
            }
            Long cardNumberWrapper = new Long(cardNumber);

            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(cardNumberWrapper.toString());




            //entering expiration date

            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys("11/21");

            driver.findElement(By.className("btn_light")).click();

            Thread.sleep(2000);



            //Verifying that "New order has been successfully added" text is on the page

            String text = "New order has been successfully added";
            assertTrue(driver.getPageSource().contains(text));


            // Click "View all orders"
            driver.findElement(By.xpath("//a[contains(.,'View all orders')]")).click();

            //Verify that the order information matches the input information


            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String todaysDate= formatter.format(date);

            String actualFirstRowOfTr = driver.findElements(By.tagName("tr")).get(2).getText();
            String expectedFirstRowOfTr = name +" "+ product +" "+ "20" +" "+ todaysDate + " "+ street +" "+city+ " "+ state +" "+ zip +" "+cardName +" "+cardNumberWrapper+" "+"11/21";
            assertEquals(actualFirstRowOfTr, expectedFirstRowOfTr);


            //Logout of the application

            driver.findElement(By.id("ctl00_logout")).click();


        }





    }

}
