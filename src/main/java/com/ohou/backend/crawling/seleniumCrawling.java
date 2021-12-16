package com.ohou.backend.crawling;

import com.ohou.backend.entity.*;
import com.ohou.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class seleniumCrawling {
    private final ProductRepository productRepository;
    private final ProductImagesRepository productImagesRepository;
    private final ProductDetailImagesRepository productDetailImagesRepository;
    private final OptionRepository optionRepository;
    private final OptionDetailsRepository optionDetailsRepository;

    public void test() throws InterruptedException, AWTException, IOException {
        WebElement element;

        // 크롬드라이버 쓸거임
        final String WEB_DRIVER_ID = "webdriver.chrome.driver";
        // 경로
        final String WEB_DRIVER_PATH = "C:/chromedriver.exe";

        // 드라이브를 실행할 수 있도록 환경설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();

        // URL 설정
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("2_6");
        categoryList.add("28");
        categoryList.add("21");
        categoryList.add("1");
        categoryList.add("8");

        for(String category: categoryList){
            String url = "https://ohou.se/store/category?affect_type=StoreHamburger&category=" + category;
            // 크롬 드라이버 객체 생성
            WebDriver driver = new ChromeDriver(options);
            driver.get(url);
            // 브라우저가 완전히 로딩될 때까지 시간을 기다려줌
            Thread.sleep(4000);

            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,14000)");
            Thread.sleep(2000);
            jse.executeScript("window.scrollBy(0,14000)");
            Thread.sleep(2000);

            List<WebElement> list = driver.findElements(By.cssSelector("div.virtualized-list.category-feed__content__content.row > div a"));   //URL
//            for (WebElement item : list) {
//                System.out.println(item.getAttribute("href"));
//            }

            for (WebElement item : list) {
                driver = new ChromeDriver();
                driver.get(item.getAttribute("href"));
                Thread.sleep(3000);

                WebElement brand = driver.findElement(By.cssSelector("div.production-selling-header > h1 > p > a"));
                System.out.println("브랜드:" + brand.getText());

                WebElement title = driver.findElement(By.cssSelector("div.production-selling-header > h1 > div > span"));
                System.out.println("상품명:" + title.getText());

                WebElement discountRate = null;
                try{
                    discountRate = driver.findElement(By.cssSelector("span.production-selling-header__price__discount > span.number"));
                } catch(Exception e){
                    continue;
                }
                System.out.println("할인율:" + discountRate.getText());

                WebElement price = driver.findElement(By.cssSelector("span.production-selling-header__price__price > span.number"));
                System.out.println("가격:" + price.getText());

                List<WebElement> productImages = driver.findElements(By.cssSelector(" div.carousel.production-selling-cover-image.production-selling-overview__cover-image > ul > li img"));
                for (WebElement productImage : productImages) {
                    System.out.println("상품이미지:" + productImage.getAttribute("src").split("\\?")[0]);
                }

                List<WebElement> productDetailImages = driver.findElements(By.cssSelector("center > img"));
                if (productDetailImages.isEmpty()){
                    productDetailImages = driver.findElements(By.cssSelector("div.production-selling__detail__content.col-12.col-lg-8 > div > section:nth-child(3) > div > div.production-selling-description__content > div > center > table > tbody > tr  img"));
                }
                for (WebElement productDetailImage : productDetailImages) {
                    System.out.println("상품소개이미지:" + productDetailImage.getAttribute("src"));
                }

                CategoryEnum catTxt = null;
                switch(category){
                    case "2_6":
                        catTxt = CategoryEnum.CHRISTMAS;
                        break;
                    case "28":
                        catTxt = CategoryEnum.WINTER;
                        break;
                    case "21":
                        catTxt = CategoryEnum.NECESSITY;
                        break;
                    case "1":
                        catTxt = CategoryEnum.FABRIC;
                        break;
                    case "8":
                        catTxt = CategoryEnum.PET;
                        break;
                }

                Product product = Product.builder()
                        .title(title.getText())
                        .brand(brand.getText())
                        .price(Integer.parseInt(price.getText().replaceAll(",","")))
                        .discountRate(Integer.parseInt(discountRate.getText()))
                        .category(catTxt)
                        .build();

                product = productRepository.save(product);
                System.out.println("product : " + product);

                List<ProductImages> productImagesList = new ArrayList<>();
                for(WebElement webElement: productImages){
                    productImagesList.add(ProductImages.builder()
                            .imgSrc(webElement.getAttribute("src").split("\\?")[0])
                            .product(product)
                            .build());
                }
                productImagesRepository.saveAll(productImagesList);

                List<ProductDetailImages> productDetailImagesList = new ArrayList<>();
                if(productDetailImages.size() > 0){
                    for(WebElement webElement: productDetailImages){
                        productDetailImagesList.add(ProductDetailImages.builder()
                                .imgSrc(webElement.getAttribute("src"))
                                .product(product)
                                .build());
                    }
                }
                productDetailImagesRepository.saveAll(productDetailImagesList);

                OptionEntity color = OptionEntity.builder().OptionName("컬러").product(product).build();
                color = optionRepository.save(color);
                List<OptionDetails> colorList = new ArrayList<>();
                colorList.add(OptionDetails.builder().detail("빨강").option(color).build());
                colorList.add(OptionDetails.builder().detail("파랑").option(color).build());
                colorList.add(OptionDetails.builder().detail("노랑").option(color).build());
                colorList.add(OptionDetails.builder().detail("초록").option(color).build());
                colorList.add(OptionDetails.builder().detail("보라").option(color).build());
                optionDetailsRepository.saveAll(colorList);

                OptionEntity size = OptionEntity.builder().OptionName("사이즈").product(product).build();
                size = optionRepository.save(size);
                List<OptionDetails> sizeList = new ArrayList<>();
                sizeList.add(OptionDetails.builder().detail("S").option(size).build());
                sizeList.add(OptionDetails.builder().detail("M").option(size).build());
                sizeList.add(OptionDetails.builder().detail("L").option(size).build());
                sizeList.add(OptionDetails.builder().detail("XL").option(size).build());
                optionDetailsRepository.saveAll(sizeList);

                /*
                    private String imgSrc;
                    @ManyToOne(fetch = FetchType.LAZY)
                    @JoinColumn(nullable = false)
                    private Product product;
                 */

                driver.close();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException, AWTException, IOException {

        WebElement element;

        // 크롬드라이버 쓸거임
        final String WEB_DRIVER_ID = "webdriver.chrome.driver";
        // 경로
        final String WEB_DRIVER_PATH = "C:/chromedriver.exe";

        // 드라이브를 실행할 수 있도록 환경설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();

        // URL 설정
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("2_6");
        categoryList.add("28");
        categoryList.add("21");
        categoryList.add("1");
        categoryList.add("8");

        for(String category: categoryList){
            String url = "https://ohou.se/store/category?affect_type=StoreHamburger&category=" + category;
            // 크롬 드라이버 객체 생성
            WebDriver driver = new ChromeDriver(options);
            driver.get(url);
            // 브라우저가 완전히 로딩될 때까지 시간을 기다려줌
            Thread.sleep(4000);

            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,14000)");
            Thread.sleep(2000);
            jse.executeScript("window.scrollBy(0,14000)");
            Thread.sleep(2000);

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

                WebElement discountRate = null;
                try{
                    /*
                    span.production-selling-header__price__discount > span.number
                     */
                    discountRate = driver.findElement(By.cssSelector("span.production-selling-header__price__discount > span.number"));
                } catch(Exception e){
                    continue;
                }
                System.out.println("할인율:" + discountRate.getText());

                WebElement price = driver.findElement(By.cssSelector("span.production-selling-header__price__price > span.number"));
                System.out.println("가격:" + price.getText());

                List<WebElement> productImages = driver.findElements(By.cssSelector(" div.carousel.production-selling-cover-image.production-selling-overview__cover-image > ul > li img"));
                for (WebElement productImage : productImages) {
                    System.out.println("상품이미지:" + productImage.getAttribute("src").split("\\?")[0]);
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
}

//        for (WebElement item : list) {
//            System.out.println(item.getAttribute("href"));
//            driver.get(item.getAttribute("href"));
//            WebElement title = driver.findElement(By.cssSelector("div.production-selling-header > h1 > div > span"));
//            System.out.println("제목:"+ title);
//            Thread.sleep(4000);






