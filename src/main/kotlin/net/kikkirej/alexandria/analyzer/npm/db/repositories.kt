package net.kikkirej.alexandria.analyzer.npm.db

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface AnalysisRepository : CrudRepository<Analysis, Long>

interface NPMProjectRepository: CrudRepository<NPMProject, Long>

interface NPMProjectDependencyRepository: CrudRepository<NPMProjectDependency, Long>

interface NPMDependencyRepository: CrudRepository<NPMDependency, Long>{
    fun findByName(name: String) : Optional<NPMDependency>
}