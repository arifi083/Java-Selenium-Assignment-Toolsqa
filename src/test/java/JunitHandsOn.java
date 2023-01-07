import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.attribute.AclEntry;
import java.time.Duration;
import java.util.List;

public class JunitHandsOn {
    WebDriver driver;
    WebDriverWait wait;


    @Before
    public void SetUp(){

        System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver_win32/chromedriver.exe");
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.addArguments("--headed");
        driver= new ChromeDriver();
        //maximize window size
        driver.manage().window().maximize();
        //timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

   // @Test
    public void getTitle() throws InterruptedException {
        //open a new url in browser
        driver.get("https://demoqa.com");
        Thread.sleep(5000);
        String title=driver.getTitle();
        System.out.println("Title of Demo site : " + title);
        Assert.assertTrue(title.contains("ToolsQA"));

    }

    //@Test
    public void verifyImage(){
        //open a new url in browser
        driver.get("https://demoqa.com");
        //explicit wait
        wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        boolean imageStatus=wait.until(ExpectedConditions.elementToBeClickable(By.className("banner-image"))).isDisplayed();
        System.out.println(imageStatus);
        Assert.assertTrue(imageStatus);
    }

   //@Test
    public void registration() throws InterruptedException {
        //open the url
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Arif Miah");
        driver.findElement(By.id("userEmail")).sendKeys("arif@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Khilket");
        driver.findElement(By.id("permanentAddress")).sendKeys("Bogura");
        wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        boolean submitBtn=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submit']"))).isDisplayed();
        Assert.assertTrue(submitBtn);

        //driver.findElement(By.xpath("//button[@id='submit']")).click();
        WebElement element = driver.findElement(By.xpath("//button[@id='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);

        Thread.sleep(7000);


    }
   //@Test
    public void doubleClick() throws InterruptedException {
        driver.get("https://demoqa.com/buttons");
        WebElement doubleClickElement = driver.findElement(By.id("doubleClickBtn"));
        Actions action = new Actions(driver);
        action.doubleClick(doubleClickElement).perform();
        Thread.sleep(6000);
        String doubleClickMsg = driver.findElement(By.id("doubleClickMessage")).getText();
        Assert.assertTrue(doubleClickMsg.contains("You have done a double click"));

    }
    //@Test
    public void multipleButtons() throws InterruptedException {
        driver.get("https://demoqa.com/buttons");
        //List<WebElement> multipleButtons1 = driver.findElement(By.cssSelector("[type=button]"));
        List<WebElement> multipleButtons= driver.findElements(By.cssSelector("[type=button]"));
        Actions action = new Actions(driver);
        action.doubleClick(multipleButtons.get(1)).perform();
        Thread.sleep(6000);
        String doubleClickMsg = driver.findElement(By.id("doubleClickMessage")).getText();
        Assert.assertTrue(doubleClickMsg.contains("You have done a double click"));

    }
   // @Test
    public void rightClickBtn(){
        driver.get("https://demoqa.com/buttons");
        WebElement rightClick= driver.findElement(By.id("rightClickBtn"));
        Actions actions = new Actions(driver);
        actions.contextClick(rightClick).build().perform();
       // actions.contextClick(rightClick).build().perform();
        String rightClickMsg = driver.findElement(By.id("rightClickMessage")).getText();
        Assert.assertTrue(rightClickMsg.contains("You have done a right click"));
        System.out.println("Right Click text" + rightClickMsg);
    }
   // @Test
    public void acceptCookies() throws InterruptedException {
        driver.get("https://cleanerdhaka.ideascale.com/a/community/login");
       WebElement cookies = driver.findElement(By.xpath("//button[@class='btn btn-primary flex-fill json-link']"));
        Actions actions = new Actions(driver);
        actions.click(cookies).build().perform();
        Thread.sleep(1000);
    }
   // @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("confirmButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();
        Thread.sleep(1000);
       String cancelText = driver.findElement(By.id("confirmResult")).getText();
       System.out.println("cancel Text"+ cancelText);
       Assert.assertTrue(cancelText.contains("You selected Cancel"));

    }
   // @Test
    public void selectDropDown() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        Select colors = new Select(driver.findElement(By.id("oldSelectMenu")));
        colors.selectByIndex(1);
        Thread.sleep(3000);
        colors.selectByValue("3");
        Thread.sleep(3000);

        if(colors.isMultiple()){
            colors.selectByValue("2");
            System.out.println("it is multiple ");
        }
        else{
            colors.selectByIndex(3);
            System.out.println("it is not multiple ");
        }
    }
   // @Test
    public void selectMultipleDropdwon() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        Select cars = new Select(driver.findElement(By.id("cars")));
        cars.selectByValue("volvo");
        cars.selectByValue("opel");
        Thread.sleep(3000);
        if(cars.isMultiple()){
            System.out.println("it is multiple ");
        }
        else {
            System.out.println("it is not multiple ");
        }
    }

    @Test
    public void selectMultiItem() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scroll(0, 550)");
        driver.findElement(By.xpath("//div[@class='row']/*/div[@class=' css-2b097c-container']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.id("react-select-4-option-0")).click();
        driver.findElement(By.id("react-select-4-option-1")).click();
        driver.findElement(By.id("react-select-4-option-2")).click();
        driver.findElement(By.id("react-select-4-option-3")).click();
        List <WebElement> colors = driver.findElements(By.cssSelector("div.css-1rhbuit-multiValue"));

        for(WebElement e: colors) {

            System.out.println(colors.size());
            System.out.println( e.getText() );
        }

        Thread.sleep(3000);



    }



   @After
    public void closeBrowser(){
        driver.close();
    }
}