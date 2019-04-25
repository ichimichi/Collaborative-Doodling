package com.softwareengineeringproject.collaborativedoodling.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Instruction {
    var command : String? = ""
    var x : Float? = 0.0F
    var y : Float? = 0.0F
    var color : Int? = 0
    override fun toString(): String {
        return "$command + $x + $y + $color"
    }
}