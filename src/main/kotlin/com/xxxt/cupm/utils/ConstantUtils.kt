package com.xxxt.cupm.utils

import com.xxxt.cupm.CUPMMod
import net.minecraft.resources.ResourceLocation

fun cupmResource(path: String) : ResourceLocation = ResourceLocation.fromNamespaceAndPath(CUPMMod.MODID, path)
