import numpy as np


def get_inputs():
    file = open("input.txt", "r")
    first = []
    second = []
    tag = "none"
    for line in file:
        just_tagged = False
        line = line[:-1]
        if line == "first":
            tag = "first"
            just_tagged = True
        elif line == "second":
            just_tagged = True
            tag = "second"
        if tag == "first" and not just_tagged:
            row = line.split()
            for i in range(0, len(row), 1):
                row[i] = int(row[i])
            first.append(row)
        elif tag == "second" and not just_tagged:
            row = line.split()
            for i in range(0, len(row), 1):
                row[i] = int(row[i])
            second.append(row)
    return np.array(first), np.array(second)


def calculate(arrays):
    if len(arrays[0]) == 2:
        return np.matmul(arrays[0], arrays[1])
    a11 = arrays[0][0:int(len(arrays) / 2) + 1, 0:int(len(arrays) / 2) + 1].copy()
    a12 = arrays[0][0:int(len(arrays) / 2) + 1, int(len(arrays) / 2) + 1:].copy()
    a21 = arrays[0][int(len(arrays) / 2) + 1:, 0:int(len(arrays) / 2) + 1].copy()
    a22 = arrays[0][int(len(arrays) / 2) + 1:, int(len(arrays) / 2) + 1:].copy()
    b11 = arrays[1][0:int(len(arrays) / 2) + 1, 0:int(len(arrays) / 2) + 1].copy()
    b12 = arrays[1][0:int(len(arrays) / 2) + 1, int(len(arrays) / 2) + 1:].copy()
    b21 = arrays[1][int(len(arrays) / 2) + 1:, 0:int(len(arrays) / 2) + 1].copy()
    b22 = arrays[1][int(len(arrays) / 2) + 1:, int(len(arrays) / 2) + 1:].copy()
    m1 = calculate((np.add(a11, a22), np.add(b11, b22)))
    m2 = calculate((np.add(a21, a22), b11))
    m3 = calculate((a11, np.add(b12, -1 * b22)))
    m4 = calculate((a22, np.add(b21, -1 * b11)))
    m5 = calculate((np.add(a11, a12), b22))
    m6 = calculate((np.add(a21, -1 * a11), np.add(b11, b12)))
    m7 = calculate((np.add(a12, -1 * a22), np.add(b21, b22)))
    ans11 = sum([m1, m4, -1 * m5, m7])
    ans12 = np.add(m3, m5)
    ans21 = np.add(m2, m4)
    ans22 = sum([m1, m3, -1 * m2, m6])
    return np.vstack((np.hstack((ans11, ans12)), np.hstack((ans21, ans22))))


if __name__ == '__main__':
    arrays = get_inputs()
    print(calculate(arrays))
