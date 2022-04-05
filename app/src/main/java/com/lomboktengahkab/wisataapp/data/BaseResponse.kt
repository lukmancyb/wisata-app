package com.lomboktengahkab.wisataapp.data



class BaseResponse<T>(
    val success : Boolean,
    val message : String,
    val data : T
)