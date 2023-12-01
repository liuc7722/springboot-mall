#### 安裝Maven
Windows比較麻煩，下載後放在適當位置，在環境變數設定
1. 下方的系統變數建立`MAVEN_HOME`變數，變數值為MAVEN路徑，如`Desktop\apache-maven-3.9.5-bin\apache-maven-3.9.5`
底下有資料夾`bin` `boot` `conf` `lib`、檔案`LICENSE` `NOTICE` `README`
2. 上方的使用者變數的`Path`變數內，新增上方的`bin`路徑
#### 測試安裝
`mvn -version`

#### 安裝VSCode extension
##### Community Server Connectors
用途:用來安裝與管理Tomcat後端伺服器用

##### 可安裝Extension Pack for Java VSCode
https://code.visualstudio.com/docs/languages/java
裡面已包含有6個套件

#### 建立VSCode新專案
Ctrl+Shift+P，輸入Maven:Create from acchetype
選擇maven-webapp
1.4
Group Id直接enter
artifact Id直接Enter
選擇一個合適的目錄即可
建立中下方終端機會詢問一些問題，直接Enter兩次即可
出現綠色的BUILD SUCCESS表示建立成功，右下角會提示藍色打開project
此project包含:
1. src目錄 -> main目錄 -> webapp
2. target目錄
3. pom.xml

> 如果出現The ternimal process failed to launch: Path to shell executalbe "cmd.exe" does not exist錯誤
訊息，請將`C:\Windows\System32`加到環境變數內`Path`變數內 => 1. Why(?) 2. 加到使用者or系統(?)

MAC直接使用HomeBrew安裝即可，不需修改環境變數

#### 修改pom.xml
1.7改成1.8

#### 新增Servlet套件
1. Ctrl+Shift+P，輸入maven，選擇新增相依套件
2. 輸入servlet然後enter
3. 選擇javax.servler
#### 新增mysql-connector套件
同上，輸入mysql選擇mysql-connector-java

> 1. 有新增相依套件的話，會需要重新編譯maven，此時儲存檔案，右下角有提示選擇藍色按鈕即可
> 2. 或者可以手動操作，Ctrl+Shift+P，輸入maven，選擇執行命令，選擇complie，等待終端機出現BUILD SUCCESS

> 我們已經安裝套件管理器了，所以我們不需要安裝selvet等相依，maven會去掃描這個xml，你需要什麼檔案，它會直接幫你下載

#### 安裝Tomcat Server
0. 先確定安裝VSCode Community Server Connerctor套件
1. 左下角有一些被折疊的欄位，找到SERVERS欄位
2. 展開後看到Community Server Connerctor(Stopped)
3. 右鍵點擊，選擇出現的唯一選項，"開始RSP Provider"，等待變成"Community Server Connector(Started)"
4. 再次點擊右鍵，選擇建立新伺服器"Create New Server"
5. Download Server，選擇Yes，選擇Apache-tomcat-9.0.41
6. 選擇Yes同意授權

#### 測試Tomcat
1. 右鍵Tomact 9.x(stopped)，選擇開始伺服器
2. 等待變成"Tomcat 9.x(started)"
3. 完成後，終端機跑完最後是Server startup in... 打開瀏覽器輸入localhost:8080可以看到Tomcat網頁

> src底下有webapp和main目錄
> webapp :存放一些Servlet設定檔
> main   :存放原始碼，利用套件的概念，通常是main/com

#### 開發一個Servlet
1. 在src/main目錄下新增java目錄(Servlet程式碼根目錄)
2. 新增package目錄com
3. 新增Servlet檔案Hello.java

4. Ctrl+Shift+P maven執行命令選擇package
5. 編譯及打包成功後，在target多了demo.war，可以部屬到網站Server(Tomcat)上 => 1. complie與package差異? 2. target為何

接下來要部屬到Server上了
web.xml是部屬的設定

#### 修改src/webapp/WEB-INF 目錄下的web.xml
#### 新增servlet設定
```xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <servlet>
    <servlet-name>Hello</servlet-name>
    <servlet-class>com.Hello</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>Hello</servlet-name>
    <url-pattern>/test</url-pattern>
  </servlet-mapping>
</web-app>
```

#### 部屬到Tomcat中
##### 打包專案
1. Ctrl+Shift+P，maven執行命令，package

#### 加入到Tomcat伺服器中
1. 右鍵點擊Tomcat 9.x(started)，選擇Add Deployment，再選擇target\demo.war 檔案
2. 選擇No(不修改參數)
3. 右鍵點擊Tomcat9.x(started)，選擇Publish Server(Full)


練習:
在登入頁實現登入錯誤時可以告知使用者:
1. 帳號不存在
2. 密碼錯誤