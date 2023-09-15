package com.example.movilessoftware2023

class ICities(
    var name: String?,
    var state: String?,
    var country: String?,
    var capital: Boolean?,
    var population: Number?,
    var regions: List<String>?
) {
    override fun toString(): String {
        return "${name} - ${country}"
    }
}