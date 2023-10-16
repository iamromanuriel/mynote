package com.romanuriel.utils.enums

sealed class RecordingState {
    object NotRecording : RecordingState()
    object Recording : RecordingState()
    data class Error(val message: String) : RecordingState()
}