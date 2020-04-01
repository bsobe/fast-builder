package com.bsobe.fastbuildercompiler.types

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element

object IntegerType : Type() {

    private val CODE_BLOCK = CodeBlock.builder().add("%L", 0).build()

    override fun isSatisfy(typeName: TypeName): Boolean =
        typeName.toString() == Integer::class.java.name

    override fun getKotlinType(element: Element, typeName: TypeName): TypeName {
        return Int::class.asTypeName().copy(nullable = true)
    }

    override fun getPropertyDefaultValue(element: Element, typeName: TypeName): CodeBlock =
        CODE_BLOCK

    override fun getParameterDefaultValue(element: Element, typeName: TypeName): CodeBlock =
        CODE_BLOCK
}