package com.supersapiens.jobfinder.job

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class contains the data associated with a single job. This may
 * come directly from the GitHub Jobs API, or it may be pulled from the
 * local database.
 *
 * @param id The job's unique ID.
 * @param company The company name.
 * @param title The job title.
 * @param description The job description in simple HTML.
 */
@Entity
data class Job(
    @PrimaryKey val id: String,
    val company: String,
    val title: String,
    val description: String
)
