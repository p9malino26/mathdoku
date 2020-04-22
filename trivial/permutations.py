outSet = set()
runs = 0

def testAdd(cage : list, start: int, permute = False):
    global runs
    if (len(cage) == 0):
        print()
        return
    for i, e in enumerate(cage):
        result = start + e
        runs+=1
        print(e, end=' ')
        #outSet.add(result)
        if permute:
            newList = cage[:i] + cage[i + 1:]
        else:
            newList = cage[i + 1:]

        testAdd(newList, result)

permute = True
operator = lambda x, y: x / y

def f(result, cageList, startindex = 0):
    print("Analyzing", result)
    for i, e in enumerate(cageList):
        if startindex == 0:
            newResult = e
        else:
            newResult = operator(result, e)

        if permute:
            newList = cageList[:i] + cageList[i + 1:]
        else:
            newList = cageList[i + 1:]

        f(newResult, newList, 1)
    print("Done analyzing", result)


f(0, [6, 1, 12])