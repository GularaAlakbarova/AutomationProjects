package AutomationProject3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.*;


public class AutomationProject3 {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gular\\OneDrive\\Документы\\Eduction\\I Programmer\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.carfax.com/");
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

       //2. Click on "Find a Used Car" button
        driver.findElement(By.xpath("//div [@id='hero-buttons-container-default']/descendant::a[@href='/cars-for-sale']")).click();

        //3.Verify that the page contains text "Used Cars"

        String usedCars = "Used Cars";

        if(driver.getPageSource().contains("Used Cars")){
            System.out.println("The text: " + usedCars + " is present");
        }else{
            System.out.println("The text: " + usedCars + " is not present");
        }

        //4. Choose “Tesla” for  Make.
        Select make = new Select(driver.findElement(By.name("make")));
        make.selectByValue("Tesla");
        make.selectByValue("Honda"); //The UI of the website occasionally blocked the model dropdown nemue. To ensure, that the program will run, I included
                                     // switching to "Honda" and then back to "Tesla". That way UI is not bloking the dropdown.
        make.selectByValue("Tesla");



        //5. Verify that the Select Model dropdown box contains 4 current Tesla models - (Model 3, Model S, Model X, Model Y).
        List<String> actualOptions = new ArrayList<>();
        List<String> expectedOptions = Arrays.asList("Model 3", "Model S", "Model X", "Model Y");
        List<WebElement> elements = new Select(driver.findElement(By.xpath("//select[@name = 'model']"))).getOptions();

        for (int i = 1; i < elements.size() - 1; i++){
            actualOptions.add(elements.get(i).getText().trim());
        }
        Assert.assertEquals(actualOptions, expectedOptions);

        Thread.sleep(1000);

        //6.	Choose “Model S” for Model.

        Select model = new Select(driver.findElement(By.name("model")));
        model.selectByValue("Model S");

        //7.	Enter the zip code 22182 and click Next
        driver.findElement(By.name("zip")).sendKeys("22182");
        driver.findElement(By.id("make-model-form-submit")).click();

        //8.  Verify that the page contains the text “Step 2 – Show me cars with”

        String showMeCars = "Step 2 - Show me cars with";
        if(driver.getPageSource().contains(showMeCars)){
            System.out.println("The text: " + showMeCars + " is present");
        }else{
            System.out.println("The text: " + showMeCars + " is NOT present");
        }

        //9.	Check all 4 checkboxes.

        driver.findElement(By.xpath("//span[@aria-label = 'Toggle noAccidents']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[@aria-label = 'Toggle oneOwner']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[@aria-label = 'Toggle personalUse']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[@aria-label = 'Toggle serviceRecords']")).click();

        Thread.sleep(1000);

        //10.	Save the count of results from “Show me X Results” button. In this case it is 10.
        String resultText =  driver.findElement(By.xpath("//span[@class = 'totalRecordsText']")).getText();
        System.out.println("The total number of results displayed is: " + resultText);

        //11.	Click on “Show me x Results” button.
        driver.findElement(By.xpath("//span[@class = 'totalRecordsText']")).click();

        Thread.sleep(1000);

        //12.	Verify the results count by getting the actual number of results displayed
        // in the page by getting the count of WebElements that represent each result

//        List<WebElement> elements1 = driver.findElements(By.cssSelector("article[id*='listing_']"));
//         for (WebElement element : elements1) { //I am doing it to fool around the Selenium bug. This is like bringing a sacrifice to the mighty Selenium,
//             //in order it allows to further work. For some reason the number of elements is (actual_elements*2)+1, and it displays as 25 elements instead of 12
//         }

        List<WebElement> resultElements = driver.findElements(By.xpath("//*[contains(@id,'listing_')]"));   //Identify  the elements on web page //*[contains(@id,'listing_')]

        int elementsCount = resultElements.size();

        int i = 0;

        for (WebElement e : resultElements) {
            i += 1;
            System.out.println("Result number " + i + " " + e);
        }

        System.out.println("The number of WebElements representing the result is " + elementsCount);

        Assert.assertEquals(elementsCount, Integer.parseInt(resultText));

        //13.	Verify that each result header contains “Tesla Model S”.

        List<WebElement> resultHeader = driver.findElements(By.xpath("//h4[@class = 'srp-list-item-basic-info-model']"));

        String expectedHeader = "Tesla Model S";

        for (WebElement header: resultHeader) {
//            if(header.getText().contains(expectedHeader)){
//                System.out.println("The header contains 'Tesla Model S' text");
            Assert.assertTrue(header.getText().contains(expectedHeader));
            }

