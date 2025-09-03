package gautam.projects.dsavizualizer.sorting_techquines.data.local



fun mergeSortWithHistory(list: List<Int>): List<List<Int>> {
    // A list to store the snapshot of the array at each step.
    val history = mutableListOf<List<Int>>()
    // Add the initial, unsorted state as the very first step.
    history.add(list.toList())

    // We work on a mutable copy of the list.
    val mutableList = list.toMutableList()

    // Call the recursive helper function to start the sorting.
    mergeSortRecursive(mutableList, history, 0, mutableList.size - 1)

    return history
}

/**
 * This is the recursive helper that does the actual work.
 * It sorts the list "in-place" between a 'start' and 'end' index.
 */
private fun mergeSortRecursive(
    list: MutableList<Int>,
    history: MutableList<List<Int>>,
    start: Int,
    end: Int
) {
    // Base case: If the section has 0 or 1 elements, it's already sorted.
    if (start >= end) {
        return
    }

    // --- The "Divide" Step ---
    val middle = start + (end - start) / 2
    mergeSortRecursive(list, history, start, middle)
    mergeSortRecursive(list, history, middle + 1, end)

    // --- The "Conquer" Step ---
    // Merge the two sorted halves.
    val leftHalf = list.subList(start, middle + 1).toList()
    val rightHalf = list.subList(middle + 1, end + 1).toList()
    val merged = merge(leftHalf, rightHalf) // Your existing merge function!

    // This is the MOST IMPORTANT part.
    // We copy the small, newly merged result back into the correct position
    // in our main list.
    for (i in merged.indices) {
        list[start + i] = merged[i]
    }

    // After updating the main list, we save a snapshot of its
    // current state to our history. .toList() creates a new, safe copy.
    history.add(list.toList())
}
fun merge(left: List<Int>, right: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    var leftIndex = 0
    var rightIndex = 0

    // Loop while there are still elements to compare in BOTH lists
    while (leftIndex < left.size && rightIndex < right.size) {
        if (left[leftIndex] < right[rightIndex]) {
            result.add(left[leftIndex])
            leftIndex++ // Move to the next element in the left list
        } else {
            result.add(right[rightIndex])
            rightIndex++ // Move to the next element in the right list
        }
    }

    // After the loop, one of the lists might still have elements left over.
    // Add all the remaining elements from the left list.
    while (leftIndex < left.size) {
        result.add(left[leftIndex])
        leftIndex++
    }

    // Add all the remaining elements from the right list.
    while (rightIndex < right.size) {
        result.add(right[rightIndex])
        rightIndex++
    }

    return result
}