# Backend
0. 資料庫格式 : 
* Database : my_system
* 使用者(User):
    * 
1. 老師的作法，習慣在response時附上code和message兩種資料，因此通常會重新寫一個class，並繼承BaseResponseModel

比如要查詢所有商品時，從資料庫取得商品資料，但我們不return此資料結構
而是再建立一個class ProductResponseModel並繼承BaseResponse

裡面就包含code, message, ProductModel三種資料，最後return 這個 class

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