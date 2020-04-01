package com.bsobe.fastbuildercompiler.types

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Element

object BooleanType : Type() {

    private val CODE_BLOCK = CodeBlock.builder().add("%L", false).build()

    override fun isSatisfy(typeName: TypeName): Boolean =
        typeName.toString() == Boolean::class.qualifiedName

    override fun getKotlinType(element: Element, typeName: TypeName): TypeName {
        var type = Boolean::class.asTypeName()
        if (element.getAnnotation(Nullable::class.java) != null) {
            type = type.copy(nullable = true) as ClassName
        }
        return type
    }

    override fun getPropertyDefaultValue(element: Element, typeName: TypeName): CodeBlock =
        CODE_BLOCK

    override fun getParameterDefaultValue(element: Element, typeName: TypeName): CodeBlock =
        CODE_BLOCK
}