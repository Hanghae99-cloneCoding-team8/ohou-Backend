# ohou-Backend 📆 2021.12.13 ~ 2021.12.16</h4>

## 프로젝트 소개
원스톱 인테리어 플랫폼으로서 다양한 인테리어 콘텐츠를 제공하는 오늘의 집을 클론 코딩하여 소셜 커머스 사이트의 주요 기능들을 구현해 보았다. 🏠


## 백엔드 수행작업

- DB 스키마 설계
- 오늘의 집 상품 데이터 크롤링 및 DB 구축(AWS RDS)
- API 설계 및 비즈니스 로직 구현
- CORS 설정
- Swagger 설정
- End-to-End Test
- AWS EC2 배포
<hr>



## 와이어 프레임

[![Figma](https://user-images.githubusercontent.com/93498724/146488344-782d62c7-c010-47e2-83cb-5be70cfc4a91.png)](https://www.figma.com/file/fFlzAvcm2FpFSlUiOPCLu4/7조-오늘의-집?node-id=0%3A1)
<hr>


## ERD 설계

![Untitled (4)](https://user-images.githubusercontent.com/22443546/146488325-784be331-4bf5-4372-bac8-ff2efacf8a56.png)
<hr>


## API 설계

![api img](https://user-images.githubusercontent.com/93498724/146489080-5c731fd0-69c3-4e4b-b07a-14ca85e52875.png)

<hr>


## Back-End 


### 팀원 소개

<h3 align="center"><b>👨🏻‍🤝‍👨🏻 Members 👨🏻‍🤝‍👨🏻</b></h3>
<br>
<table align="center">
    <tr>
        <td align="center">
        <a href="https://github.com/Zabee52"><img src="https://img.shields.io/badge/김용빈-000AFF?style=뱃지모양&logo=로고&logoColor=white"/></a>
        </td>
        <td align="center">
        <a href="https://github.com/SeongBeomKo"><img src="https://img.shields.io/badge/고성범-2DDC88?style=뱃지모양&logo=로고&logoColor=black"/></a>
        </td>
        <td align="center">
        <a href="https://github.com/sy0713"><img src="https://img.shields.io/badge/최석영-D77EE9?style=뱃지모양&logo=로고&logoColor=white"/></a>
        </td>
    </tr>
    <tr>
        <th width="25%" align="center">:spider_web: BACK-END
        </th>
        <th width="25%" align="center">:spider_web: BACK-END
        </th>
        <th width="25%" align="center">:spider_web: BACK-END 
        </th>
    </tr>
</table>
<br>

### 기술 스택
<img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white"></a>&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/MySQL-005C84?style=flat&logo=mysql&logoColor=white"></a>&nbsp;&nbsp;&nbsp; 
<img src="https://img.shields.io/badge/Springboot-47?style=flat&logo=Springboot&logoColor=white"/></a>&nbsp;&nbsp;&nbsp; 
<img src="https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white"/></a>&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=Swagger&logoColor=white"></a>&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/gradle-02303A?style=flat&logo=gradle&logoColor=white"></a>&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=flat&logo=amazonaws&logoColor=white"></a>&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white"></a>&nbsp;&nbsp;&nbsp;
<img src="https://img.shields.io/badge/Selenium-43B02A?style=flat&logo=Selenium&logoColor=white"></a>&nbsp;&nbsp;&nbsp;
<br>
<hr>

## Trouble Shooting

- restTemplate의 Delete 메소드 사용시, 테스트 케이스가 계속 실패

*오류 코드*

```java
restTemplate.delete("/api/products/reviews/" + commentId, request);
```

**오류 발생 이유**: restTemplate.delete을 사용하면 request 객체를 매개변수로 받을 수가 없어서 계속 에러가 났다.
아래 코드를 보면 request 객체를 받을 수 있는 메소득 없다.

```java

	/**
	 * Delete the resources at the specified URI.
	 * <p>
	 * URI Template variables are expanded using the given URI variables, if any.
	 * <p>
	 * If you need to assert the request result consider using the
	 * {@link TestRestTemplate#exchange exchange} method.
	 * @param url the URL
	 * @param urlVariables the variables to expand in the template
	 * @see RestTemplate#delete(java.lang.String, java.lang.Object[])
	 */
	public void delete(String url, Object... urlVariables) {
		this.restTemplate.delete(url, urlVariables);
	}

	/**
	 * Delete the resources at the specified URI.
	 * <p>
	 * URI Template variables are expanded using the given map.
	 * <p>
	 * If you need to assert the request result consider using the
	 * {@link TestRestTemplate#exchange exchange} method.
	 * @param url the URL
	 * @param urlVariables the variables to expand the template
	 * @see RestTemplate#delete(java.lang.String, java.util.Map)
	 */
	public void delete(String url, Map<String, ?> urlVariables) {
		this.restTemplate.delete(url, urlVariables);
	}

	/**
	 * Delete the resources at the specified URL.
	 * <p>
	 * If you need to assert the request result consider using the
	 * {@link TestRestTemplate#exchange exchange} method.
	 * @param url the URL
	 * @see RestTemplate#delete(java.net.URI)
	 */
	public void delete(URI url) {
		this.restTemplate.delete(applyRootUriIfNecessary(url));
	}
```

*수정코드*

```java
ResponseEntity<String> resp = restTemplate.exchange("/api/products/reviews/" + commentId, HttpMethod.DELETE, request, String.class);
```

exchange 메소드를 사용하여 delete 요청이 request를 받을 수 있도록 설정해주어서 해결 했다.

<br>

<hr>
