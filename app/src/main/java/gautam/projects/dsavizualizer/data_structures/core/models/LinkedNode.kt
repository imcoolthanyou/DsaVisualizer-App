package gautam.projects.dsavizualizer.data_structures.core.models

import java.util.UUID

data class LinkedNode(
    val nodeValue: Any,
    val nextAddress: String,
    val id:String= UUID.randomUUID().toString()
)
