

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class RadioButton {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gular\\OneDrive\\Документы\\Eduction\\I Programmer\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.cars.com/");
        driver.manage().window().maximize();

//        driver.findElement(By.id("vfb-7-1")).click();
//        driver.findElement(By.id("vfb-7-2")).click();
//        driver.findElement(By.id("vfb-7-3")).click();
//
//        Thread.sleep(1000);
//
//        driver.findElement(By.id("vfb-6-0")).click();
//        driver.findElement(By.id("vfb-6-0")).click();
//        driver.findElement(By.id("vfb-6-1")).click();
//        driver.findElement(By.id("vfb-6-1")).click();
//        driver.findElement(By.id("vfb-6-2")).click();
//        driver.findElement(By.id("vfb-6-2")).click();


        driver.findElement(By.id("body_style"));

        Select s = new Select (driver.findElement(By.id("body_style"))); //use select when you have a page with the select option
        List<WebElement> we = s.getOptions();
//        System.out.println(we);

        for(WebElement w: we){
            System.out.println(w.getText());
        }

        s.getFirstSelectedOption();
        s.selectByIndex(3);
        s.selectByVisibleText("SUV");
        s.selectByValue("truck");

        }




    }