        // 14.	Get the price of each result and save them into a List in the order of their appearance. (You can exclude “Call for price” options)
//        List <WebElement> price = new ArrayList<>();

        List<String> a = new ArrayList<>();
        List<WebElement> price  = driver.findElements(By.xpath("//span[@class = 'srp-list-item-price']")); //



        for(int p = 0; p < price.size(); p++){
            if(!price.get(p).getText().contains("Call for Price")){
            a.add(price.get(p).getText());
            System.out.println(price.get(p).getText());
            }
        }

        Thread.sleep(500);

        //15.	Choose “Price - High to Low” option from the Sort By menu

        Select highToLow = new Select(driver.findElement(By.xpath("//select[@aria-label= 'SelectSort']")));
        highToLow.selectByValue("PRICE_DESC");

        Thread.sleep(2000);


        //16.	Verify that the results are displayed from high to low price.

        List <WebElement> sortedPrices = driver.findElements(By.xpath("//*//div[@class='srp-list-container  srp-list-container--srp']/child::article [contains(@id,\"listing_\")]//a/div[1]/div/span")); //   for price
        List<String> sortedPricesToCompare = new ArrayList<>();

        for(int sp = 0; sp < sortedPrices.size(); sp++){

            if((!sortedPrices.get(sp).getText().contains("Call for Price"))) {
                sortedPricesToCompare.add(sortedPrices.get(sp).getText());

                System.out.println("Sorted prices to compare" + sortedPrices.get(sp).getText());
            }
        }

        List<String> expectedPriceDistribution = new ArrayList<String>(sortedPricesToCompare);
        Collections.sort(expectedPriceDistribution, Collections.reverseOrder());

        System.out.println(expectedPriceDistribution);

     Assert.assertEquals(sortedPricesToCompare, expectedPriceDistribution);

        //17.	Choose “Mileage - Low to High” option from Sort By menu

        Select actualMileage = new Select(driver.findElement(By.xpath("//select[@aria-label= 'SelectSort']")));
        actualMileage.selectByValue("MILEAGE_ASC");
        Thread.sleep(1000);

        //18.	Verify that the results are displayed from low to high mileage.

        List <WebElement> sortedMileage = driver.findElements(By.xpath("//*//div[@class='srp-list-container  srp-list-container--srp']/child::article [contains(@id,\"listing_\")]//div[4]/div[2]/span[1]"));
        List<Integer> mileageLowToHigh = new ArrayList<>();



        for(int m = 0; m < sortedMileage.size(); m++){
            if (sortedMileage.get(m).getText().contains("miles")){

                String[] splitMileage = sortedMileage.get(m).getText().split(" ");

               Integer mileage = Integer.parseInt(splitMileage[1].replaceAll("[^-?0-9]+", "").trim());
//                mileageLowToHigh.add(mileage);
                System.out.println("mileage " + mileage);
                System.out.println("splitMileage" + splitMileage);

            }

          }

        List<Integer> expectedMileageDistribution = new ArrayList<>(mileageLowToHigh);
        Collections.sort(expectedMileageDistribution);

        System.out.println(mileageLowToHigh);

        System.out.println("Expected mileage distribution " + expectedMileageDistribution);

        Assert.assertEquals(mileageLowToHigh, expectedMileageDistribution);

        Thread.sleep(1000);


        //19.	Choose “Year - New to Old” option from Sort By menu

        Select yearNewToOld = new Select(driver.findElement(By.xpath("//select[@aria-label= 'SelectSort']")));
        yearNewToOld.selectByValue("YEAR_DESC");

        Thread.sleep(1000);


        //20.	Verify that the results are displayed from new to old year.

        List <WebElement> years = driver.findElements(By.xpath("//*//div[@class='srp-list-container  srp-list-container--srp']/child::article [contains(@id,\"listing_\")] //div[1]/h4"));
        ArrayList<Integer> sortedYears = new ArrayList<>();

        for (WebElement we: years) {
//           sortedYears.add(we);
            String [] splits = we.getText().split(" ");
            System.out.println(splits[0]);

            Integer year = Integer.parseInt(splits[0]);

            sortedYears.add(year);
        }


        ArrayList <Integer> expectedYears = new ArrayList<>();
        for (Integer s: sortedYears) {
            expectedYears.add(s);
            
        }
        Collections.sort(expectedYears, Collections.reverseOrder());

        System.out.println(expectedYears);


        Assert.assertEquals(sortedYears, expectedYears);








    }




        }







