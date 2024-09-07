package kz.hxncus.mc.minesonapikotlin.util

import java.util.Arrays.sort

/**
 * This method implements the Quick Sort
 *
 * It is a Divide and Conquer an algorithm. It picks an element as a pivot and partitions the given array around the picked pivot.
 *
 * Worst-case performance	O(n^2)
 * Best-case performance	O(n log n)
 * Average performance	O(n log n)
 * Worst-case space complexity	O(1)
 **/
fun <T : Comparable<T>> Array<T>.quickSort(low: Int, high: Int) {
    if (low < high) {
        val pivot = this.partition(low, high)
        this.quickSort(low, pivot - 1)
        this.quickSort(pivot, high)
    }
}

/**
 * This method finds the pivot index for an array
 *
 * @param low The first index of the array
 * @param high The last index of the array
 *
 * */
fun <T : Comparable<T>> Array<T>.partition(low: Int, high: Int): Int {

    var left = low
    var right = high
    val mid = (left + right) / 2
    val pivot = this[mid]
    while (left <= right) {
        while (this[left] < pivot) {
            left++
        }
        while (this[right] > pivot) {
            right--
        }
        if (left <= right) {
            swapElements(left, right)
            left++
            right--
        }
    }
    return left
}

fun <T : Comparable<T>> Array<T>.swapElements(left: Int, right: Int) {
    val temp = this[left]
    this[left] = this[right]
    this[right] = temp
}

/**
 * Linear search is an algorithm that finds the position of a target value within an array (Usually unsorted)
 *
 * Worst-case performance	O(n)
 * Best-case performance	O(1)
 * Average performance	O(n)
 * Worst-case space complexity	O(1)
 */

/**
 * @param key The element you're looking for
 * @return the location of the key or –1 if the element is not found.
 **/
fun <T : Comparable<T>> Array<T>.linearSearch(key: T): Int {
    for (i in this.indices) {
        if (this[i].compareTo(key) == 0) {
            return i
        }
    }
    return -1
}

/**
 * This function implements Merge Sort
 *
 * It is a Divide and Conquer an algorithm. It sorts the array by dividing it into two halves and comparing the elements.
 *
 * Worst-case performance O(n log n)
 * Best-case performance O(n log n)
 * Average performance O(n log n)
 * Worst-case space complexity	O(n)
 */
fun <T : Comparable<T>> Array<T>.mergeSort(start: Int, end: Int) {
    val temp = arrayOfNulls<Comparable<*>>(this.size) as Array<T>

    if (start < end) {
        val mid = start + (end - start) / 2
        mergeSort(start, mid)
        mergeSort(mid + 1, end)
        merge(temp, start, mid, end)
    }
}

/**
 * This function merges the two halves after comparing them
 * @param temp The temp array containing the values
 * @param start A starting index of the array
 * @param mid Middle index of the array
 * @param end Ending index of the array
 */
fun <T : Comparable<T>> Array<T>.merge(temp: Array<T>, start: Int, mid: Int, end: Int) {
    System.arraycopy(this, start, temp, start, end - start + 1)
    var i = start
    var j = mid + 1
    var k = start
    while (i <= mid && j <= end) {
        if (temp[i] < temp[j]) {
            this[k++] = temp[i++]
        } else {
            this[k++] = temp[j++]
        }
    }
    while (i <= mid) {
        this[k++] = temp[i++]
    }
    while (j <= end) {
        this[k++] = temp[j++]
    }
}

/**
 * Approach 1: Brute Force
 *
 * Complexity Analysis:
 *
 * Time complexity: O(n^2)
 * Space complexity: O(1)
 *
 * Try all the pairs in the array and see if any of them add up to the target number.
 * @param target Integer target.
 * @return Indices of the two numbers such that they add up to the target.
 */
fun IntArray.twoSum(target: Int): IntArray{
    for (index1 in this.indices) {
        val startIndex = index1 + 1
        for (index2 in startIndex..this.lastIndex) {
            if (this[index1] + this[index2] == target) {
                return intArrayOf(index1, index2)
            }
        }
    }
    return intArrayOf(0,1)

}

/**
 * Calculate the average out of a list of Double
 *
 * @return average of given numbers
 */
fun DoubleArray.average(): Double {
    var sum = 0.0
    for (number in this) {
        sum += number
    }
    return sum / this.size
}

/**
 * Calculate the average out of a list of Int
 *
 * @return average of given numbers
 */
fun IntArray.average() : Int {
    var sum = 0
    for (number in this) {
        sum += number
    }
    return sum / this.size
}

/**
 * Calculates the median out of an array of Int
 *
 * @return the middle number of the array
 */
fun IntArray.median(): Double {
    sort(this)
    return when {
        this.size % 2 == 0 -> getHalfwayBetweenMiddleValues()
        else -> getMiddleValue()
    }
}

/**
 * Calculates the middle number of an array when the size is an even number
 *
 * @return the middle number of the array
 */
fun IntArray.getHalfwayBetweenMiddleValues(): Double {
    val arraySize = this.size
    val sumOfMiddleValues = (this[arraySize / 2] + this[(arraySize / 2) - 1 ])
    return sumOfMiddleValues / 2.0
}

/**
 * Calculates the middle number of an array when the size is an odd number
 *
 * @return the middle number of the array
 */
fun IntArray.getMiddleValue(): Double {
    return this[this.size / 2].toDouble()
}
