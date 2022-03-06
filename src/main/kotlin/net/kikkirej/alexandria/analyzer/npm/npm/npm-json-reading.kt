package net.kikkirej.alexandria.analyzer.npm.npm

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class NpmJsonReader {

    fun parseJson(json: String): NpmJsonData {
        val jsonParser = ObjectMapper()
        return jsonParser.readValue(json, NpmJsonData::class.java)
    }

}

class NpmJsonData(
    var name: String ="",
    var version: String = "",
    var dependencies: Map<String, NpmJsonDependency> = mapOf()
){}

class NpmJsonDependency(
    var version: String="",
    var resolved: String="",
    var dependencies: Map<String, NpmJsonDependency> = mapOf()
){}