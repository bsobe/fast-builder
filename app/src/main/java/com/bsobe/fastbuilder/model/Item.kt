package com.bsobe.fastbuilder.model

import com.bsobe.fastbuilderannotations.FastBuilder

@FastBuilder
data class Item(
    val name: String,
    val value: Long? = null
)
