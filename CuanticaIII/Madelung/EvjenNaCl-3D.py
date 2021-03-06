"""
    Programa para la obtencion de la constante de Madelung para una esctructura
    3-D de NaCl mediante el procedimiento de Evjen. Se realiza el calculo
    iterativo para un numero dado de zonas de carga nula.

    (x, y, z) => (k, j, i) siendo z la profundidad, y la altura y x la anchura
"""

import numpy as np
import matplotlib.pyplot as plt

def crearRed(zona):
    length = zona+1
    red = np.zeros((length, length, length))

    for i in range(length):
        if i == 0:
            for j in range(length):
                for k in range(length):
                    xyz = (j+k)/zona
                    if j == 0:
                        xyz += 1

                    red[i][j][k] += 1/(2.0**xyz)
        elif i < length-1:
            red[i] = red[0]
        else:
            for j in range(length):
                for k in range(length):
                    xyz = (i/zona+j/zona+k/zona)
                    if j == 0:
                        xyz += 1

                    red[i][j][k] += 1/(2.0**xyz)
    return red

def calcMadelung(red):
    madelung = 0
    length = len(red)

    for i in range(length):
        for j in range(length):
            for k in range(1, length):
                if i == len(red)-1:
                    pij2 = i*i+j*j+k*k

                    if pij2 % 2 == 0:
                        madelung -= red[i][j][k]/np.sqrt(pij2)
                    else:
                        madelung += red[i][j][k]/np.sqrt(pij2)
                elif (j == length-1 or k == length-1):
                    pij2 = i*i+j*j+k*k

                    if pij2 % 2 == 0:
                        madelung -= red[i][j][k]/np.sqrt(pij2)
                    else:
                        madelung += red[i][j][k]/np.sqrt(pij2)

    madelung = 8*madelung

    if (length-1)%2 == 0:
        madelung -= 1/float(len(red)-1)
    else:
        madelung += 1/float(len(red)-1)

    return madelung

madelung = 0
zonas = [i for i in range(1, 1001)]
#madelungArray = []

for zona in zonas:
    if zona > 1:
        length = len(red)
        red1 = np.zeros((length, length, length)) + 1

        for i in range(length):
            for j in range(length):
                if j == 0:
                    for k in range(1, length):
                        red1[i][j][k] = red1[i][j][k]*0.5

        red = red1 - red
        madelung += calcMadelung(red)

    red = crearRed(zona)
    madelung += calcMadelung(red)
    #madelungArray.append(madelung)
#print "Para las 3 primeras zonas: %s + %s" %(madelungArray[0], (madelungArray[2]-madelungArray[0]))
print "Para %s zonas: %s" %(len(zonas), madelung)
#plt.plot(zonas, madelungArray)
#plt.show()
