# Supersapiens Job Finder (Android)

This project contains a rudimentary two screen GitHub Jobs client
Android app. It has two functions:

1. Searching the GitHub Jobs API for jobs matching a given search query.
2. Tracking interesting jobs in local storage.

It uses many of the same ubiquitous Android libraries we use in our real
apps.

We have removed approximately 100 lines of code from the working app and
stubbed out the associated API so everything still compiles. Your task is
to return the app to working order. That means:

1. When the app is started and/or the search query is blank, show the
   list of tracked jobs. This list will be empty until you search for
   and track at least one job.
2. When the search icon is tapped on the job list screen and a search
   query is enetered, fetch and display the matching jobs from the
   GitHub Jobs API.
3. When a job in the list is tapped on, navigate to the job detail screen
   for that job. If it is a tracked job, display the locally-cached job
   data immediately, then update it with data fetched from the API.
4. When the heart icon is tapped on the job detail page toggle the
   tracking status of the associated job. A job is "tracked" if it is
   stored in the local database. Deleting the job from the database
   stops tracking it.
5. Swiping left on a tracked job removes it from the jobs list.

All the stubbed out code is marked with TODOs. The API, database, and
UI code have been left alone to minimize the amount of app-specific
knowledge you will need to complete this task. No files have been
removed from the project.


This following links show you how the app would function once you solve this challenge:

GIF version --> [GIF showing the (solved) working app](https://android-challenge.s3.amazonaws.com/AndroidChallenge.gif)

WEBM version --> [Video showing the (solved) working app](https://android-challenge.s3.amazonaws.com/AndroidChallenge.webm)