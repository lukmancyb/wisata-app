package com.lomboktengahkab.wisataapp.network


import com.lomboktengahkab.wisataapp.data.BaseResponse
import com.lomboktengahkab.wisataapp.data.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DestinationRepository {


    val destinationList: Flow<BaseResponse<List<Destination>>> = flow {
        val products = NetworkModule.service().getAllDestination()
        emit(products)
    }.flowOn(Dispatchers.IO)

    fun destinationListByCategory(id : String) : Flow<BaseResponse<List<Destination>>> = flow {
        val products = NetworkModule.service().getDestinationByCategory(id)
        emit(products)
    }.flowOn(Dispatchers.IO)




}