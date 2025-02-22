tasks.register("check") {
    group = "verification"
    description = "Runs the check task across all subprojects and included builds."
    val subprojectTasks = subprojects.mapNotNull { it.tasks.findByName("check") }
    dependsOn(subprojectTasks)
    gradle.includedBuilds.forEach { includedBuild ->
        dependsOn(gradle.includedBuild(includedBuild.name).task(":check"))
    }
}
