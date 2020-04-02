<img src="https://raw.githubusercontent.com/bsobe/fast-builder/master/images/logo.png" width="400"/>

[![](https://jitpack.io/v/bsobe/fast-builder.svg)](https://jitpack.io/#bsobe/fast-builder) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Fast Builder
Fast Builder is a code generation tool that generates the builder classes, especially for the unit test class.

## Summarize
On the code generation side, Work done by the library is to generate a builder class of the model you add the annotation to.

## TODO
[X] An option to include object classes to the generation process.

## Motivation
* Annoying duplicated and boilerplate codes
* No more worrying about object changes

## How it works?
**Fast Builder** is a code generation tool that generates the builder classes.

# Usage
```kotlin
@FastBuilder
data class Item(
    val name: String,
    val value: Long? = null
)
```

# Generated Class
```kotlin
class ItemBuilder {
	private var name: String = ""

	private var value: Long? = 0

	fun name(name: String): ItemBuilder =
		apply { this.name = name }

	fun value(value: Long?): ItemBuilder =
		apply { this.value = value }

	fun build(): Item = Item(
		name = name,
		value = value
	)
}
```

# Installation
 - To implement **Fast Builder** to your Android project via Gradle, you need to add JitPack repository to your root build.gradle.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 - After adding JitPack repository, you can add **Fast Builder** dependency to your app level build.gradle.
```gradle
apply plugin: 'kotlin-kapt'

dependencies {
    implementation 'com.github.bsobe.fast-builder:fastbuilderannotations:$last-version'
    kapt 'com.github.bsobe.fast-builder:fastbuildercompiler:$last-version'
}
```

License
--------
    Copyright 2020 bsobe / Barış Söbe

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
