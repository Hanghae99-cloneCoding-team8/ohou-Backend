# ohou-Backend π 2021.12.13 ~ 2021.12.16</h4>

## νλ‘μ νΈ μκ°
μμ€ν± μΈνλ¦¬μ΄ νλ«νΌμΌλ‘μ λ€μν μΈνλ¦¬μ΄ μ½νμΈ λ₯Ό μ κ³΅νλ μ€λμ μ§μ ν΄λ‘  μ½λ©νμ¬ μμ μ»€λ¨Έμ€ μ¬μ΄νΈμ μ£Όμ κΈ°λ₯λ€μ κ΅¬νν΄ λ³΄μλ€. π 


## λ°±μλ μνμμ

- DB μ€ν€λ§ μ€κ³
- μ€λμ μ§ μν λ°μ΄ν° ν¬λ‘€λ§ λ° DB κ΅¬μΆ(AWS RDS)
- API μ€κ³ λ° λΉμ¦λμ€ λ‘μ§ κ΅¬ν
- CORS μ€μ 
- Swagger μ€μ 
- End-to-End Test
- AWS EC2 λ°°ν¬
<hr>



## μμ΄μ΄ νλ μ

[![Figma](https://user-images.githubusercontent.com/93498724/146488344-782d62c7-c010-47e2-83cb-5be70cfc4a91.png)](https://www.figma.com/file/fFlzAvcm2FpFSlUiOPCLu4/7μ‘°-μ€λμ-μ§?node-id=0%3A1)
<hr>


## ERD μ€κ³

![Untitled (4)](https://user-images.githubusercontent.com/22443546/146488325-784be331-4bf5-4372-bac8-ff2efacf8a56.png)
<hr>


## API μ€κ³

![api img](https://user-images.githubusercontent.com/93498724/146489080-5c731fd0-69c3-4e4b-b07a-14ca85e52875.png)

<hr>


## Back-End 


### νμ μκ°

<h3 align="center"><b>π¨π»βπ€βπ¨π» Members π¨π»βπ€βπ¨π»</b></h3>
<br>
<table align="center">
    <tr>
        <td align="center">
        <a href="https://github.com/Zabee52"><img src="https://img.shields.io/badge/κΉμ©λΉ-000AFF?style=λ±μ§λͺ¨μ&logo=λ‘κ³ &logoColor=white"/></a>
        </td>
        <td align="center">
        <a href="https://github.com/SeongBeomKo"><img src="https://img.shields.io/badge/κ³ μ±λ²-2DDC88?style=λ±μ§λͺ¨μ&logo=λ‘κ³ &logoColor=black"/></a>
        </td>
        <td align="center">
        <a href="https://github.com/sy0713"><img src="https://img.shields.io/badge/μ΅μμ-D77EE9?style=λ±μ§λͺ¨μ&logo=λ‘κ³ &logoColor=white"/></a>
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

### κΈ°μ  μ€ν
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

1.  restTemplateμ Delete λ©μλ μ¬μ©μ, νμ€νΈ μΌμ΄μ€κ° κ³μ μ€ν¨

*μ€λ₯ μ½λ*

```java
restTemplate.delete("/api/products/reviews/" + commentId, request);
```

**μ€λ₯ λ°μ μ΄μ **: restTemplate.deleteμ μ¬μ©νλ©΄ request κ°μ²΄λ₯Ό λ§€κ°λ³μλ‘ λ°μ μκ° μμ΄μ κ³μ μλ¬κ° λ¬λ€.
μλ μ½λλ₯Ό λ³΄λ©΄ request κ°μ²΄λ₯Ό λ°μ μ μλ λ©μ μλ€.

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

*μμ μ½λ*

```java
ResponseEntity<String> resp = restTemplate.exchange("/api/products/reviews/" + commentId, HttpMethod.DELETE, request, String.class);
```

exchange λ©μλλ₯Ό μ¬μ©νμ¬ delete μμ²­μ΄ requestλ₯Ό λ°μ μ μλλ‘ μ€μ ν΄μ£Όμ΄μ ν΄κ²° νλ€.

<br>

2. Jsop μ μ¬μ©νμ¬ μ€λμ μ§ μΉ ν¬λ‘€λ§ μ€ν¨

**μ€λ₯ λ°μ μ΄μ **: μ μ  μΉ νμ΄μ§κ° μλ, λ¬΄ν μ€ν¬λ‘€ λ±μ κΈ°λ₯μ΄ κ΅¬νλ λμ  μΉ νμ΄μ§λ Jsoup μΌλ‘ ν¬λ‘€λ§μ΄ νλ€λ€. 
<br>

**μ€λ₯ ν΄κ²°**: Selenium μ μ¬μ©νλ€. <br>
λν μ ν μμΈ μ λ³΄λ₯Ό ν¬λ‘€λ§ νκΈ° μν΄μλ, μ§μ  μν μμΈ νμ΄μ§λ₯Ό νλνλ λ€μ΄κ°μ ν¬λ‘€λ§ ν΄μμΌ νλ€.<br>
μ΄ λ, μν URLμ λ¨Όμ  ν¬λ‘€λ§ ν΄μ¨ ν, driver.get(); λ©μλλ‘ μ μνκ³ , driver.close(); λ©μλλ‘ μ μ ν΄μ λ₯Ό ν΄μ£Όμλ€.

<hr>
