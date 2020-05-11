package com.example.dto

import com.example.Response

data class ItemDTO(
        var id: String? = null,
        var name: String = "",
        var description: String = "") : Response()