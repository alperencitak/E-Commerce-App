package com.alperencitak.e_commerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.Category
import com.alperencitak.e_commerce_app.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchAllParent(){
        viewModelScope.launch {
            try {
                _loading.value = true
                _categoryList.value = categoryRepository.fetchAllParent()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchChildByParentId(parentId: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _categoryList.value = categoryRepository.fetchChildByParentId(parentId)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchById(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _category.value = categoryRepository.fetchById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun add(category: Category){
        viewModelScope.launch {
            try {
                _loading.value = true
                _category.value = categoryRepository.add(category)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun deleteById(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                categoryRepository.deleteById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun update(category: Category){
        viewModelScope.launch {
            try {
                _loading.value = true
                _category.value = categoryRepository.update(category)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }


}