import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import views.MainView;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.lowestOneBit;

import org.json.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainController mainController = new MainController();
        MainView mainView = new MainView(mainController);
        mainView.show();
    }

    public static void main(String[] args) throws Exception {
//        launch(args);

        // Get a random proxy from API
        String randomProxy = getProxy();

        System.out.println(randomProxy);

        // Create Chrome webdriver and use proxy
        WebDriver driver = createDriver(randomProxy);

        boolean createOK;

        // Create Twitter account and follow user
        Scanner input = new Scanner(System.in);
        System.out.print("amount of followers to add: ");
        int followAmount = input.nextInt();

        for (int i = 0; i < followAmount; i++) {
            do {
                createOK = createAccount(driver);
                if (createOK) {
                    followUser(driver, "pokerstars");
                    driver.close();
                    driver = createDriver(getProxy());
                } else {
                    driver.close();
                    driver = createDriver(getProxy());
                }
            } while (!createOK);
        }

        driver.close();
        System.exit(1);
    }

    public static String getProxy() throws Exception {

//        HttpResponse<JsonNode> response = Unirest.get("https://api.getproxylist.com/proxy?anonymity[]=high%20anonymity&anonymity[]=transparent&country[]=US&protocol[]=socks5").asJson();
//        JSONObject jo = (JSONObject) response.getBody().getObject();
//        StringBuilder Buildy = new StringBuilder();
//        Buildy.append(jo.get("ip").toString());
//        Buildy.append(":"+jo.get("port"));
//        String proxy = Buildy.toString();

        URL url = new URL("https://api.proxyscrape.com/?request=displayproxies&proxytype=http&timeout=5000&country=US&anonymity=elite&ssl=yes");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\n");
        }
        in.close();

        String proxys = content.toString();

        String result = null;
        Random rand = new Random();
        int n = 0;
        for (Scanner sc = new Scanner(proxys); sc.hasNext(); ) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }

        return result;
    }

    public static String randomString(int count) {

        String RANDOM_CHARACTER_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * RANDOM_CHARACTER_STRING.length());
            builder.append(RANDOM_CHARACTER_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static WebDriver createDriver(String randomProxy) {

        System.setProperty("webdriver.chrome.driver", "src/main/chromedriver");

        DesiredCapabilities dc;
        dc = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--proxy-server=http://" + randomProxy);
//        options.addArguments("headless");
        dc.setCapability(ChromeOptions.CAPABILITY, options);

        WebDriver driver = new ChromeDriver(dc);

        return driver;

    }

    public static String getConfCode(String email) throws Exception {

        HttpResponse<JsonNode> response = Unirest.get("http://lacedmail.com/api/mailbox/" + email + "@likmeballe.ga").asJson();
        JSONObject jo = (JSONObject) response.getBody().getObject();
        JSONArray ja = (JSONArray) jo.get("messages");
        JSONArray okk = (JSONArray) ja.getJSONArray(0);
        String message = okk.get(1).toString();
        int sub = message.indexOf(" ");
        String code = message.substring(0, sub);

        return code;

    }

    public static Boolean createAccount(WebDriver driver) throws Exception {
        String randName = randomString(6);
        String randPass = randomString(10);
        String randMail = randomString(8);

        driver.get("http://twitter.com/i/flow/signup");

        try {
            if(((ChromeDriver) driver).findElementByXPath("//*[contains(text(), 'No internet')]").isDisplayed()){
                return false;
            }
        } catch (WebDriverException we){

        }

        try {
            if(((ChromeDriver) driver).findElementByXPath("//*[contains(text(), 'This site can’t be reached')]").isDisplayed()){
                return false;
            }
        } catch (WebDriverException we){

        }

        WebDriverWait wait = new WebDriverWait(driver, 15);

        try{
            WebElement nameBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
            nameBox.sendKeys(randName);
        } catch (WebDriverException we) {
            System.out.println("Page not loading...restarting");
            return false;
        }

        wait = new WebDriverWait(driver, 10);

        WebElement useMailLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Use email instead')]")));
        useMailLink.click();

        WebElement mailBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        mailBox.sendKeys(randMail + "@likmeballe.ga");

        TimeUnit.SECONDS.sleep(2);

        try {
            if (((ChromeDriver) driver).findElementByXPath("//*[text()='Email has already been taken.']").isDisplayed()) {
                do{
                    mailBox.sendKeys(Keys.CONTROL + "a");
                    mailBox.sendKeys(Keys.DELETE);
                    randMail = randomString(8);
                    mailBox.sendKeys(randMail+"@likmeballe.ga");
                    TimeUnit.SECONDS.sleep(2);
                }while(((ChromeDriver) driver).findElementByXPath("//*[text()='Email has already been taken.']").isDisplayed());
            }
            TimeUnit.SECONDS.sleep(2);
        } catch (WebDriverException we){
            System.out.println("Email has not been taken.");
        }

        TimeUnit.SECONDS.sleep(2);

        WebElement nextLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Next')]")));

        try {
            nextLink.click();
        } catch (WebDriverException we){
            System.out.println("Next button is being slow. waiting 5 seconds to be sure.");
            Thread.sleep(5000);
            nextLink.click();
        }

        try {
            if (((ChromeDriver) driver).findElementByXPath("//*[text()='Step 2 of 5']").isDisplayed()) {
                WebElement nextLink6 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Next')]")));
                nextLink6.click();
            }
        } catch (WebDriverException we) {
            System.out.println("4 pages");
        }

        WebElement signupLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Sign up')]")));
        signupLink.click();

        TimeUnit.SECONDS.sleep(10);

        // Get confirmation code from email
        String code = getConfCode(randMail);

        TimeUnit.SECONDS.sleep(3);

        WebElement verCode = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("verfication_code")));
        TimeUnit.SECONDS.sleep(2);
        verCode.sendKeys(code);

        TimeUnit.SECONDS.sleep(2);

        WebElement nextLink2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Next')]")));
        nextLink2.click();

        WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        TimeUnit.SECONDS.sleep(2);
        password.sendKeys(randPass);

        TimeUnit.SECONDS.sleep(2);
        WebElement nextLink3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Next')]")));

        try {
            nextLink3.click();
        } catch (WebDriverException we){
            System.out.println("Next button is being slow. waiting 5 seconds to be sure.");
            Thread.sleep(5000);
            nextLink3.click();
        }


        Thread.sleep(5000);

        try {
            if (((ChromeDriver) driver).findElementByXPath("//*[contains(text(), 'temporarily limited')]").isDisplayed()) {
                System.out.println("Account limited. Retrying.");
                return false;
            }
        } catch (WebDriverException we){
            System.out.println("Account not limited.");
        }

        try {
            if (((ChromeDriver) driver).findElementByXPath("//*[contains(text(), 'Add a phone number')]").isDisplayed()) {
                System.out.println("Asked for phonenumber. Retrying.");
                return false;
            }
        } catch (WebDriverException we){
            System.out.println("Didn't ask for phonenumber.");
        }

        try {
            if (((ChromeDriver) driver).findElementByXPath("//*[contains(text(), 'Your account has been locked')]").isDisplayed()) {
                System.out.println("Account locked. Retrying.");
                return false;
            }
        } catch (WebDriverException we){
            System.out.println("Account not locked.");
        }

        WebElement skipLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Skip for now')]")));
        skipLink.click();

        WebElement skipLink2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Skip for now')]")));
        skipLink2.click();

        WebElement skipLink3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Skip for now')]")));
        skipLink3.click();

        WebElement nextLink4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Next')]")));
        nextLink4.click();

        WebElement skipLink4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Skip for now')]")));
        skipLink4.click();

        System.out.println("Account created");
        return true;

    }

    public static void followUser(WebDriver driver, String username) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        System.out.println("Getting user page");

        Thread.sleep(10000);
        driver.get("https://twitter.com/" + username);

        try {
            WebElement viewProfileLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Yes, view profile')]")));
            viewProfileLink.click();
        } catch (WebDriverException we){
            System.out.println("Didnt ask to view profile.");
        }

        System.out.println("Following user "+username);
        WebElement followLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Follow']")));
        followLink.click();

        Thread.sleep(2000);

    }


}
