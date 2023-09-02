package tasks

import org.gradle.api.Project
import org.gradle.api.plugins.internal.DefaultJavaApplication

private val newLine = System.lineSeparator()

fun templateRunConfig(runName: String, moduleName: String, mainClassName: String, folderName: String): String {
    return "<component name=\"ProjectRunConfigurationManager\">$newLine" +
        "  <configuration default=\"false\" name=\"$runName\" type=\"Application\" factoryName=\"Application\" folderName=\"$folderName\">$newLine" +
        "    <option name=\"MAIN_CLASS_NAME\" value=\"$mainClassName\" />$newLine" +
        "    <module name=\"$moduleName.main\" />$newLine" +
        "    <method v=\"2\">$newLine" +
        "      <option name=\"Make\" enabled=\"true\" />$newLine" +
        "    </method>$newLine" +
        "  </configuration>$newLine" +
        "</component>"
}

fun generateRunConfig(rootProject: Project) {
    val runFolder = rootProject.file(".run")
    if (!runFolder.exists()) {
        runFolder.mkdir()
    }

    val rootProjectName = rootProject.name
    rootProject.subprojects
        .forEach {
            println(it.toString())
            val moduleNameEnd = it.name
            var moduleName = it.name
            var currentProject = it
            var previousProject: Project? = null
            val firstParentName = currentProject.parent?.name
            while (currentProject.name != rootProjectName) {
                previousProject = currentProject
                currentProject = currentProject.parent
                moduleName = currentProject.name + "." + moduleName
            }
            val runFileName =
                if ((firstParentName != rootProjectName)) "$firstParentName.$moduleNameEnd"
                else moduleNameEnd
            val runName = runFileName.replace(".", "-")
            val moduleType =
                if ((firstParentName != rootProjectName)) firstParentName
                else "^([^-]+)-".toRegex().find(runName)?.groupValues?.get(1)

            val runContent = templateRunConfig(runName, moduleName, (it.extensions.getByName("application") as DefaultJavaApplication).mainClass.get(), moduleType!!)
            rootProject.file(".run/$runFileName.run.xml").writeText(runContent);
            println("Run configuration generated for $runName")
        }
}