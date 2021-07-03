import javafx.scene.control.Tab;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;

public class AutomationProject1 {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gular\\OneDrive\\Документы\\Eduction\\I Programmer\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://duotifyapp.us-east-2.elasticbeanstalk.com/register.php");
        driver.manage().window().maximize();

        String expectedTitle = "Welcome to Duotify!";
        String actualTitle = driver.getTitle();

        assertTrue(actualTitle.contains(expectedTitle));

        driver.findElement(By.id("hideLogin")).click();

        driver.findElement(By.id("username")).sendKeys("Gulara", Keys.TAB, "Gulara", Keys.TAB, "Alakbarova",
                Keys.TAB, "alakbarovag@gmail.com", Keys.TAB, "alakbarovag@gmail.com", Keys.TAB, "duoB5", Keys.TAB, "duoB5", Keys.ENTER);

        String URL = driver.getCurrentUrl();
        assertTrue(URL.contains("http://duotifyapp.us-east-2.elasticbeanstalk.com/browse.php?"));

        String actualName = driver.findElement(By.id("nameFirstAndLast")).getText();
        String expectedName = "Gulara Alakbarova";

        assertTrue(actualName.contains(expectedName));

        driver.findElement(By.id("nameFirstAndLast")).click();
        driver.findElement(By.id("rafael")).click();

        assertTrue(URL.contains("http://duotifyapp.us-east-2.elasticbeanstalk.com/register.php"));

        driver. findElement(By.id("loginUsername")).sendKeys("Gularochka", Keys.TAB, "duoB5", Keys.ENTER);

        if ( driver.getPageSource().contains("You Might Also Like")){
            System.out.println("Text 'You Might Also Like' is present. ");
        } else {
            System.out.println("Text 'You Might Also Like' is not present. ");
        }

            driver.findElement(By.id("nameFirstAndLast")).click();
            driver.findElement(By.id("rafael")).click();

            assertTrue(URL.contains("http://duotifyapp.us-east-2.elasticbeanstalk.com/register.php"));


    }
}
