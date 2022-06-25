package com.nikhilchauhan.locationtracker.model

import com.google.gson.annotations.SerializedName

data class Location(
  @SerializedName("latitude")
  val latitude: Double,

  @SerializedName("longitude")
  val longitude: Double,

  @SerializedName("altitude")
  val altitude: Double,

  @SerializedName("bearing")
  val bearing: Float
)

data class LocationResponse(
  @SerializedName("locations")
  val locations: List<Location> = listOf()
)