import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class CreateFlywayMigration : DefaultTask() {
    @Option(option = "name", description = "The migration description")
    var filename: String = "MIGRATION"

    @TaskAction
    fun flywayCreate() {
        File("src/main/resources/db/migration/V${System.currentTimeMillis()}__$filename.sql").createNewFile()
    }
}