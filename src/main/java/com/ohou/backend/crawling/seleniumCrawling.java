package com.ohou.backend.crawling;

import com.ohou.backend.entity.ProductImages;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class seleniumCrawling {

    public static void main(String[] args) throws InterruptedException, AWTException {

        WebDriver driver;
        WebElement element;
        String url;


        // 크롬드라이버 쓸거임
        final String WEB_DRIVER_ID = "webdriver.chrome.driver";
        // 경로
        final String WEB_DRIVER_PATH = "C:/Users/User/Downloads/chromedriver_win32/chromedriver.exe";

        // 드라이브를 실행할 수 있도록 환경설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();
        // 크롬 드라이버 객체 생성
        driver = new ChromeDriver(options);

        // URL 설정

        url = "https://ohou.se/store/category?category=2_6&order=popular&affect_type=StoreHomeCategory&affect_id=1";
        driver.get(url);
        // 브라우저가 완전히 로딩될 때까지 시간을 기다려줌
        Thread.sleep(4000);

//        JavascriptExecutor jse = (JavascriptExecutor) driver;
//        jse.executeScript("window.scrollBy(0,14000)");
//        Thread.sleep(2000);

        List<WebElement> list = driver.findElements(By.cssSelector("div.virtualized-list.category-feed__content__content.row > div a"));   //URL
        for (WebElement item : list) {
            System.out.println(item.getAttribute("href"));
        }

        for (WebElement item : list) {
            driver = new ChromeDriver();
            driver.get(item.getAttribute("href"));
            Thread.sleep(3000);
            WebElement brand = driver.findElement(By.cssSelector("div.production-selling-header > h1 > p > a"));
            System.out.println("브랜드:" + brand.getText());

            WebElement title = driver.findElement(By.cssSelector("div.production-selling-header > h1 > div > span"));
            System.out.println("상품명:" + title.getText());

            WebElement discountRate = driver.findElement(By.cssSelector("span.production-selling-header__price__discount > span.number"));
            System.out.println("할인율:" + discountRate.getText());

            WebElement price = driver.findElement(By.cssSelector("span.production-selling-header__price__price > span.number"));
            System.out.println("가격:" + price.getText());

            List<WebElement> productImages = driver.findElements(By.cssSelector(" div.carousel.production-selling-cover-image.production-selling-overview__cover-image > ul > li img"));
            for (WebElement productImage : productImages) {
                System.out.println("상품이미지:" + productImage.getAttribute("src"));
            }

            List<WebElement> productDetailImages = driver.findElements(By.cssSelector("center > img"));
            if (productDetailImages.isEmpty()){
                productDetailImages = driver.findElements(By.cssSelector("div.production-selling__detail__content.col-12.col-lg-8 > div > section:nth-child(3) > div > div.production-selling-description__content > div > center > table > tbody > tr  img"));
            }
            for (WebElement productDetailImage : productDetailImages) {
                System.out.println("상품소개이미지:" + productDetailImage.getAttribute("src"));
            }
            driver.close();
        }
    }
}

//        for (WebElement item : list) {
//            System.out.println(item.getAttribute("href"));
//            driver.get(item.getAttribute("href"));
//            WebElement title = driver.findElement(By.cssSelector("div.production-selling-header > h1 > div > span"));
//            System.out.println("제목:"+ title);
//            Thread.sleep(4000);






