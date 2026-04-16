import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    labyProcessor()
    api(project(":api"))

    // Gson für BlitzerManager (JSON-Persistenz)
    addonMavenDependency("com.google.code.gson:gson:2.10.1")
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.DEFAULT
}
