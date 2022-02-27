package net.kikkirej.alexandria.analyzer.npm.db

import javax.persistence.*

@Entity(name = "analysis")
class Analysis(
    @Id var id: Long,
)

@Entity(name = "npm_project")
class NPMProject(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    var path: String,
    var version: String,
    var name: String,
    @ManyToOne var analysis: Analysis,
)

@Entity(name = "npm_project_dependency")
class NPMProjectDependency(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    var version: String,
    var resolve_url: String?,
    @ManyToOne var project: NPMProject,
    @ManyToOne var dependency: NPMDependency,
    @ManyToOne var parent: NPMProjectDependency?,
    var depth: Int,
)

@Entity(name = "npm_dependency")
class NPMDependency(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    var name: String,
)