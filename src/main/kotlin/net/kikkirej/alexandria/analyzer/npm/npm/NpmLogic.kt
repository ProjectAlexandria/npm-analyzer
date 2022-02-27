package net.kikkirej.alexandria.analyzer.npm.npm

import net.kikkirej.alexandria.analyzer.npm.db.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class NpmLogic(@Autowired val commandRunner: NpmCommandRunner,
               @Autowired val jsonReader: NpmJsonReader,
               @Autowired val analysisRepository: AnalysisRepository,
               @Autowired val npmProjectRepository: NPMProjectRepository,
               @Autowired val npmDependencyRepository: NPMDependencyRepository,
               @Autowired val npmProjectDependencyRepository: NPMProjectDependencyRepository,
) {
    fun handleProjectIn(projectDirectory: File, subpath: String, analysisId: Long){
        val npmLsCommandOutput = commandRunner.runNpmLsCommandIn(projectDirectory)

        val parentData = jsonReader.parseJson(npmLsCommandOutput)

        val project: NPMProject = getProject(parentData, subpath, analysisId)
        handleDependencies(project, parentData.dependencies, 0, null)
    }

    private fun handleDependencies(
        project: NPMProject,
        dependencies: Map<String, NpmJsonDependency>,
        depth: Int,
        parent: NPMProjectDependency?
    ) {
        for (dependencyName in dependencies.keys){
            val npmJsonDependency = dependencies[dependencyName]!!
            val npmDependency = getNPMDependency(dependencyName)
            val npmProjectDependency = NPMProjectDependency(
                depth = depth,
                parent = parent,
                dependency = npmDependency,
                project = project,
                version = npmJsonDependency.version,
                resolve_url = npmJsonDependency.resolved
            )
            npmProjectDependencyRepository.save(npmProjectDependency)
            handleDependencies(project, npmJsonDependency.dependencies, depth+1, npmProjectDependency)
        }
    }

    private fun getNPMDependency(dependencyName: String): NPMDependency {
        val optional = npmDependencyRepository.findByName(dependencyName)
        if(optional.isPresent){
            return optional.get()
        }else{
            val npmDependency = NPMDependency(name = dependencyName)
            npmDependencyRepository.save(npmDependency)
            return npmDependency
        }
    }

    private fun getProject(parentData: NpmJsonData, subpath: String, analysisId: Long): NPMProject {
        val analysisOptional = analysisRepository.findById(analysisId)
        val npmProject = NPMProject(
            path = subpath,
            version = parentData.version,
            analysis = analysisOptional.get(),
            name = parentData.name
        )
        npmProjectRepository.save(npmProject)
        return npmProject
    }
}