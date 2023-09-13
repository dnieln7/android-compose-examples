import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class AndroidApplicationConfigPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.getByName(
            "androidComponents"
        ) as ApplicationAndroidComponentsExtension

        extension.finalizeDsl {
            with(it) {
                compileSdk = Defaults.TARGET_SDK

                defaultConfig {
                    minSdk = Defaults.MIN_SDK
                    targetSdk = Defaults.TARGET_SDK

                    testInstrumentationRunner = Defaults.DEFAULT_TEST_INSTRUMENTATION_RUNNER
                }
            }
        }
    }
}
