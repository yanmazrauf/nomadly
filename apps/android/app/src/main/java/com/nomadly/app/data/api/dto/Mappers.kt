package com.nomadly.app.data.api.dto

import com.nomadly.app.model.Board
import com.nomadly.app.model.Destination
import com.nomadly.app.model.UserProfile

// ── DTO → Domain ─────────────────────────────────────────────────────────────

fun DestinationDto.toDomain(): Destination = Destination(
    id             = id,
    name           = name,
    country        = country,
    region         = region,
    tagline        = tagline,
    description    = description,
    imageUrl       = imageUrl,
    tags           = tags,
    rating         = rating,
    reviewCount    = reviewCount,
    highlights     = highlights,
    bestTimeToVisit = bestTimeToVisit,
    avgBudget      = avgBudget,
    isSaved        = isSaved
)

fun BoardDto.toDomain(): Board = Board(
    id                   = id,
    title                = title,
    destinationCount     = destinationCount,
    imageUrl             = imageUrl,
    collaboratorAvatars  = collaboratorAvatars,
    extraCollaborators   = extraCollaborators
)

fun UserProfileDto.toDomain(): UserProfile = UserProfile(
    name         = name,
    location     = location,
    avatarUrl    = avatarUrl,
    savedCount   = savedCount,
    boardCount   = boardCount,
    visitedCount = visitedCount,
    travelStyles = travelStyles
)

// ── Domain → DTO (for write requests) ────────────────────────────────────────

fun Destination.toDto(): DestinationDto = DestinationDto(
    id              = id,
    name            = name,
    country         = country,
    region          = region,
    tagline         = tagline,
    description     = description,
    imageUrl        = imageUrl,
    tags            = tags,
    rating          = rating,
    reviewCount     = reviewCount,
    highlights      = highlights,
    bestTimeToVisit = bestTimeToVisit,
    avgBudget       = avgBudget,
    isSaved         = isSaved
)
