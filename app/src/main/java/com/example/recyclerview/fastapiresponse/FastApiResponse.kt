package com.example.recyclerview.fastapiresponse

data class Context(
    val reason: String
)

data class Detail(
    val type: String,
    val loc: List<String>,
    val msg: String,
    val input: String,
    val ctx: Context
)

data class Error422Response(
    val detail: List<Detail>
)
data class Error400Response(
    val detail: String
)