# Clabo
[![license](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/ivan-osipov/Clabo.svg?branch=master)](https://travis-ci.org/ivan-osipov/Clabo) 
[![Chat](https://img.shields.io/badge/any%20questions-telegram-2370bc.svg)](https://t.me/joinchat/D2ZdJQ57kOIU7APYaDb2eg) 
[![Maven Central](https://img.shields.io/maven-central/v/com.github.ivan_osipov/clabo.svg)](https://search.maven.org/#artifactdetails%7Ccom.github.ivan_osipov%7Cclabo%7C0.0.1%7Cjar)

Clabo is DSL (Domain-Specific Language) for [Kotlin](https://kotlinlang.org) which gives you the easiest way to create 
[bot for Telegram messenger](https://core.telegram.org/bots).  

## Usage
If you use Gradle, you should add the follow config
```gradle
repositories {
    mavenCentral()
    jcenter() //for used fuel lib
}
dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlin_version", //probably, you have the kotlin stdlib dependency
            'com.github.ivan_osipov:clabo:0.0.1', //the clabo dependency
            'ch.qos.logback:logback-classic:1.2.3', //any implementation of logging for slf4j
            'ch.qos.logback:logback-core:1.2.3')
}

```

The easiest sample of bot that you can implement below. You should change 'apiKey' with your value
```kotlin
fun main(args: Array<String>) {
    bot("apiKey") longPooling {
        onStart { command ->
            val message = command.update.message!!
            message answer "Hi, ${message.from}"
        }
    }
}
```

## The Main Idea
I believe automatization moves the world forward. I believe that a business which really helps people at same time does 
them happier. I see that support teams, shops and different services which work with bots really simplify life of them
 customers and I want to see the same 
convenient customer service everywhere and this project is my contribution in shared interest.

## Author
Clabo is developed by [Ivan Osipov](https://github.com/ivan-osipov) as free and open source project.

## Contribution
There are many features which should be supported. If you have ideas about better DSL, please, feel free to create an 
issue with a description of your thought and it will be discussed. If you have some code with a new part of DSL, 
good changes in internal code or fixes for docs, please, feel free to do pull request.
More information about contribution you can find in [contribution guidelines for this project](https://github.com/ivan-osipov/Clabo/blob/master/CONTRIBUTING.md)

## Kotlin and Java
Kotlin give us ability for making projects which will be started on JRE 6. Sometimes there are limitations 
for launching applications in JRE 8 in production. This project is compiled for JRE 6.

## Code Of Conduct
Openness for the dialog opens door for contribution to this project.
More information about code of conduct you can find [here](https://github.com/ivan-osipov/Clabo/blob/master/CODE_OF_CONDUCT.md).

## License
Clabo is Open Source which is licensed under the Apache Licenses, version 2.0. It can be freely used in commercial 
projects.

## Dependencies
In this project there is usage of the next libraries:
#### Directly in library
- [Fuel 1.7.0](https://github.com/kittinunf/Fuel) - The easiest HTTP networking library for Kotlin/Android 
(MIT License)
- [Gson 2.8.0](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects 
into JSON and back (Apache 2.0 License)
- [Slf4j-Api 1.7.25](https://www.slf4j.org) - The Simple Logging Facade for Java (MIT License)
- [Guava 22.0](https://github.com/google/guava) - Google Core Libraries for Java (Apache 2.0 License)  
#### In test runtime
- [Logback 1.2.3](https://logback.qos.ch) - Logging (Eclipse Public License v1.0)
