package xyz.dnieln7.portfolio.fake

import xyz.dnieln7.portfolio.data.local.model.ProjectDbModel
import xyz.dnieln7.portfolio.data.mapper.DEFAULT_DELETED_AT
import xyz.dnieln7.portfolio.data.remote.dto.ProjectDTO
import xyz.dnieln7.portfolio.domain.model.Project

fun buildProjectDTO(): ProjectDTO {
    return ProjectDTO(
        id = 123,
        name = "My Awesome Project",
        ownership = "John Doe",
        summary = "A project that does something awesome.",
        year = 2022,
        importance = 9.5,
        thumbnail = "https://example.com/thumbnail.jpg",
        images = listOf(
            "https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            "https://example.com/image3.jpg"
        ),
        tags = listOf("programming", "web development", "machine learning"),
        duration = "3 months",
        description = "This project is designed to do something awesome using machine learning algorithms.",
        features = listOf("feature 1", "feature 2", "feature 3"),
        technologies = listOf("Python", "TensorFlow", "Flask", "HTML", "CSS", "JavaScript"),
        androidGit = "https://github.com/example/my-awesome-project-android",
        androidUrl = "https://example.com/android",
        webUrl = "https://example.com",
        webGit = "https://github.com/example/my-awesome-project",
        programUrl = "https://python.org",
        programGit = "https://github.com/example/my-awesome-project-python",
        createdAt = "2022-01-01T00:00:00Z",
        updatedAt = "2022-03-31T23:59:59Z"
    )
}

fun buildProjectDbModel(): ProjectDbModel {
    return ProjectDbModel(
        id = 456,
        name = "My Cool App",
        ownership = "Jane Smith",
        summary = "A mobile app that helps users find local events.",
        year = 2023,
        importance = 8.2,
        thumbnail = "https://example.com/app_thumbnail.jpg",
        images = listOf(
            "https://example.com/app_image1.jpg",
            "https://example.com/app_image2.jpg",
            "https://example.com/app_image3.jpg"
        ),
        tags = listOf("mobile development", "event discovery", "UI/UX design"),
        duration = "6 months",
        description = "This mobile app is designed to help users find local events and activities by leveraging APIs from various sources.",
        features = listOf("event search", "event details", "user profile", "favorites"),
        technologies = listOf("Kotlin", "Android Studio", "Retrofit", "Glide", "Firebase"),
        androidGit = "https://github.com/example/my-cool-app-android",
        androidUrl = "https://example.com/app-android",
        webUrl = "https://example.com/app-web",
        webGit = "https://github.com/example/my-cool-app-web",
        programUrl = "https://kotlinlang.org",
        programGit = "https://github.com/example/my-cool-app-kotlin",
        createdAt = "2023-01-01T00:00:00Z",
        updatedAt = "2023-06-30T23:59:59Z",
        deletedAt = DEFAULT_DELETED_AT,
    )
}

fun buildProject(
    id: Int = 789,
    updatedAt: String = "2021-04-30T23:59:59Z",
    deletedAt: Long = DEFAULT_DELETED_AT,
): Project {
    return Project(
        id = id,
        name = "E-commerce website",
        ownership = "Alex Lee",
        summary = "An online store for selling fashion products.",
        year = 2021,
        importance = 7.8,
        thumbnail = "https://example.com/ecommerce_thumbnail.jpg",
        images = listOf(
            "https://example.com/ecommerce_image1.jpg",
            "https://example.com/ecommerce_image2.jpg",
            "https://example.com/ecommerce_image3.jpg"
        ),
        tags = listOf("web development", "e-commerce", "UI/UX design"),
        duration = "4 months",
        description = "This e-commerce website is designed to sell fashion products online with features such as product listing, shopping cart, payment processing, and user authentication.",
        features = listOf(
            "product listing",
            "shopping cart",
            "payment processing",
            "user authentication"
        ),
        technologies = listOf("PHP", "Laravel", "MySQL", "Bootstrap", "JavaScript", "jQuery"),
        androidGit = "https://github.com/example/ecommerce-android",
        androidUrl = "https://example.com/ecommerce-android",
        webUrl = "https://example.com/ecommerce",
        webGit = "https://github.com/example/ecommerce",
        programUrl = "https://php.net",
        programGit = "https://github.com/example/ecommerce-php",
        createdAt = "2021-01-01T00:00:00Z",
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}
