package net.kikkirej.alexandria.analyzer.npm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NpmAnalyzerApplication

fun main(args: Array<String>) {
	runApplication<NpmAnalyzerApplication>(*args)
}
