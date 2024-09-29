package com.baseapplication.countrypicker

class Country {
    var isoCode: String? = null
    var dialingCode: String? = null

    constructor(isoCode: String, dialingCode: String) {
        this.isoCode = isoCode
        this.dialingCode = dialingCode
    }
}
