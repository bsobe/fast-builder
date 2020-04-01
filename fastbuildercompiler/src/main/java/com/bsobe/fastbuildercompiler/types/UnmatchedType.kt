package com.bsobe.fastbuildercompiler.types

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Element

class UnmatchedType(private val propertyType: TypeName) : Type() {

    companion object {
        private val CODE_BLOCK = CodeBlock.builder().add("%L", "null").build()
    }

    override fun isSatisfy(typeName: TypeName): Boolean = true

    override fun getKotlinType(element: Element, typeName: TypeName): TypeName =
        if (element.getAnnotation(Nullable::class.java) != null) {
            propertyType.copy(nullable = true) as ClassName
        } else {
            propertyType
        }

    override fun getPropertyDefaultValue(element: Element, typeName: TypeName): CodeBlock =
        CODE_BLOCK

    override fun getParameterDefaultValue(element: Element, typeName: TypeName): CodeBlock =
        CODE_BLOCK
}
