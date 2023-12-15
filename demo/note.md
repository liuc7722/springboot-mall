1. 
* 老師的作法，習慣在response時附上code和message兩種資料，因此通常會重新寫一個class，並繼承BaseResponseModel
    比如要查詢所有商品時，從資料庫取得商品資料，但我們不return此資料結構
    而是再建立一個class ProductResponseModel並繼承BaseResponse

    裡面就包含code, message, ProductModel三種資料，最後return 這個 class

* 查詢XX時若成功就要回傳XX給前端，若失敗也要回傳null給前端。
* 在dto裡成員的命名，和前端傳來的有關。
* 在rs.getXX("")引號裡的命名，和資料庫的欄位有關。

2. 後端程式結構
* controller : 前端接口
* service : 業務邏輯
* dao : 專門處理和資料庫交互
* model : 主要是單純定義一個資料模型。
* response : 設計一個新的型態回傳給前端，通常僅是繼承BaseResponseModel + 要給前端的資料而已。

3. 業務邏輯
* User 
    * 註冊，前端傳入dto，新增一筆用戶並回傳id，再由這筆id查詢用戶回傳完整資訊。
    * 登入，前端傳入dto(僅帳號密碼)，由這筆帳號查詢用戶，若無中斷請求；若有再判斷密碼是否相等，若不相等中斷請求，若有回傳完整用戶資訊給前端。

* Product
在查詢商品列表中，Controller和Dao就有不同的需求
Controller需要回傳的資料結構不只是一個`List<Product>`，還需要商品總數`total`等其他資訊
而Dao中的每個動作則是比較單一，查詢商品列表時就是回傳一個`List<Product>`
所以Controller還需要其他方法取得商品總數`total`，最後一併包在一個新的資料結構`Page`
至於為何裡面還要設一個泛型`Page<Product>`，只是為了方便閱讀及維護，以及裡面的`List<T> results`會有編譯器幫我們做檢查

* Order
訂單拆成兩個資料表，分為別
1. order，主要是紀錄user_id, total_price, order_date, shipping_address, status
2. order_item，紀錄order_id, product_id, quantity, price(此商品的總價)
order紀錄訂單的總資訊
order_item紀錄著所有訂單的單項，用以關聯**訂單**和**使用者**，當要查詢訂單細項時，便可使用`JOIN`來取得一筆訂單的細項，並注意price是因為當訂單成立後價格是不可修改的，所以要特別註記，比如若商品漲價，藉由order_item查詢訂單的價格時便不受影響，而若是商品改名，藉由order_item查詢的商品名稱則是改名後的名稱。 


* util package: 主要是為了回傳給前端需要更多的資訊，所以創建的class
* 創建訂單的邏輯
1. 傳入userId和CreateOrderRequest(內含地址和一個List)
2. Service層做業務邏輯，檢查使否有此用戶? 檢查後判斷每一個商品，是否有此商品? 此商品是否還有庫存? 若皆為是，則先更新商品庫存，再計算總價及準備要回傳給前端的商品細項，最後創建訂單和創建訂單細項，回傳給前端。
* 取得商品列表和訂單列表的邏輯:
當我們需要取得商品或訂單列表時，理論上只需要一個不需參數的getOrders()，不過當然不希望一次回傳給前端，否則會對資料庫造成影響，所以我們需要一些參數，包含查詢條件(category/search)、排序(orderBy/sort)、分頁(limit/offset)，這些東西是靠"邏輯"和"需求"來判斷需要加或否，比方說在訂單列表上，我們沒有設計什麼條件，就不需要判斷種類或是名字來搜尋(之後要做也是可以啦)，但是一定需要`userId`判斷哪個使用者的訂單，而查詢商品列表則相反；通常訂單都是由新到舊呈現給使用者，就用預設或寫死排序條件為日期；設計分頁上，一次要呈現幾筆訂單給使用者? 
要記住設計上要先假定數據會有上千上萬筆! 才會考慮要設計這麼多情況，當有上千上萬筆，就不可能一次呈現全部數據給前端，相當重要。

另外，在回傳列表時，通常不喜歡只有回傳一個List，而是用一個物件(類別)包一個List再加其他資訊，像是通常"至少"會回傳List和數量給前端，所以我們設計在`util`的`Page<T>`是一個通用的Response格式! 查詢商品或訂單列表都可以使用它。

另外，在設計Dao上，我們將相同的查詢條件都提煉了出來，名為`addFiltering`，注意，這是用在SQL語法中的`WHERE x=x`，把全部的查詢條件都寫在這一個方法裡，可以將相同的程式重複利用。

## Controller-Service-Dao

### Controller: 專門處理和前端相關的API，有關和前端資料傳輸都會在此實作
### Service: 專門處理業務邏輯，如驗證、判斷、過期等
### Dao: 專門處理和資料庫相關的功能，如SQL語法的增刪查改等，注意，Dao層不應該放業務邏輯


### model: 和資料表模型對應的資料模型
### Dto:   專門接收前端傳過來的資料模型
