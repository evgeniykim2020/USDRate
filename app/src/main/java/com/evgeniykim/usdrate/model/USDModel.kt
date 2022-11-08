package com.evgeniykim.usdrate.model

import org.simpleframework.xml.*
import kotlin.String
import kotlin.collections.List

@Root(name = "Record", strict = false)
data class USDModel @JvmOverloads constructor(
    @field: Attribute(name = "Id", required = false)
    @param: Attribute(name = "Id")
    val Id: String = "",
    @field: Attribute(name = "Date", required = false)
    @param: Attribute(name = "Date")
    val Date: String = "",
    @field: Element(name = "Value", required = false)
    @param: Element(name = "Value")
    val Value: String = "",
    @field: Element(name = "Nominal", required = false)
    @param: Element(name = "Nominal")
    val Nominal: String = ""
)


@Root(name = "ValCurs", strict = false)
data class ValCurs @JvmOverloads constructor(
    @field: Attribute(name = "DateRange2")
    @param: Attribute(name = "DateRange2")
    val DateRange2: String = "",
    @field: Attribute(name = "DateRange1")
    @param: Attribute(name = "DateRange1")
    val DateRange1: String = "",
    @field: Attribute(name = "name")
    @param: Attribute(name = "name")
    val name: String = "",
    @field: Attribute(name = "ID")
    @param: Attribute(name = "ID")
    val ID: String = "",
    @field: ElementList(entry = "Record", inline = true, required = false)
    @param: ElementList(entry = "Record", inline = true, required = false)
    val Record: List<USDModel> = listOf()
)


data class Base(
    val ValCurs: ValCurs?
)


