package com.praveen.picsumapp.presentation

import androidx.navigation.NamedNavArgument


/**
 * Created by Praveen.Sharma on 01/03/25 - 08:19..
 *
 ***/

sealed interface PicSumDestinations {
    val route: String

    data object List : PicSumDestinations {
        override val route = "list"
    }

    data object Detail : PicSumDestinations {
        override val route = "detail"
        val routeWithArgs = route
        val arguments = emptyList<NamedNavArgument>()
    }
}