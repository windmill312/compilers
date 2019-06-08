list = [1,2,3,4,5]
nnCnt = 2

for i in range(0, nnCnt):
    temp = list[len(list) - 1 - i]
    list[len(list) - 1 - i] = list[i]
    list[i] = temp
print(list)
