package xyz.dnieln7.portfolio.data.mapper

import xyz.dnieln7.portfolio.data.local.model.ProjectDbModel
import xyz.dnieln7.portfolio.data.remote.dto.ProjectDTO
import xyz.dnieln7.portfolio.data.remote.request.UpdateProjectRequest
import xyz.dnieln7.portfolio.domain.model.Project

fun Project.toUpdateProjectRequest(): UpdateProjectRequest {
    return UpdateProjectRequest(
        id = id,
        name = name,
        ownership = ownership,
        summary = summary,
        year = year,
        importance = importance,
        thumbnail = thumbnail,
        images = images,
        tags = tags,
        duration = duration,
        description = description,
        features = features,
        technologies = technologies,
        androidGit = androidGit,
        androidUrl = androidUrl,
        webUrl = webUrl,
        webGit = webGit,
        programUrl = programUrl,
        programGit = programGit,
    )
}

fun ProjectDTO.toDomain(): Project {
    return Project(
        id = id,
        name = name,
        ownership = ownership,
        summary = summary,
        year = year,
        importance = importance,
        thumbnail = thumbnail,
        images = images,
        tags = tags,
        duration = duration,
        description = description,
        features = features,
        technologies = technologies,
        androidGit = androidGit,
        androidUrl = androidUrl,
        webUrl = webUrl,
        webGit = webGit,
        programUrl = programUrl,
        programGit = programGit,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = DEFAULT_DELETED_AT,
    )
}

fun ProjectDbModel.toDomain(): Project {
    return Project(
        id = id,
        name = name,
        ownership = ownership,
        summary = summary,
        year = year,
        importance = importance,
        thumbnail = thumbnail,
        images = images,
        tags = tags,
        duration = duration,
        description = description,
        features = features,
        technologies = technologies,
        androidGit = androidGit,
        androidUrl = androidUrl,
        webUrl = webUrl,
        webGit = webGit,
        programUrl = programUrl,
        programGit = programGit,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}

fun Project.toDbModel(): ProjectDbModel {
    return ProjectDbModel(
        id = id,
        name = name,
        ownership = ownership,
        summary = summary,
        year = year,
        importance = importance,
        thumbnail = thumbnail,
        images = images,
        tags = tags,
        duration = duration,
        description = description,
        features = features,
        technologies = technologies,
        androidGit = androidGit,
        androidUrl = androidUrl,
        webUrl = webUrl,
        webGit = webGit,
        programUrl = programUrl,
        programGit = programGit,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}

const val DEFAULT_DELETED_AT = 0L
