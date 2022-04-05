package com.lomboktengahkab.wisataapp.network


import com.lomboktengahkab.wisataapp.data.BaseResponse
import com.lomboktengahkab.wisataapp.data.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepository {



    val category : Flow<BaseResponse<List<Category>>> = flow {
        val categories = NetworkModule.service().getAllCategory()
        emit(categories)
    }.flowOn(Dispatchers.IO)

}