import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class AndroidLibraryConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.getByName(
            "androidComponents"
        ) as LibraryAndroidComponentsExtension

        extension.finalizeDsl {
            with(it) {
                compileSdk = Defaults.TARGET_SDK

                defaultConfig {
                    minSdk = Defaults.MIN_SDK

                    testInstrumentationRunner = Defaults.DEFAULT_TEST_INSTRUMENTATION_RUNNER
                }
            }
        }
    }
}
