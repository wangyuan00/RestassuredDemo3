import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class BaiduTest {
//    一个简单的get请求，去验证http请求是否存在
   /* @Test
    public void testGetHtml() {
        given()
                .log().all().get("http://www.baidu.com")
                .then().log().all().statusCode(200);
    }*/
    @Test
    public void testMp3(){
//        输入的数据
        given()
                .queryParam("wd","mp3")
//        触发的条件
        .when()
                .get("http://www.baidu.com/s")
//        断言
         .then()
                .log().all()
                .statusCode(200);

    }

}
