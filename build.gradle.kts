tasks.register("check") {
    val subprojectTasks = subprojects.mapNotNull { it.tasks.findByName("check") }
    dependsOn(subprojectTasks)
}
