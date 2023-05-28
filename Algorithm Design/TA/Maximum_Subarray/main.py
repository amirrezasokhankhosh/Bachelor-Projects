def get_inputs():
    file = open("input.txt", "r")
    lines = []
    for line in file:
        lines.append(int(line[:-1]))
    return lines


def find_maximum_subarray(array):
    if len(array) == 1:
        return 0, 0, array[0]
    right_part = find_maximum_subarray(array[int(len(array) / 2):])
    left_part = find_maximum_subarray(array[:-1 * (int(len(array) / 2) + len(array) % 2)])
    middle_part = find_middle_part(array)
    tuples = [left_part, (right_part[0] + int(len(array) / 2), right_part[1] + int(len(array) / 2), right_part[2]),
              middle_part]
    tuples.sort(key=lambda x: x[2])
    return tuples[2]


def find_middle_part(array):
    # right
    right_index = int(len(array) / 2)
    best_right_sum = array[right_index]
    for i in range(int(len(array) / 2) + 1, int(len(array)), 1):
        if best_right_sum < best_right_sum + array[i]:
            best_right_sum = best_right_sum + array[i]
            right_index = i
    # left
    left_index = int(len(array) / 2) - 1
    best_left_sum = array[left_index]
    for i in range(int(len(array) / 2) - 2, -1, -1):
        if best_left_sum < best_left_sum + array[i]:
            best_left_sum = best_left_sum + array[i]
            left_index = i
    return left_index, right_index, best_left_sum + best_right_sum


if __name__ == '__main__':
    array = get_inputs()
    print(find_maximum_subarray(array))