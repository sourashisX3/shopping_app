package com.sourashis.meesho.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourashis.meesho.common.HomeScreenState
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
import com.sourashis.meesho.domain.useCase.GetProductByIdUseCase
import com.sourashis.meesho.domain.useCase.GetProductsInLimitUseCase
import com.sourashis.meesho.domain.useCase.GetSpecificCategoryItemsUseCase
import com.sourashis.meesho.domain.useCase.GetUserUseCase
import com.sourashis.meesho.domain.useCase.LoginUserUseCase
import com.sourashis.meesho.domain.useCase.UpdateUserDataUseCase
import com.sourashis.meesho.domain.useCase.UserProfileImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val getCheckoutUseCase: GetProductByIdUseCase,
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

            }
        }
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













