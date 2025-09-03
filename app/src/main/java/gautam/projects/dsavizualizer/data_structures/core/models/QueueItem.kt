package gautam.projects.dsavizualizer.data_structures.core.models

import java.util.UUID

data class QueueItem(
    val value: Any,
    val id: String = UUID.randomUUID().toString()
)
