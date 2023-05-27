import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.AclEntry;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JunitHandsOn {
    WebDriver driver;
    WebDriverWait wait;


    @Before
    public void SetUp(){

        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void getTitle() throws InterruptedException {
        //open a new url in browser
        driver.get("https://demoqa.com");
        Thread.sleep(5000);
        String title=driver.getTitle();
        System.out.println("Title of Demo site : " + title);
        Assert.assertTrue(title.contains("ToolsQA"));

    }

    @Test
    public void verifyImage(){
        //open a new url in browser
        driver.get("https://demoqa.com");
        //explicit wait
        wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        boolean imageStatus=wait.until(ExpectedConditions.elementToBeClickable(By.className("banner-image"))).isDisplayed();
        System.out.println(imageStatus);
        Assert.assertTrue(imageStatus);
    }

    @Test
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
    @Test
    public void doubleClick() throws InterruptedException {
        driver.get("https://demoqa.com/buttons");
        WebElement doubleClickElement = driver.findElement(By.id("doubleClickBtn"));
        Actions action = new Actions(driver);
        action.doubleClick(doubleClickElement).perform();
        Thread.sleep(6000);
        String doubleClickMsg = driver.findElement(By.id("doubleClickMessage")).getText();
        Assert.assertTrue(doubleClickMsg.contains("You have done a double click"));

    }

    public void btnClick(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> button= driver.findElements(By.cssSelector(("[type=button]")));
        Actions action =new Actions(driver);
        action.doubleClick(button.get(1)).perform();
        action.contextClick(button.get(2)).perform(); // for mousehover replace contextClick/ doubleclick by obj.moveToElement
        action.click(button.get(3)).perform();

        String actual_message=driver.findElement(By.id("doubleClickMessage")).getText();
        String actual_message2=driver.findElement(By.id("rightClickMessage")).getText();
        String expected_message= "You have done a double click";
        Assert.assertTrue(actual_message.contains(expected_message));
        Assert.assertTrue(actual_message.contains(expected_message));
    }


    @Test
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
    @Test
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
    @Test
    public void acceptCookies() throws InterruptedException {
        driver.get("https://cleanerdhaka.ideascale.com/a/community/login");
       WebElement cookies = driver.findElement(By.xpath("//button[@class='btn btn-primary flex-fill json-link']"));
        Actions actions = new Actions(driver);
        actions.click(cookies).build().perform();
        Thread.sleep(1000);
    }
    @Test
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

    @Test
    public void selectDate() throws InterruptedException {
        driver.get("https://demoqa.com/date-picker");
        WebElement date= driver.findElement(By.id("datePickerMonthYearInput"));
        Thread.sleep(6000);
        date.sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
        Thread.sleep(5000);
        date.sendKeys("09/09/2023");
        Thread.sleep(5000);
        date.sendKeys(Keys.ENTER);
    }


   @Test
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
    @Test
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
    @Test
    public void windowHandleTab() throws InterruptedException {
        driver.get("https://demoqa.com/links");
        driver.findElement(By.id("simpleLink")).click();
        Thread.sleep(3000);
        ArrayList<String>windowHandle= new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandle.get(1));
        String childTab = driver.getTitle();
        System.out.println("New tab title assertion" +childTab);
        Assert.assertTrue(childTab.contains("ToolsQA"));
        boolean imagePresent = driver.findElement(By.xpath("//img[@alt='Selenium Online Training']")).isDisplayed();
        Assert.assertEquals(true,imagePresent);
        System.out.println("image status" + imagePresent);
        driver.close();

        driver.switchTo().window(windowHandle.get(0));
        String parentTab = driver.getTitle();
        System.out.println("parent title assertion" +parentTab);
        Assert.assertTrue(parentTab.contains("ToolsQA"));

    }
  @Test
    public void webTable(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement>allRows = driver.findElements(By.className("rt-tr"));
        System.out.println(allRows);

        int i = 0;
        for(WebElement row:allRows){
            List<WebElement>allColums = driver.findElements(By.className("rt-td"));

            for(WebElement col:allColums){
                i++;
                System.out.println("colums values ["+ i + "]"+col.getText());
            }
        }
    }
    @Test
    public void file() throws InterruptedException {
        driver.get("https://demoqa.com/upload-download");
        WebElement uploadFile = driver.findElement(By.id("uploadFile"));
        uploadFile.sendKeys("C:/Users/Arif/Downloads/sampleFile.jpeg");
        Thread.sleep(5000);
    }


    @Test
    public void takeScreenShot() throws IOException {
        driver.get("https://demoqa.com");
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);

    }

   @After
    public void closeBrowser(){

        driver.close();
    }
}