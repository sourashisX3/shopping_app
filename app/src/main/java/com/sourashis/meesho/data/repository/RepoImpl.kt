package com.sourashis.meesho.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sourashis.meesho.common.ADD_TO_CART
import com.sourashis.meesho.common.ADD_TO_FAV
import com.sourashis.meesho.common.BANNERS
import com.sourashis.meesho.common.CATEGORIES
import com.sourashis.meesho.common.PRODUCT_COLLECTION
import com.sourashis.meesho.common.PRODUCT_COLLECTION
import com.sourashis.meesho.common.ResultState
import com.sourashis.meesho.common.USER_CART
import com.sourashis.meesho.common.USER_COLLECTION
import com.sourashis.meesho.common.USER_FAV
import com.sourashis.meesho.domain.models.BannersDataModel
import com.sourashis.meesho.domain.models.CartDataModel
import com.sourashis.meesho.domain.models.CategoryDataModel
import com.sourashis.meesho.domain.models.ProductsDataModel
import com.sourashis.meesho.domain.models.UserData
import com.sourashis.meesho.domain.models.UserDataParent
import com.sourashis.meesho.domain.repository.Repo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    var firebaseAuth: FirebaseAuth,
    var firebaseFirestore: FirebaseFirestore
) : Repo {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        firebaseFirestore
                            .collection(USER_COLLECTION)
                            .document(result.result.user?.uid.toString())
                            .set(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(ResultState.Success("Registration Successful and added to firestore."))
                                } else {
                                    if (it.exception != null) {
                                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                                    }
                                }
                            }
                        trySend(ResultState.Success("Registration Successful"))
                    } else {
                        if (result.exception != null) {
                            trySend(ResultState.Error(result.exception?.localizedMessage.toString()))
                        }
                    }
                }
            // closing firebase
            awaitClose {
                close()
            }
        }

    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        trySend(ResultState.Success("Login Successful"))
                    } else {
                        if (result.exception != null) {
                            trySend(ResultState.Error(result.exception?.localizedMessage.toString()))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun getUserById(uid: String): Flow<ResultState<UserDataParent>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(USER_COLLECTION).document(uid).get()
            .addOnCompleteListener { res ->
                if (res.isSuccessful) {
                    val data = res.result.toObject(UserData::class.java)!!
                    val userDataParent = UserDataParent(res.result.id, data)
                    trySend(ResultState.Success(userDataParent))
                } else {
                    if (res.exception != null) {
                        trySend(ResultState.Error(res.exception?.localizedMessage.toString()))
                    }
                }
            }
        awaitClose {
            close()
        }
    }

    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(USER_COLLECTION)
                .document(userDataParent.nodeId)
                .update(userDataParent.userData.toMap())
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        trySend(ResultState.Success("User data update successful"))
                    } else {
                        if (result.exception != null) {
                            trySend(ResultState.Error(result.exception?.localizedMessage.toString()))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)

        FirebaseStorage.getInstance().reference.child("userProfileImage/${System.currentTimeMillis()} + ${firebaseAuth.currentUser?.uid}")
            .putFile(uri/* ?: Uri.EMPTY TODO*/).addOnCompleteListener { res ->
                res.result.storage.downloadUrl.addOnSuccessListener { imageUri ->
                    trySend(ResultState.Success(imageUri.toString()))
                }
                if (res.exception != null) {
                    trySend(ResultState.Error(res.exception?.localizedMessage.toString()))
                }
            }
        awaitClose {
            close()
        }
    }

    override fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(CATEGORIES).limit(7).get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    val categories =
                        queryDocumentSnapshots.documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(CategoryDataModel::class.java)
                        }
                    trySend(ResultState.Success(categories))
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getProductsInLimited(): Flow<ResultState<List<ProductsDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).limit(10).get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(ProductsDataModel::class.java)?.apply {
                        productId = documentSnapshot.id
                    }
                }
                trySend(ResultState.Success(products))
            }.addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getAllProducts(): Flow<ResultState<List<ProductsDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(PRODUCT_COLLECTION).get().addOnSuccessListener {
            val products = it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(ProductsDataModel::class.java)?.apply {
                    productId = documentSnapshot.id
                }
            }
            trySend(ResultState.Success(products))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getProductById(productId: String): Flow<ResultState<ProductsDataModel>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get()
                .addOnSuccessListener {
                    val product = it.toObject(ProductsDataModel::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun addToCart(cartDataModel: CartDataModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
            .collection(USER_CART).add(cartDataModel).addOnSuccessListener {
                trySend(ResultState.Success("Products added to the cart"))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun addToFav(productsDataModel: ProductsDataModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
                .collection(USER_FAV).add(productsDataModel).addOnSuccessListener {
                    trySend(ResultState.Success("Products added to fav"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getAllFav(): Flow<ResultState<List<ProductsDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
            .collection(USER_FAV).get().addOnSuccessListener {
                val fav = it.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(ProductsDataModel::class.java)
                }
                trySend(ResultState.Success(fav))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getCart(): Flow<ResultState<List<CartDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
            .collection(USER_CART)
            .get().addOnSuccessListener {
                val cart = it.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(CartDataModel::class.java)?.apply {
                        cartId = documentSnapshot.id
                    }
                }
                trySend(ResultState.Success(cart))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(CATEGORIES).get().addOnSuccessListener {
            val categories = it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(CategoryDataModel::class.java)
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getCheckout(productId: String): Flow<ResultState<ProductsDataModel>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get()
                .addOnSuccessListener {
                    val product = it.toObject(ProductsDataModel::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getBanners(): Flow<ResultState<List<BannersDataModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(BANNERS).get().addOnSuccessListener {
            val banner = it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(BannersDataModel::class.java)
            }
            trySend(ResultState.Success(banner))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductsDataModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(PRODUCT_COLLECTION).whereEqualTo(CATEGORIES, categoryName)
                .get().addOnSuccessListener {
                    val products = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(ProductsDataModel::class.java)?.apply {
                            productId = documentSnapshot.id
                        }
                    }
                    trySend(ResultState.Success(products))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductsDataModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
                .collection(USER_FAV).get().addOnSuccessListener {
                    val fav = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(ProductsDataModel::class.java)
                    }
                    trySend(ResultState.Success(fav))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }
}