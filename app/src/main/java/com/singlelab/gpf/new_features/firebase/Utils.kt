package com.singlelab.gpf.new_features.firebase

import kotlin.reflect.KClass

fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>): T {
    //Get default constructor
    val constructor = clazz.constructors.first()

    //Map constructor parameters to map values
    val args = constructor
        .parameters.associateWith { map[it.name] }

    //return object from constructor call
    return constructor.callBy(args)
}