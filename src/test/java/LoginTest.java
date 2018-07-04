import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest {
    public static String code;
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;
//  @BeforeClass在之前执行
    @BeforeClass
    public static void Login(){
//  信任本地代理
        useRelaxedHTTPSValidation();
//  设置全局代理
        RestAssured.proxy("127.0.0.1",7778);
//  全局请求地址
        RestAssured.baseURI = "https://api.xueqiu.com";
//  全局cookie
        RestAssured.given().cookie("testerhome_id","hogwarts");
//  全局header
        RestAssured.given().header("User-Agent","XueqiuTest Android 11.0.1");
//  build一个requestSpecification出来复用
        requestSpecification = new RequestSpecBuilder().build();
        requestSpecification.port(80);
        requestSpecification.cookie("testerhome_id", "hogwarts");
        requestSpecification.header("User-Agent", "XueqiuTest Android 11.0.1");

        responseSpecification = new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);
        responseSpecification.body("error",equalTo(1));

//        LoginXueQiu();
    }
    /*登录*/
    public static void LoginXueQiu(){
        code = given()
                .header("User-Agent","XueqiuTest Android 11.0.1")
                .queryParam("_t","1HUAWEIb16c07af9c44cae8eb633568f31fad00.8722213637.1530602942975.1530603044631")
                .queryParam("_s","2b311a")
                .cookie("u","8722213637")
                .cookie("xq_a_token","1d5828d710564f61d99afc592d30e5b468b25d45")
                .formParam("grant_type","password")
                .formParam("telephone","17710878939")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1HUAWEIb16c07af9c44cae8eb633568f31fad00")
                .when()
                .post("https://api.xueqiu.com/provider/oauth/token")
                .then()
                .log().all()
                .statusCode(400)
                .body("error_code",equalTo("20082"))
//  通过extract()导出想要的值，将其变量化，用于下一个接口
                .extract().path("error_code");
        System.out.println(code);
    }
    @Test
    /*导出path值*/
        public void testLogin(){
        useRelaxedHTTPSValidation();
//        设置全局代理
        RestAssured.proxy("127.0.0.1",7778);
            String code = given()
                .header("User-Agent","XueqiuTest Android 11.0.1")
                .queryParam("_t","1HUAWEIb16c07af9c44cae8eb633568f31fad00.8722213637.1530602942975.1530603044631")
                .queryParam("_s","2b311a")
                .cookie("u","8722213637")
                .cookie("xq_a_token","1d5828d710564f61d99afc592d30e5b468b25d45")
                .formParam("grant_type","password")
                .formParam("telephone","17710878939")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1HUAWEIb16c07af9c44cae8eb633568f31fad00")
                .when()
                    .post("/provider/oauth/token")
                .then()
                    .log().all()
                    .statusCode(400)
                    .body("error_code",equalTo("20082"))
//                通过extract()导出想要的值，将其变量化，用于下一个接口
                    .extract().path("error_code");
        System.out.println(code);
    }
    @Test
    /*导出Response值*/
    public void testLogin2(){
        useRelaxedHTTPSValidation();
        io.restassured.response.Response response = given().proxy("127.0.0.1",7778)
                .header("User-Agent","XueqiuTest Android 11.0.1")
                .queryParam("_t","1HUAWEIb16c07af9c44cae8eb633568f31fad00.8722213637.1530602942975.1530603044631")
                .queryParam("_s","2b311a")
                .cookie("u","8722213637")
                .cookie("xq_a_token","1d5828d710564f61d99afc592d30e5b468b25d45")
                .formParam("grant_type","password")
                .formParam("telephone","17710878939")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1HUAWEIb16c07af9c44cae8eb633568f31fad00")
                .when()
                    .post("https://api.xueqiu.com/provider/oauth/token")
                .then()
                    .log().all()
                    .statusCode(400)
//                    .body("error_code",equalTo("20082"))
                .extract().response();
        System.out.println(response);
    }
    @Test
    /*发送json请求*/
    public void testPostJson(){
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a",1);
        map.put("b","testerhome");
        map.put("array",new String[]{"111","222"});
        given()
//    requestSpecification复用,默认port cookie header
                .spec(requestSpecification)
//  指明我要发送的是json类型,不支持 .contentType(ContentType.XML)
                .contentType(ContentType.JSON)
                .body(map)
         .when()
                .post("http://www.baidu.com")
         .then()
                .log().all()
                .spec(responseSpecification)
          /*      断言时间不得小于1000ms，一般接口返回是十几ms，大一点最多到几百ms，到1000ms的很少
                注意单位要大写*/
                .time(lessThan(1000L));


    }
}
