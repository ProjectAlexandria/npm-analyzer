package net.kikkirej.alexandria.analyzer.npm.npm

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class NpmJsonReader {

    fun parseJson(json: String): NpmJsonData {
        val jsonParser: ObjectMapper = ObjectMapper()
        return jsonParser.readValue(json, NpmJsonData::class.java)
    }

}

class NpmJsonData(
    val name: String,
    val version: String,
    val dependencies: Map<String, NpmJsonDependency>
){}

class NpmJsonDependency(
    val version: String,
    val resolved: String,
    val dependencies: Map<String, NpmJsonDependency>
){}