package com.github.ivan_osipov.clabo.command

import com.github.ivan_osipov.clabo.model.Update

class Command(val name: String, val parameter: String? = null, val update: Update)