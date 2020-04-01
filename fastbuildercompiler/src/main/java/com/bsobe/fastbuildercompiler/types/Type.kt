package com.bsobe.fastbuildercompiler.types

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import javax.lang.model.element.Element

abstract class Type {

    abstract fun isSatisfy(typeName: TypeName): Boolean

    abstract fun getKotlinType(element: Element, typeName: TypeName): TypeName

    abstract fun getPropertyDefaultValue(element: Element, typeName: TypeName): CodeBlock

    abstract fun getParameterDefaultValue(element: Element, typeName: TypeName): CodeBlock

    companion object {
        private val types: MutableList<Type> = mutableListOf(
            NullableBooleanType,
            BooleanType,
            IntType,
            IntegerType,
            DoubleType,
            LongType,
            StringType,
            AnyType,
            ArrayListType,
            ListType
        )

        fun decideType(typeName: TypeName): Type =
            types.find { it.isSatisfy(typeName) } ?: UnmatchedType(typeName)
    }
}