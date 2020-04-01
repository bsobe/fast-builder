package com.bsobe.fastbuildercompiler

import com.bsobe.fastbuilderannotations.FastBuilder
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

class Processor : AbstractProcessor() {

    private lateinit var filer: Filer
    private lateinit var messager: Messager
    private lateinit var elementUtils: Elements

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        filer = processingEnv.filer;
        messager = processingEnv.messager;
        elementUtils = processingEnv.elementUtils;
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        val supportedAnnotations = p0 ?: return true
        val roundEnvironment = p1 ?: return true

        if (roundEnvironment.processingOver().not()) {
            //  Find all occurrences of annotation elements
            val annotatedElements: Sequence<TypeElement> = AnnotationFinder.searchOn(
                logger = messager,
                rootElements = roundEnvironment.rootElements,
                supportedAnnotations = supportedAnnotations
            )

            annotatedElements.forEach { typeElement ->
                try {
                    val packageName =
                        elementUtils.getPackageOf(typeElement).qualifiedName.toString()
                    val typeName = typeElement.simpleName.toString()
                    val className = ClassName(packageName, typeName)
                    val generatedClassName =
                        ClassName(packageName, FileOperations.generateBuilderClassName(typeName));

                    val classBuilder = TypeSpec.classBuilder(generatedClassName)
                        .addModifiers(KModifier.PUBLIC)

                    val propertyNameList = mutableListOf<String>()
                    typeElement.enclosedElements.asSequence()
                        .filter { it.kind.isField }
                        .forEach {
                            val property =
                                BuilderFactory.addClassProperties(it, classBuilder)
                            BuilderFactory.addClassMethod(it, classBuilder, generatedClassName)
                            propertyNameList.add(property.name)
                        }

                    BuilderFactory.addBuildMethod(className, propertyNameList, classBuilder)

                    FileSpec.builder(packageName, generatedClassName.simpleName)
                        .indent("\t")
                        .addType(classBuilder.build())
                        .build()
                        .writeTo(filer)
                } catch (e: Exception) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.toString(), typeElement);
                }
            }
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(FastBuilder::class.java.canonicalName)
    }
}