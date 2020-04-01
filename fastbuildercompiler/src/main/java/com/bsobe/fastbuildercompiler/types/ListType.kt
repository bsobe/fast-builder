package com.bsobe.fastbuildercompiler.types

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Element

object ListType : Type() {

    override fun isSatisfy(typeName: TypeName): Boolean {
        return (typeName as? ParameterizedTypeName)?.rawType.toString() == List::class.java.name
    }

    override fun getKotlinType(element: Element, typeName: TypeName): TypeName {
        val parameterizedTypeName = (typeName as ParameterizedTypeName).typeArguments[0]
        val decidedTypeName: TypeName = if (parameterizedTypeName is WildcardTypeName) {
            parameterizedTypeName
        } else {
            decideType(parameterizedTypeName).getKotlinType(element, typeName)
        }
        return ClassName(
            "kotlin.collections",
            "MutableList"
        ).parameterizedBy(decidedTypeName).copy(element.getAnnotation(Nullable::class.java) != null)
    }

    override fun getPropertyDefaultValue(element: Element, typeName: TypeName): CodeBlock {
        val parameterizedTypeName = (typeName as ParameterizedTypeName).typeArguments[0]
        val decidedTypeName: TypeName = if (parameterizedTypeName is WildcardTypeName) {
            parameterizedTypeName.outTypes[0]
        } else {
            // Come on!! Parameterized type does not include any annotation about nullability!!
            // Ask someone.
            // Also try to get Nullable annotation to decide may not be the best practice. Ask KotlinPoet repository.
            decideType(parameterizedTypeName).getKotlinType(element, typeName)
        }
        return CodeBlock.builder().add("%L", "mutableListOf<${decidedTypeName}>()").build()
    }

    override fun getParameterDefaultValue(element: Element, typeName: TypeName): CodeBlock {
        val parameterizedTypeName = (typeName as ParameterizedTypeName).typeArguments[0]
        val decidedTypeName: TypeName = if (parameterizedTypeName is WildcardTypeName) {
            parameterizedTypeName.outTypes[0]
        } else {
            decideType(parameterizedTypeName).getKotlinType(element, typeName)
        }
        return CodeBlock.builder().add("%L", "emptyList<${decidedTypeName}>()").build()
    }
}
