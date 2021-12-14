//package com.ohou.backend.crawling;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//
//@Service
//public class dataCrawling {
//    private static String ohousURL = "https://ohou.se/store";
//
//    @PostConstruct  //시작하자 마자 실행
//    public void getOhous() throws IOException, InterruptedException {
//        Document doc = Jsoup.connect(ohousURL)
//                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
//                .header("scheme", "https")
//                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                .header("accept-encoding", "gzip, deflate, br")
//                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
//                .header("cache-control", "no-cache")
//                .header("pragma", "no-cache")
//                .header("upgrade-insecure-requests", "1")
//                .get();
//
////        Thread.sleep(1000);
//        Elements linkList = doc.select(".production-item >.production-item__overlay");
//        //.container store-index-section store-index-product-list > div.virtualized-list row >
//
//        for (int i = 0; i < linkList.size(); i++) {
//            //브랜드
//            String href = linkList.get(i).attr("href");
//            String detailUrl = "https://ohou.se/" + href;
//            doc = Jsoup.connect(detailUrl).get();   //doc = 디테일 페이지
//
//            Elements brand = doc.select("div.production-selling-overview__content col-12 col-md-6 col-lg-5 > div.production-selling-header > h1.production-selling-header__title > p.production-selling-header__title__brand-wrap > a.production-selling-header__title__brand");
//            System.out.println("브랜드명: " + brand.text());
//            //col-6 col-md-3 product-item-wrap
//            // > production-item
//            // > production-item__content
//            // > production-item__header > production-item__header__brand (브랜드)
//            // > production-item__header > production-item__header__name(이름)
//            // > production-item-price > production-item-price__rate(할인율)
//            // > production-item-price > production-item-price__price(가격)
//            // -> _price 에 "외" 가 붙으면 크롤링 하지 않는다.
//            // category, images
//
//        }
////        System.out.println(doc);
//    }
//}
