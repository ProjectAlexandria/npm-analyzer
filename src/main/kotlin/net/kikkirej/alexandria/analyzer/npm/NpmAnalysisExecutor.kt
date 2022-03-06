package net.kikkirej.alexandria.analyzer.npm

import net.kikkirej.alexandria.analyzer.npm.config.GeneralProperties
import net.kikkirej.alexandria.analyzer.npm.npm.NpmLogic
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
@ExternalTaskSubscription("npm-analysis")
class NpmAnalysisExecutor(@Autowired val generalProperties: GeneralProperties,
                          @Autowired val npmLogic: NpmLogic) : ExternalTaskHandler{

    val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        try {
            val npmProjectPath = externalTask!!.getVariable<String>("npm_project_path")
            val businessKey = externalTask.businessKey
            val projectPath = projectPathOf(npmProjectPath, businessKey)
            log.info("Analyzing Project ${projectPath.absolutePath}")

            npmLogic.handleProjectIn(projectPath, npmProjectPath, businessKey.toLong())

            externalTaskService!!.complete(externalTask)
        }catch (exception: Exception){
            externalTaskService?.handleBpmnError(externalTask, "unspecified", exception.message)
        }
    }

    private fun projectPathOf(npmProjectPath: String, businessKey: String): File {
        return File(File(generalProperties.sharedfolder).absolutePath + File.separator + businessKey + npmProjectPath)
    }
}