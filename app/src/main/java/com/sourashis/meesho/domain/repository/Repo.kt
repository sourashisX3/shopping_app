package com.sourashis.meesho.domain.repository

import android.net.Uri
import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.BannersDataModel
import com.sourashis.meesho.domain.models.CartDataModel
import com.sourashis.meesho.domain.models.CategoryDataModel
import com.sourashis.meesho.domain.models.ProductsDataModel
import com.sourashis.meesho.domain.models.UserData
import com.sourashis.meesho.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
    fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
    fun getUserById(uid: String): Flow<ResultState<UserDataParent>>
    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>>
    fun userProfileImage(uri: Uri): Flow<ResultState<String>>
    fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModel>>>
    fun getProductsInLimited(): Flow<ResultState<List<ProductsDataModel>>>
    fun getAllProducts(): Flow<ResultState<List<ProductsDataModel>>>
    fun getProductById(productId: String): Flow<ResultState<ProductsDataModel>>
    fun addToCart(cartDataModel: CartDataModel): Flow<ResultState<String>>
    fun addToFav(productsDataModel: ProductsDataModel): Flow<ResultState<String>>
    fun getAllFav(): Flow<ResultState<List<ProductsDataModel>>>
    fun getCart(): Flow<ResultState<List<CartDataModel>>>
    fun getAllCategories(): Flow<ResultState<List<CategoryDataModel>>>
    fun getCheckout(productId: String): Flow<ResultState<ProductsDataModel>>
    fun getBanners(): Flow<ResultState<List<BannersDataModel>>>
    fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductsDataModel>>>
    fun getAllSuggestedProducts(): Flow<ResultState<List<ProductsDataModel>>>

}