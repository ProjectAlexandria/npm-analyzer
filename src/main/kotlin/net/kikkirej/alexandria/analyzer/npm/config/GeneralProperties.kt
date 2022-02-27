package net.kikkirej.alexandria.analyzer.npm.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("alexandria")
class GeneralProperties {
    var sharedfolder = "/alexandriadata"
}