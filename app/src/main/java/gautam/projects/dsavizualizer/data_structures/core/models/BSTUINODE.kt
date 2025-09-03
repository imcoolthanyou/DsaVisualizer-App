package gautam.projects.dsavizualizer.data_structures.core.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.UUID

data class BSTUINODE(
    val value: Int,
    val id:String= UUID.randomUUID().toString(),
    val depth:Int,
    var xOffSet: Dp =0.dp,
    var yOffSet:Dp=0.dp,
    var parentXOffSet:Dp?=null,
    var parentYOffSet:Dp?=null,
)
