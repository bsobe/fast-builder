package com.bsobe.fastbuildercompiler

object FileOperations {

    private const val BUILDER_CLASS_NAME_SUFFIX: String = "Builder"

    fun generateBuilderClassName(originalFileName: String): String =
        originalFileName + BUILDER_CLASS_NAME_SUFFIX
}