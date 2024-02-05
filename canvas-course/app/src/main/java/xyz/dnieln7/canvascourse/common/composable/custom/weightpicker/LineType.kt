package xyz.dnieln7.canvascourse.common.composable.custom.weightpicker

sealed class LineType {
    data object Normal : LineType()
    data object FiveStep : LineType()
    data object TenStep : LineType()
}