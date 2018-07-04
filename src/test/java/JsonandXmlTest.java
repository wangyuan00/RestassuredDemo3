import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class JsonandXmlTest {
    /*json文件不支持 ** 查找*/
    @Test
    public void testerhomeJson() {
        /*json 需使用预定义的解析器解析*/
        RestAssured.registerParser("application/octet-stream", Parser.JSON);
        when().get("http://127.0.0.1:8080/testerhome.json")
                .then()
                .body("store.book[0].price", equalTo(8.95f))
                .body("store.book.author", hasItems("Nigel Rees", "Evelyn Waugh", "Herman Melville", "J. R. R. Tolkien"));

    }
    @Test
    public void testXML() {
        when().get("http://127.0.0.1:8080/testerhome.xml")
                .then()
                .body("shopping.category.item[0].name", equalTo("Chocolate"))
                .body("shopping.category.item.size()", equalTo(5))
                //查找shopping.category下的type == groceries数量；
                .body("shopping.category.findAll { it.@type == 'groceries' }.size()", equalTo(1))
                //查找price=20的商品名称是什么。price不是属性不用加@
                .body("shopping.category.item.findAll { it.price == 20 }.name", equalTo("Coffee"))
                //查找所有元素里面price=20的商品名称是什么
                .body("**.findAll { it.price == 20 }.name", equalTo("Coffee"));
    }
}
