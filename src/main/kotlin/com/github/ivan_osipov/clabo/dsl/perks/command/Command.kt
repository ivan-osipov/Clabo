package com.github.ivan_osipov.clabo.dsl.perks.command

import com.github.ivan_osipov.clabo.api.model.Update

class Command(val name: String, val parameter: String? = null, val update: Update)