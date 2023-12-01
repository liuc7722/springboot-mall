
//註:示範bean的使用，老師將mysql設定檔的內容用一個class包起來，再變成bean給spring管理而已
//目的就是，要連線到mysql時可以減少很多語法
//還有更新資料庫設定檔時，只需來這邊修改即可

//另外就是，注意到要使用到這個MySqlConfigBean只有註冊驅動程式(Class.forName)和連接資料庫(conn=DriverManager.get..blabla)
//所以再寫一個BaseController.java，讓每個連線資料庫的Controller都繼承它即可
//以上，使用.properties設定檔和Spring Boot JDBC也能更方便解決
package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component //變成Bean
@Data      //自動補上getter/setter
public class MySqlConfigBean {
    @Value("${spring.datasource.url}")
    private String mysqlUrl;
    @Value("${spring.datasource.username}")
    private String mysqlUsername;
    @Value("${spring.datasource.password}")
    private String mysqlPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String mysqlDriverName; 
}
