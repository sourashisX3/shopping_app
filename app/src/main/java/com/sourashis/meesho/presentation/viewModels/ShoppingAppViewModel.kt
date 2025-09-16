package com.sourashis.meesho.presentation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourashis.meesho.common.HomeScreenState
import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.domain.models.CartDataModel
import com.sourashis.meesho.domain.models.CategoryDataModel
import com.sourashis.meesho.domain.models.ProductsDataModel
import com.sourashis.meesho.domain.models.UserDataParent
import com.sourashis.meesho.domain.useCase.AddToCartUseCase
import com.sourashis.meesho.domain.useCase.AddToFavUseCase
import com.sourashis.meesho.domain.useCase.CreateUserUseCase
import com.sourashis.meesho.domain.useCase.GetAllCategoryUseCase
import com.sourashis.meesho.domain.useCase.GetAllFavUseCase
import com.sourashis.meesho.domain.useCase.GetAllProductUseCase
import com.sourashis.meesho.domain.useCase.GetAllSuggestedProductsUseCase
import com.sourashis.meesho.domain.useCase.GetBannerUseCase
import com.sourashis.meesho.domain.useCase.GetCartUseCase
import com.sourashis.meesho.domain.useCase.GetCategoryLimitUseCase
import com.sourashis.meesho.domain.useCase.GetCheckoutUseCase
import com.sourashis.meesho.domain.useCase.GetProductByIdUseCase
import com.sourashis.meesho.domain.useCase.GetProductsInLimitUseCase
import com.sourashis.meesho.domain.useCase.GetSpecificCategoryItemsUseCase
import com.sourashis.meesho.domain.useCase.GetUserUseCase
import com.sourashis.meesho.domain.useCase.LoginUserUseCase
import com.sourashis.meesho.domain.useCase.UpdateUserDataUseCase
import com.sourashis.meesho.domain.useCase.UserProfileImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


class ShoppingAppViewModel @Inject constructor(

    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getCategoryLimitUseCase: GetCategoryLimitUseCase,
    private val getProductsInLimitUseCase: GetProductsInLimitUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToFavUseCase: AddToFavUseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getSpecificCategoryItemsUseCase: GetSpecificCategoryItemsUseCase,
    private val getAllProductsUseCase: GetAllProductUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getCheckoutUseCase: GetCheckoutUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getAllSuggestedProductsUseCase: GetAllSuggestedProductsUseCase,

    ) : ViewModel() {

    private val _signupScreenState = MutableStateFlow(SignupScreenState())
    val signupScreenState = _signupScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpdateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _userProfileImageState = MutableStateFlow(UploadUserProfileImageScreenState())
    val userProfileImageState = _userProfileImageState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartScreenState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _getProductsByIdState = MutableStateFlow(GetProductsByIdState())
    val getProductsByIdState = _getProductsByIdState.asStateFlow()

    private val _addToFavState = MutableStateFlow(AddToFavScreenState())
    val addToFavState = _addToFavState.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getCheckoutState = MutableStateFlow(GetCheckoutState())
    val getCheckoutState = _getCheckoutState.asStateFlow()

    private val _getSpecificCategoryItemsState = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategoryItemsState = _getSpecificCategoryItemsState.asStateFlow()

    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()


    fun getSpecificCategoryItems(categoryId: String) {
        viewModelScope.launch {

            getSpecificCategoryItemsUseCase.getSpecificCategoryItems(categoryId).collect {
                when (it) {

                    is ResultState.Error -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                errorMessage = it.message,
                            )
                    }

                    ResultState.Loading -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }
        }
    }

    fun getCheckoutData(productId: String) {
        viewModelScope.launch {
            getCheckoutUseCase.getCheckoutUseCase(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }

        fun getCart() {
            viewModelScope.launch {
                getCartUseCase.getCart().collect {
                    when (it) {
                        is ResultState.Error -> {
                            _getCartState.value = _getCartState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                        }

                        is ResultState.Loading -> {
                            _getCartState.value = _getCartState.value.copy(
                                isLoading = true
                            )
                        }

                        is ResultState.Success -> {
                            _getCartState.value = _getCartState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase.getAllCategoriesUseCase().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase.getAllProducts().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllFav() {
        viewModelScope.launch {
            getAllFavUseCase.getAllFavUseCase().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun addToFav(productsDataModel: ProductsDataModel) {
        viewModelScope.launch {
            addToFavUseCase.addToFav(productsDataModel).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductByIdUseCase.getProductById(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getProductsByIdState.value = _getProductsByIdState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getProductsByIdState.value = _getProductsByIdState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getProductsByIdState.value = _getProductsByIdState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun addToCart(cartDataModel: CartDataModel) {
        viewModelScope.launch {
            addToCartUseCase.addToCart(cartDataModel).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    init {
        loadHomeScreenData()
    }

    fun loadHomeScreenData() {
        viewModelScope.launch {
            combine(
                getCategoryLimitUseCase.getCategoryLimited(),
                getCategoryLimitUseCase.getCategoryLimited(),
                getBannerUseCase.getBannerUseCase()
            ) { categoriesResult, productsResult, bannersResult ->
                when {
                    // Error
                    categoriesResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = categoriesResult.message)
                    }

                    productsResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = productsResult.message)
                    }

                    bannersResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = bannersResult.message)
                    }

                    // Success
                    categoriesResult is ResultState.Success && productsResult is ResultState.Success && bannersResult is ResultState.Success -> {
                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productsResult.data,
                            banners = bannersResult.data
                        )

                    }

                    else -> {
                        HomeScreenState(isLoading = true)
                    }
                }

            }.collect { state ->
                _homeScreenState.value = state
            }
        }
    }

    fun uploadUserProfileImage(uri: Uri) {
        viewModelScope.launch {
            userProfileImageUseCase.userProfileImageUseCase(uri).collect {
                when(it) {
                    is ResultState.Error -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


    fun updateUserData(userDataParent: UserDataParent) {
        // TODO: ****
    }
}


data class ProfileScreenState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val userData: UserDataParent? = null
)

data class SignupScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class LoginScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UpdateScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UploadUserProfileImageScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class AddToCartScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetProductsByIdState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductsDataModel? = null
)

data class AddToFavScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModel?> = emptyList()
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModel?> = emptyList()
)

data class GetCartState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CartDataModel?> = emptyList()
)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CategoryDataModel?> = emptyList()
)

data class GetCheckoutState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductsDataModel? = null
)

data class GetSpecificCategoryItemsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModel?> = emptyList()
)

data class GetAllSuggestedProductsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModel?> = emptyList()
)













