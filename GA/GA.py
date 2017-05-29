# -*- coding: UTF-8 -*-
import math
import random
# 函数
def Rosenbrock(x1,x2):
    return 100*(x1*x1-x2)**2+(1-x1)**2

# 解码
def decode(x):
    x1,x2=0,0
    if x[0]==1:
        x1=-int(x[1:12],2)*1.0/1000
    else:
        x1 =int(x[1:12], 2) * 1.0 / 1000
    if x[12]==1:
        x2=-int(x[13:24],2)*1.0/1000
    else:
        x2 =int(x[13:24], 2) * 1.0 / 1000
    return x1,x2

# 随机生成 X
def randomX():
    x=''
    for i in range(24):
        x=x+str(random.randint(0,1))
    return x

def main():
    x=[]
    number=1000
    # 1. 编码 - 初始化1000个物种
    for i in range(number):
        x.append(randomX())

    last_total_adapt=0
    this_total_adapt=10
    adapt = []
    # 直到最后生存的几乎只有一种物种
    while(abs(last_total_adapt-this_total_adapt)>0.1):
        print abs(last_total_adapt-this_total_adapt)
        # 重置
        last_total_adapt = this_total_adapt
        this_total_adapt = 0
        adapt = []
        # 2. 计算适应度
        for i in range(len(x)):
            x1, x2 = decode(x[i])
            adapt.append(Rosenbrock(x1, x2))

        # 3. 复制 - 找到适应度最大的和最小的
        minAdapt,minX=adapt[0],0
        maxAdapt,maxX=adapt[0],0
        for i in range(len(x)):
            if(adapt[i]<minAdapt):
                minAdapt, minX = adapt[i], i
            if (adapt[i] > maxAdapt):
                maxAdapt, maxX =  adapt[i], i
        #  适应度最小的去掉,最大的double
        x[minX],adapt[minX]=x[maxX],adapt[maxX]

        # 4. 交换 随机交换(这里交换 3 次)
        for i in range(3):
            n1=random.randint(0,3)
            n2 = random.randint(0, 3)
            xa=x[n1]
            xb =x[n2]
            cut_point=random.randint(1,23)
            xa1 = xa[:cut_point] + xb[cut_point:]
            xb1 = xb[:cut_point] + xa[cut_point:]
            x[n1]=xa1
            x[n2]=xb1
        # 5. 变异
        for i in range(len(x)):
            tempx=x[i]
            for j in range(len(tempx)):
                # 每位有十万分之2的概率变异
                ran=random.randint(0,100000)
                if ran>99998:
                    if tempx[j] is '1':
                       tempx=tempx[:j]+'0'+tempx[j+1:]
                    else:
                        tempx = tempx[:j] + '1' + tempx[j + 1:]

        # 重算 totoal_adapt
        for i in range(len(x)):
            x1, x2 = decode(x[i])
            adapt.append(Rosenbrock(x1, x2))
            this_total_adapt=this_total_adapt+Rosenbrock(x1, x2)

    print decode(x[0])
    print adapt[0]

main()
