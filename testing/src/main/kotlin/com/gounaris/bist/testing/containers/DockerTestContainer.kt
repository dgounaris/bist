package com.gounaris.bist.testing.containers

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.ImageFromDockerfile

object DockerTestContainer {
    class KGenericContainerFromImage(image: String) : GenericContainer<KGenericContainerFromImage>(image)
    class KGenericContainerFromDockerfile(image: ImageFromDockerfile) : GenericContainer<KGenericContainerFromDockerfile>(image)

    fun defineGenericContainer(image: String) = KGenericContainerFromImage(image)
    fun defineGenericContainer(image: ImageFromDockerfile) = KGenericContainerFromDockerfile(image)
}