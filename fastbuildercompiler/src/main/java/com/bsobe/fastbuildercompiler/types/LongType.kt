package com.bsobe.fastbuildercompiler.types

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.Element

object LongType : Type() {

    private val CODE_BLOCK = CodeBlock.builder().add("%L", 0L).build()

    override fun isSatisfy(typeName: TypeName): Boolean =
        typeName.toString() == java.lang.Long::class.java.name

    override fun getKotlinType(element: Element, typeName : TypeName): TypeName {
        var type = Long::class.asTypeName()
        if (element.getAnnotation(Nullable::class.java) != null) {
            type = type.copy(nullable = true) as ClassName
        }
        return type
    }

    override fun getPropertyDefaultValue (element: Element, typeName : TypeName): CodeBlock = CODE_BLOCK

    override fun getParameterDefaultValue (element: Element, typeName : TypeName): CodeBlock = CODE_BLOCK
}