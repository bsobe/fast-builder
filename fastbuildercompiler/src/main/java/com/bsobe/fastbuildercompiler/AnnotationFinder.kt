package com.bsobe.fastbuildercompiler

import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

object AnnotationFinder {

    fun searchOn(
        logger: Messager,
        rootElements: Set<Element>,
        supportedAnnotations: MutableSet<out TypeElement>
    ): Sequence<TypeElement> {
        return rootElements.asSequence()
            .filterIsInstance<TypeElement>()
            .map { Pair(it.annotationMirrors.asSequence(), it) }
            .filter {
                supportedAnnotations.find { annotation ->
                    it.first.find { annotationMirror ->
                        annotationMirror.annotationType == annotation.asType()
                    } != null
                } != null
            }.map {
                it.second
            }
            .onEach { logger.printMessage(Diagnostic.Kind.NOTE, it.toString()) }
    }
}