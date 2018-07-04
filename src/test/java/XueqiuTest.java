import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class XueqiuTest {
    @Test
    public void testSearch(){
        useRelaxedHTTPSValidation();
        given()
                .log().all()
                .queryParam("code","sogo")
                .header("Cookie","device_id=561b0b08524ef9a75e0cbef551a63631; aliyungf_tc=AQAAALAdgFtH0AgA4o14agquHGkByEze; xq_a_token.sig=_pB0kKy3fV9fvtvkOzxduQTrp7E; xq_r_token.sig=lOCElS5ycgbih9P-Ny3cohQ-FSA; s=ek12c71yku; xq_a_token=d08b9e4f633206eaaf8fa7dff04bb5a92ab89bb1; xqat=d08b9e4f633206eaaf8fa7dff04bb5a92ab89bb1; xq_r_token=ce35566c88ea60ba1557874d4857386437e013a0; xq_token_expire=Fri%20Jul%2020%202018%2018%3A12%3A17%20GMT%2B0800%20(CST); xq_is_login=1; u=1762221509; Hm_lvt_1db88642e346389874251b5a1eded6e3=1529479986,1529549083,1529917909; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1529921539; _ga=GA1.2.337342844.1529479986; _gid=GA1.2.869250333.1529917909")
         .when()
                .get("https://xueqiu.com/stock/search.json")
         .then()
                .log().all()
                .statusCode(200)
                .body("stocks.code", hasItems("SOGO"))
                .body("stocks.name",hasItems("搜狗"))
                .body("stocks.find{it.code == 'SOGO'}.name",equalTo("搜狗"))
                .body("stocks.find{it.name == '搜狗'}.stock_id",equalTo(1029472));
    }
}
