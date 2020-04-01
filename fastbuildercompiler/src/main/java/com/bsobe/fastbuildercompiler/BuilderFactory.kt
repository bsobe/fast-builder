package com.bsobe.fastbuildercompiler

import com.bsobe.fastbuildercompiler.types.Type
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

object BuilderFactory {

    fun addClassProperties(element: Element, classBuilder: TypeSpec.Builder): PropertySpec {
        val propertyType = element.asType().asTypeName()
        KTypeProjection(KVariance.INVARIANT, Int::class.createType())
        val decidedPropertyType = Type.decideType(propertyType)
        val decidedType = decidedPropertyType.getKotlinType(element, propertyType)
        val property = PropertySpec
            .builder(
                name = element.simpleName.toString(),
                type = decidedType,
                modifiers = *arrayOf(KModifier.PRIVATE)
            )
            .initializer(decidedPropertyType.getPropertyDefaultValue(element, propertyType))
            .mutable(mutable = true)
            .build()
        classBuilder.addProperty(propertySpec = property)
        return property
    }

    fun addClassMethod(
        element: Element,
        classBuilder: TypeSpec.Builder,
        generatedClassName: ClassName
    ) {
        val propertyType = element.asType().asTypeName()
        val elementName = element.simpleName.toString()
        val decidedPropertyType = Type.decideType(propertyType)
        val decidedType = decidedPropertyType.getKotlinType(element, propertyType)
        val parameterSpec = ParameterSpec
            .builder(
                name = elementName,
                type = decidedType
            )
            .build()

        val function = FunSpec
            .builder(elementName)
            .addParameter(parameterSpec)
            .returns(generatedClassName)
            .addStatement("return \n\tapply { this.$elementName = $elementName }")
            .build()

        classBuilder.addFunction(function)
    }

    fun addBuildMethod(
        className: ClassName,
        propertyNames: List<String>,
        classBuilder: TypeSpec.Builder
    ) {
        val returnStatementBuilder = StringBuilder("return ${className.simpleName}(")
        propertyNames.forEachIndexed { index, propertyName ->
            returnStatementBuilder.append("\n\t$propertyName = $propertyName")
            if (index != propertyNames.lastIndex) {
                returnStatementBuilder.append(",")
            }
        }
        returnStatementBuilder.append("\n)")
        val function = FunSpec
            .builder("build")
            .returns(className)
            .addStatement(returnStatementBuilder.toString())
            .build()
        classBuilder.addFunction(function)
    }
}