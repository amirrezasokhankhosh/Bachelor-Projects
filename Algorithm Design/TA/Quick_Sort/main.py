import random


def get_inputs():
    file = open("input.txt", "r")
    array = []
    for line in file:
        array.append(int(line[:-1]))
    return array


def quick_sort(array):
    if len(array) == 1 or len(array) == 0:
        return array
    pivot = array[random.randint(0, len(array))]
    left_array = []
    right_array = []
    for element in array:
        if element < pivot:
            left_array.append(element)
        elif element > pivot:
            right_array.append(element)
    left_sorted = quick_sort(left_array)
    right_sorted = quick_sort(right_array)
    return left_sorted + [pivot] + right_sorted


if __name__ == '__main__':
    array = get_inputs()
    print(quick_sort(array))
