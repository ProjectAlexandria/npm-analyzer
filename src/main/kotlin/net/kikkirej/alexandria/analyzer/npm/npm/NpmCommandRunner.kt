package net.kikkirej.alexandria.analyzer.npm.npm

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.reflect.typeOf


@Service
class NpmCommandRunner {
    fun runNpmLsCommandIn(directory: File): String {

        val process = ProcessBuilder(
            "npm",
            "ls",
            "--all",
            "--package-lock-only",
            "--json"
        ).directory(directory)
            .start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val builder = StringBuilder()
        var line: String? = ""
        while (reader.readLine().also { line = it } != null) {
            builder.append(line)
            builder.append(System.getProperty("line.separator"))
        }
        return builder.toString()
    }
}