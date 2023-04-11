package com.example.dnevnjak.presentation.states

sealed class ObligationModeState {
    object ReviewObligation : ObligationModeState()
    object EditObligation : ObligationModeState()
    object AddObligation : ObligationModeState()
    object DeleteObligation : ObligationModeState()
}